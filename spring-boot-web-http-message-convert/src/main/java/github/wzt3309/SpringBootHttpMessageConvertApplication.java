package github.wzt3309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
public class SpringBootHttpMessageConvertApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHttpMessageConvertApplication.class, args);
    }
}
