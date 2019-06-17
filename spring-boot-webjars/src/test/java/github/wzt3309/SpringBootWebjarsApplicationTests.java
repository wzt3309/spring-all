package github.wzt3309;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootWebjarsApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testFreeMarkerTpl() {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity("/",
                String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("Hello, Admin");
    }

    @Test
    public void testFreeMarkerTplError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.testRestTemplate
                .exchange("/does-not-exists", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Something went wrong: 404 Not Found");
    }

}
