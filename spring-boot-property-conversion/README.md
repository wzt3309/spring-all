# spring-boot-property-conversion
Spring Boot attempts to coerce the external application properties to the right type when it 
binds to the `@ConfigurationProperties` beans. If you need custom type conversion, you can 
provide a `ConversionService` bean (with a bean named conversionService) or custom property 
editors (through a `CustomEditorConfigurer` bean) or custom Converters (with bean definitions 
annotated as `@ConfigurationPropertiesBinding`).

> As this bean is requested very early during the application lifecycle, make sure to limit 
the dependencies that your `ConversionService `is using. Typically, any dependency that you 
require may not be fully initialized at creation time. You may want to rename your custom 
`ConversionService` if it is not required for configuration keys coercion and only rely on 
custom converters qualified with `@ConfigurationPropertiesBinding`.

## How it works
When using `@EnableConfigurationProperties` the bean annotated by `ConfigurationProperties` will start the process to bind data from context environment

The process of properties binding for `ConfigurationProperties` is relied on `ConfigurationPropertiesBindingPostProcessor`

First in `ConfigurationPropertiesBindingPostProcessor` the method `postProcessBeforeInitialization` is called
```java
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		ConfigurationProperties annotation = getAnnotation(bean, beanName,
				ConfigurationProperties.class);
		if (annotation != null) {
			bind(bean, beanName, annotation);
		}
		return bean;
	}
```

And then the inner call `bind(bean, beanName, annotation)` will using `ConfigurationPropertiesBinder` to bind target bean
```java
private void bind(Object bean, String beanName, ConfigurationProperties annotation) {
		ResolvableType type = getBeanType(bean, beanName);
		Validated validated = getAnnotation(bean, beanName, Validated.class);
		Annotation[] annotations = (validated != null)
				? new Annotation[] { annotation, validated }
				: new Annotation[] { annotation };
		Bindable<?> target = Bindable.of(type).withExistingValue(bean)
				.withAnnotations(annotations);
		try {
			this.configurationPropertiesBinder.bind(target);
		}
		catch (Exception ex) {
			throw new ConfigurationPropertiesBindException(beanName, bean, annotation,
					ex);
		}
	}
```

The method `bind(...)` of  `ConfigurationPropertiesBinder` will first validate the properties, and the `getBinder()` to bind bean
```java
	public void bind(Bindable<?> target) {
		ConfigurationProperties annotation = target
				.getAnnotation(ConfigurationProperties.class);
		Assert.state(annotation != null,
				() -> "Missing @ConfigurationProperties on " + target);
		List<Validator> validators = getValidators(target);
		BindHandler bindHandler = getBindHandler(annotation, validators);
		getBinder().bind(annotation.prefix(), target, bindHandler);
	}
```

The `getBinder()` method shown as following
```java
private Binder getBinder() {
		if (this.binder == null) {
			this.binder = new Binder(getConfigurationPropertySources(),
					getPropertySourcesPlaceholdersResolver(), getConversionService(),
					getPropertyEditorInitializer());
		}
		return this.binder;
	}
```

The key of class `Binder` is `ConversionService`, so we look into methdo `getConversionService()`
```java
private ConversionService getConversionService() {
		return new ConversionServiceDeducer(this.applicationContext)
				.getConversionService();
	}
```

Above will return an object of type `ConversionServiceDeducer`, so we track the class, and we find this
```java
public ConversionService getConversionService() {
		try {
			return this.applicationContext.getBean(
					ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME,
					ConversionService.class);
		}
		catch (NoSuchBeanDefinitionException ex) {
			return new Factory(this.applicationContext.getAutowireCapableBeanFactory())
					.create();
		}
	}
```

