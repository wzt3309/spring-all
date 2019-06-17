package github.wzt3309.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /* using this method, so don't need to create controller logic
     * for homepage, login page and so on.
     * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/anonymous");
        // the view resolver will automatically find the view with the same name as the urlPath
        registry.addViewController("/login.html");
        registry.addViewController("/homepage.html");
        registry.addViewController("/csrfHome.html");
    }
}
