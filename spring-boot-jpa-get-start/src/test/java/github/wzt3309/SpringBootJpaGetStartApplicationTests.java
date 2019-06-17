package github.wzt3309;

import github.wzt3309.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpringBootJpaGetStartApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindAllUsers_thenReturnListWithOneUser() {
        assertThat(userRepository.findAll()).size().isEqualTo(1);
    }

}
