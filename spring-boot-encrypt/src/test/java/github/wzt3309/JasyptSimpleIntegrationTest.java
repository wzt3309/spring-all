package github.wzt3309;

import github.wzt3309.service.PropertyServiceForJasyptStarter;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jasypt.encryptor.password=password"})
@ActiveProfiles({"test"})
public class JasyptSimpleIntegrationTest {

    @Autowired
    StringEncryptor encryptor;

    @Autowired
    PropertyServiceForJasyptStarter serviceForJasypt;

    private final String plain = "password@1";

    @Test
    public void encryptTest() {
        System.out.println(encryptor.encrypt(plain));
    }

    @Test
    public void decryptedValueOfPropertyTest() {
        assertEquals("Jasypt decrypt property failed", plain, serviceForJasypt.getProperty());
    }
}
