# spring-boot-banner
> See https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/#boot-features-banner

We can print one banner.txt and one banner.jpg at same time, and the
banner.jpgs are converted into an ASCII art representation and printed 
above any text banner.

We use `spring.banner.location` and `spring.banner.image.location` to
specific the location of banner.txt and banner.jpg, but default is location is `classpath:banner.txt` and `classpath:banner.jpg/png/gif`

When you use IDEA to lanuach the application, the banner variables will not 
display well, beacause the file `MANIFEST.MF` is created only when you use
maven/gradle to package the application.

YAML maps off to false, so be sure to add quotes if you want to disable the banner
in your application, as shown in the following example:
```yaml
spring:
	main:
		banner-mode: "off"
```

The `SpringApplication.setBanner(…​)` method can be used if you want to 
generate a banner programmatically. Use the `org.springframework.boot.Banner`
interface and implement your own` printBanner()` method.