package github.wzt3309;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class SpringBootPropertyValidationApplicationTests {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @After
    public void closeContext() {
        this.context.close();
    }

    @EnableConfigurationProperties(SampleProperties.class)
    private static class TestConfiguration {
        @Bean
        public static Validator configurationPropertiesValidator() {
            return new SamplePropertiesValidator();
        }
    }

    @Test
    public void bindValidProperties() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        TestPropertyValues.of("sample.remote-address: 192.168.0.1", "sample.security.username: root")
                .applyTo(this.context);
        this.context.refresh();
        SampleProperties properties = this.context.getBean(SampleProperties.class);
        assertThat(properties.getRemoteAddress().getHostAddress()).isEqualTo("192.168.0.1");
        assertThat(properties.getSecurity().getUsername()).isEqualTo("root");
    }

    @Test
    public void bindInvalidRemoteAddress() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        TestPropertyValues.of("sample.remote-address: xxxxx", "sample.security.username: root")
                .applyTo(this.context);
        assertThatExceptionOfType(BeanCreationException.class)
                .isThrownBy(this.context::refresh)
                .withMessageContaining("Failed to bind properties under 'sample.remote-address'");
    }

    @Test
    public void bindNullSecurityUsername() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        TestPropertyValues.of("sample.remote-address: 192.168.0.1")
                .applyTo(this.context);
        assertThatExceptionOfType(BeanCreationException.class)
                .isThrownBy(this.context::refresh)
                .withMessageContaining("Failed to bind properties under 'sample'");
    }

    @Test
    public void bindNullRemoteAddress() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        assertThatExceptionOfType(BeanCreationException.class)
                .isThrownBy(this.context::refresh)
                .withMessageContaining("Failed to bind properties under 'sample'");
    }

    @Test
    public void validatorOnlyCalledOnSupportedClass() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        this.context.register(ServerProperties.class); // our validator will not apply
        TestPropertyValues.of("sample.remote-address: 192.168.0.1", "sample.security.username: root")
                .applyTo(this.context);
        this.context.refresh();
        SampleProperties properties = this.context.getBean(SampleProperties.class);
        assertThat(properties.getRemoteAddress().getHostAddress()).isEqualTo("192.168.0.1");
        assertThat(properties.getSecurity().getUsername()).isEqualTo("root");
    }

}
