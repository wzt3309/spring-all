package github.wzt3309.controller;

import github.wzt3309.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CustomController {
    private static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    @PutMapping("/user")
    public User putUser(@RequestBody User user) {
        logger.info("Create a user {}", user);
        return user;
    }
}
