package github.wzt3309;

import github.wzt3309.configuration.AppIOProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.unit.DataSize;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DataSizeConversionTest {

    @Autowired
    private AppIOProperties properties;

    @Test
    public void convertingTest() {
        assertEquals(DataSize.ofMegabytes(100), properties.getBufferSize());
        assertEquals(DataSize.ofBytes(1024), properties.getSizeThreshold());
    }

}
