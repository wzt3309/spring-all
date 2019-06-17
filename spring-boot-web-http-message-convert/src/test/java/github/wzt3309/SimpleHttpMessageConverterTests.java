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

import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleAddHttpMessageConverter.class, properties = "debug=true")
// Initialize a mock servlet environment includes SpringBootMockServletContext and TestDispatcherServlet
// but the other things like filter, converter, controller are not up to the test environment
@AutoConfigureMockMvc
@Import(TestMvcConfiguration.class)
public class SimpleHttpMessageConverterTests {

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

    @Test
    public void testDesAndSes() throws Exception {
        User.Builder builder = User.builder();
        List<User> users = new ArrayList<>();
        users.add(builder.name("Jone").age(12).build());
        users.add(builder.name("Mike").age(21).build());

        for (User user: users) {
            assertThat(deserialize(serialize(user))).isEqualTo(user);
        }
    }
}
