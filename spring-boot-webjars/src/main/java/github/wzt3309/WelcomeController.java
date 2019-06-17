package github.wzt3309;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @Value("${application.message:hello, world}")
    private String message = "hello, world";

    @GetMapping
    public String welcome(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("time", LocalDateTime.now());
        return "welcome";
    }
}