The value of `ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME` is "conversionService", we know that if we have an bean named "conversionService"
we will use it, and when we don't get, we will make a `Factory` to create it
```java
Factory(BeanFactory beanFactory) {
			this.converters = beans(beanFactory, Converter.class,
					ConfigurationPropertiesBinding.VALUE);
			this.genericConverters = beans(beanFactory, GenericConverter.class,
					ConfigurationPropertiesBinding.VALUE);
}

public ConversionService create() {
			if (this.converters.isEmpty() && this.genericConverters.isEmpty()) {
				return ApplicationConversionService.getSharedInstance();
			}
			ApplicationConversionService conversionService = new ApplicationConversionService();
			for (Converter<?, ?> converter : this.converters) {
				conversionService.addConverter(converter);
			}
			for (GenericConverter genericConverter : this.genericConverters) {
				conversionService.addConverter(genericConverter);
			}
			return conversionService;
		}
```

The `Factory` will find all `Converter` or `GenericConverter` annotated by `ConfigurationPropertiesBinding`, and add all converters to `ConversionService`. But if the list of `Converter` is empty, it will
create an default `ConversionService` which is from `ApplicationConversionService.getSharedInstance()`

Now we can go back to the constructor of class `Binder`
```java
public Binder(Iterable<ConfigurationPropertySource> sources,
			PlaceholdersResolver placeholdersResolver,
			ConversionService conversionService,
			Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
		Assert.notNull(sources, "Sources must not be null");
		this.sources = sources;
		this.placeholdersResolver = (placeholdersResolver != null) ? placeholdersResolver
				: PlaceholdersResolver.NONE;
		this.conversionService = (conversionService != null) ? conversionService
				: ApplicationConversionService.getSharedInstance();
		this.propertyEditorInitializer = propertyEditorInitializer;
	}
```

and the method `Bind(ConfigurationPropertyName name, Bindable<T> target,
			BindHandler handler)`
```java
public <T> BindResult<T> bind(ConfigurationPropertyName name, Bindable<T> target,
        BindHandler handler) {
    Assert.notNull(name, "Name must not be null");
    Assert.notNull(target, "Target must not be null");
    handler = (handler != null) ? handler : BindHandler.DEFAULT;
    Context context = new Context();
    T bound = bind(name, target, handler, context, false);
    return BindResult.of(bound);
}
```

The class `Context` is an inner class
```java
...
private final BindConverter converter;
...
Context() {
			this.converter = BindConverter.get(Binder.this.conversionService,
					Binder.this.propertyEditorInitializer);
}
```

The static method `get(...)` and the constructor of `BindConverter` looks like below
```java
static BindConverter get(ConversionService conversionService,
        Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
    if (conversionService == ApplicationConversionService.getSharedInstance()
            && propertyEditorInitializer == null) {
        if (sharedInstance == null) {
            sharedInstance = new BindConverter(conversionService,
                    propertyEditorInitializer);
        }
        return sharedInstance;
    }
    return new BindConverter(conversionService, propertyEditorInitializer);
}

private BindConverter(ConversionService conversionService,
			Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
		Assert.notNull(conversionService, "ConversionService must not be null");
		List<ConversionService> conversionServices = getConversionServices(
				conversionService, propertyEditorInitializer);
		this.conversionService = new CompositeConversionService(conversionServices);
}
```

So if we make a customer `ConversionService`, the `Binder` will use our `ConversionService` and other `ConversionService` get from `getConversionServices()`, in fact the `ConversionService` make from `PropertyEditorRegistry`. And then we
use the `CompositeConversionService` to convert properties.

Summary
So the key class is:
1. `ConfigurationPropertiesBindingPostProcessor` -> call bind()
2. `ConfigurationPropertiesBinder` -> do validate and `getBinder()` to `bind()`
3. method `getBinder()` -> call `getConversionService()` to make `Binder`
4. `getConversionService()` -> find bean named `conversionService`, if not add `Converter` annotated by `@ConfigurationPropertiesBinding` to default `ApplicationConversionService`
5. return 3
6. The constructor of `Binder` union our `ConversionSerice` and others made from
`PropertyEditorRegistry` as a `CompositeConversionService`
7. Using `CompositeConversionService` do convert

