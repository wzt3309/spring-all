package github.wzt3309.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:encrypted.properties")
public class JasyptStarterConfiguration {
}
