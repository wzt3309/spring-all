package github.wzt3309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This is known as a stereotype annotation.
// It provides hints for people reading the code and for Spring that the class plays a specific role.
// In this case, our class is a web `Controller`
@RestController
// This annotation tells Spring Boot to “guess” how you want to configure Spring,
// based on the jar dependencies that you have added
@EnableAutoConfiguration
public class SpringBootHelloApplication {

    // This annotation provides “routing” information
    @RequestMapping("/")
    public String index() {
        return "hello, world";
    }

    public static void main(String[] args) {
        // SpringApplication that can be used to bootstrap
        // and launch a Spring application from a Java main method
        SpringApplication.run(SpringBootHelloApplication.class, args);
    }
}
