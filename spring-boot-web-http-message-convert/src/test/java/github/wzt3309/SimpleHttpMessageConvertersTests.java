package github.wzt3309;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.SerializationUtils.serialize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleAddHttpMessageConverters.class)
@AutoConfigureMockMvc
@Import(TestMvcConfiguration.class)
public class SimpleHttpMessageConvertersTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void customMessageConverter() throws Exception {
        User expect = User.builder().name("Jone").age(12).build();
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/api/v1/user")
                        .contentType(new MediaType("application", "x-java-serialization", UTF_8))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(serialize(expect));
        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(objectMapper.writeValueAsString(expect)));
    }
}
