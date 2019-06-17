package github.wzt3309.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()

                .and()
                // will return default login page if loginPage is not specified
                .formLogin()
                // the default loginProcessingUrl is login, so if u want to specific
                // the url, u need to chose a different path
//                .loginProcessingUrl("/login")
                // when not visit a secure page then redirect to '/welcome'
                .defaultSuccessUrl("/welcome")
                // be careful when forward the http method is still post
                // because Spring's forward uses the current request with the same HTTP method
//                .successForwardUrl("/hello.html")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")

                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(3600*24)

                /*.and()
                .csrf().disable()*/
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                // @see LazyPasswordEncoder and then PasswordEncoderFactories
                .withUser("user1").password("{noop}user1").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin").roles("ADMIN");
    }
}
