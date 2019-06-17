package github.wzt3309;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootLogLogbackApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootLogLogbackApplication.class);

    @PostConstruct
    public void logSomething() {
        logger.debug("Debug message");
        logger.trace("Trace message");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogLogbackApplication.class, args);
    }
}
