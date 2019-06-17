package github.wzt3309.acl;

import github.wzt3309.SpringBootSecurityAclApplication;
import github.wzt3309.dao.NoticeMessageDao;
import github.wzt3309.domain.NoticeMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@RunWith(SpringRunner.class)
// scan configuration from base package(@SpringBootApplication)
// instead of ContextConfiguration
@SpringBootTest(classes = SpringBootSecurityAclApplication.class)
@ActiveProfiles({"dev"})
// only scan @Entity @Repository etc annotated class about with JPA
// and support for @Transactional
// but this can't be use with SpringBootTest
// @DataJpaTest
// dont use default test Datasource, just use the application Datasource
// whether auto-configure or manual defined
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SpringBootAclIntegrationTest {

    private static final Long FIRST_MESSAGE_ID = 1L;
    private static final Long SECOND_MESSAGE_ID = 2L;
    private static final Long THIRD_MESSAGE_ID = 3L;
    public static final String EDITED_CONTENT = "EDITED";

    @Autowired
    NoticeMessageDao noticeMessageDao;

    @Test
    @WithMockUser(username = "manager")
    public void givenUserManager_whenFindAllMessage_thenReturnFirstMessage() {
        List<NoticeMessage> messages = noticeMessageDao.findAll();
        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals(FIRST_MESSAGE_ID, messages.get(0).getId());
    }


}
