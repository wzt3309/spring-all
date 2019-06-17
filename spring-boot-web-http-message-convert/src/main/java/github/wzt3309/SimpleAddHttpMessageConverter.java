package github.wzt3309;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleAddHttpMessageConverter {

    @Bean
    public JavaSerializationConverter javaSerializationConverter() {
        return new JavaSerializationConverter();
    }
}
