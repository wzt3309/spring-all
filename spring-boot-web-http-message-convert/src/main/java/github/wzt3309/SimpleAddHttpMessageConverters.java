package github.wzt3309;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

@Configuration
public class SimpleAddHttpMessageConverters {

    @Bean
    // Get List<HttpMessageConverter<?>> from spring context
    public HttpMessageConverters customHttpMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new JavaSerializationConverter());
        return new HttpMessageConverters(converters);
    }
}
