package github.wzt3309;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class SpringBootLogLogbackApplicationTests {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void testLoadedCustomLogbackConfig() {
        SpringBootLogLogbackApplication.main(new String[0]);
        this.outputCapture.expect(containsString("Debug message"));
        this.outputCapture.expect(not(containsString("Sample Trace Message")));
    }

    @Test
    public void testProfile() {
        SpringBootLogLogbackApplication.main(new String[]{"--spring.profiles.active=staging"});
        this.outputCapture.expect(containsString("Debug message"));
        this.outputCapture.expect(containsString("Trace message"));
    }

}
