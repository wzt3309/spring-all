package github.wzt3309.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Note!! Just for h2 console in 'Development'
 * - Allow all access to the url path /console/*.
 * - Disable CRSF (Cross-Site Request Forgery). By default, Spring Security will protect against CRSF attacks.
 * - Since the H2 database console runs inside a frame, you need to enable this in in Spring Security.
 */
@Configuration
@Profile("dev")
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/myconsole/**").permitAll()

                .and()
                .csrf().disable()

                .headers().frameOptions().disable();
    }
}
