package github.wzt3309;

import github.wzt3309.configuration.AppSystemProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "conversion"})
public class ConversionServiceTest {

    @Autowired
    private AppSystemProperties properties;

    @Test
    public void getPropertiesTest() {
        String fmt = "Expected timeout is %s, but actual is %s";
        Duration expected, actual;

        expected = Duration.ofSeconds(30);
        actual = properties.getSessionTimeout();
        assertEquals(String.format(fmt, expected, actual), expected, actual);

        expected = Duration.ofMillis(1000);
        actual = properties.getReadTimeout();
        assertEquals(String.format(fmt, expected, actual), expected, actual);

        fmt = "Expected local data is %s, but actual is %s";
        LocalDate exp, act;
        exp = LocalDate.of(2018, 12, 6);
        act = properties.getLocalDate();
        assertEquals(String.format(fmt, exp, act), exp, act);
    }
}
