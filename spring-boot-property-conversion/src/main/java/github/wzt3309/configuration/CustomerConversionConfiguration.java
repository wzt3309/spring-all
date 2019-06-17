package github.wzt3309.configuration;

import github.wzt3309.converter.LocalDateConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Configuration
@EnableConfigurationProperties({AppSystemProperties.class, AppIOProperties.class})
public class CustomerConversionConfiguration {

    // The Bean must named conversionService
    @Profile("conversion")
    @Bean
    public ConversionService conversionService() {
        GenericConversionService service = new GenericConversionService();
        service.addConverter(new LocalDateConverter());
        return service;
    }

}
