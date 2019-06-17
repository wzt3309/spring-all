# spring-boot-encrypt
Spring Boot does not provide any built in support for encrypting property values, however, it does provide the hook points necessary to modify values contained in the Spring `Environment`. The `EnvironmentPostProcessor` interface allows you to manipulate the `Environment` before the application starts. 

**Jasypt (Java Simplified Encryption) Spring Boot** provides utilities for encrypting property sources in Boot applications.

## Why Jaspty
Whenever we need to store sensitive information in the configuration file – that means we’re essentially making that information vulnerable; this includes any kind of sensitive information, such as credentials, but certainly a lot more than that.

By **using Jasypt**, we can provide encryption for the property file attributes and our application will do the job of decrypting it and retrieving the original value.

## Ways to Use Jaspty with Spring Boot

### Using jasypt-spring-boot-starter
Simply add the starter jar dependency to your project if your Spring Boot application uses `@SpringBootApplication` or @`EnableAutoConfiguration` and encryptable properties will be enabled across the entire Spring Environment (This means any system property, environment property, command line argument, application.properties, yaml properties, and any other custom property sources can contain encrypted properties):

```xml
<dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot-starter</artifactId>
        <version>2.1.0</version>
</dependency>
```

> Maven Central has the latest version of the [jasypt-spring-boot-starter](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22com.github.ulisesbocchio%22%20AND%20a%3A%22jasypt-spring-boot-starter%22).

Let’s now encrypt the text “password@1” with secret key “password” and add it to the `encrypted.properties`:

```java
    @Test
    public void encryptTest() {
        System.out.println(encryptor.encrypt("password@1"));
    }
```

```properties
encrypted.property=ENC(D7g/RN6uylvT3y3NZyPpc3S1Bul5HDm6)
```

And let’s define a configuration class `JasyptStarterConfiguration` – to specify the` encrypted.properties` file as a custom property source:

```java
@Configuration
@PropertySource("classpath:encrypted.properties")
public class JasyptStarterConfiguration {
}
```

Now, we’ll write a service bean `PropertyServiceForJasyptStarter` to retrieve the values from the `encrypted.properties`. The decrypted value can be retrieved using the `@Value` annotation or the `getProperty()` method of `Environment` class:

```java
@Service
public class PropertyServiceForJasyptStarter {

    @Value("${encrypted.property}")
    private String property;

    public String getProperty() {
        return property;
    }
}
```

Finally, using the above service class and setting the secret key which we used for encryption, we can easily retrieve the decrypted password and use in our application:
```java
 @Test
    public void decryptedValueOfPropertyTest() {
        assertEquals("Jasypt decrypt property failed", "password@1", serviceForJasypt.getProperty());
    }
```

### Using jasypt-spring-boot

IF you don't use @SpringBootApplication or @EnableAutoConfiguration Auto Configuration annotations then add this dependency to your project:
```xml
<dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot</artifactId>
        <version>2.1.0</version>
</dependency>
```

And then add `@EnableEncryptableProperties` to you Configuration class. For instance:
```java
@Configuration
@EnableEncryptableProperties
public class JasyptSimpleConfiguration {
    ...
}
```

And encryptable properties will be enabled across the entire Spring Environment (This means any system property, environment property, command line argument, application.properties, yaml properties, and any other custom property sources can contain encrypted properties)

### Don't enable encryptable properties
IF you don't use `@SpringBootApplication` or `@EnableAutoConfiguration` Auto Configuration annotations and you don't want to enable encryptable properties across the entire Spring Environment, there's a third option. First add the following dependency to your project:
```xml
<dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot</artifactId>
        <version>2.1.0</version>
</dependency>
```
And then add as many `@EncryptablePropertySource` annotations as you want in your Configuration files. Just like you do with Spring's `@PropertySource` annotation. For instance:
```java
@Configuration
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:encrypted.properties")
public class MyApplication {
	...
}
```

Conveniently, there's also a `@EncryptablePropertySources` annotation that one could use to group annotations of type `@EncryptablePropertySource` like this:
```java
@Configuration
@EncryptablePropertySources({
    @EncryptablePropertySource("classpath:encrypted.properties"),
    @EncryptablePropertySource("classpath:encrypted2.properties")})
public class MyApplication {
    ...
}
```
Also, note that as of version `1.8`, `@EncryptablePropertySource` supports YAML files

More demo see https://github.com/ulisesbocchio/jasypt-spring-boot-samples