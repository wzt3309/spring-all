package github.wzt3309;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JSR303ValidationTests {
    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @After
    public void closeContext() {
        this.context.close();
    }

    @EnableConfigurationProperties(AcmeProperties.class)
    private static class TestConfiguration {

    }

    @Test
    public void bindValidProperties() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        TestPropertyValues.of("acme.remote-address: localhost", "acme.security.username: root")
                .applyTo(this.context);
        this.context.refresh();
        AcmeProperties properties = this.context.getBean(AcmeProperties.class);
        assertThat(properties.getRemoteAddress().getHostName()).isEqualTo("localhost");
        assertThat(properties.getSecurity().getUsername()).isEqualTo("root");
    }

    @Test
    public void bindNullRemoteAddress() {
        this.context.register(SpringBootPropertyValidationApplication.class, TestConfiguration.class);
        assertThatExceptionOfType(BeanCreationException.class)
                .isThrownBy(this.context::refresh)
                .withMessageContaining("Failed to bind properties under 'acme'");
    }
}
