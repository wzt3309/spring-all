package github.wzt3309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("hello")
    public Consumer<String> getHello() {
        return (name) -> {
            System.out.printf("hello %s\n", name);
        };
    }
}
