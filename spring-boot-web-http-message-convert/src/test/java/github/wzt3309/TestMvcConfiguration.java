package github.wzt3309;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("github.wzt3309.controller")
@EnableAutoConfiguration
class TestMvcConfiguration {
}