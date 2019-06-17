package github.wzt3309.config;

import github.wzt3309.security.MySimpleUrlAuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.util.*;

@Configuration
@EnableWebSecurity  // don't use spring-boot SecurityAutoConfiguration
@EnableConfigurationProperties(WebSecurityConfig.InMemoryUserProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    private static final String[] SUPPORTED_ENCODER_IDS = {"bcrypt", "noop", "pbkdf2", "scrypt", "sha256"};

    private InMemoryUserProperties inMemoryUserProperties;
    private PasswordEncoder passwordEncoder;

    @Value("${demo.auth.csrf:true}")
    private boolean csrfEnabled;

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // before password can add {idForEncoder} (e.g. {MD5}mypassword) or you need to given a bean of PasswordEncoder
    // @see WebSecurityConfigurerAdapter.LazyPasswordEncoder and DelegatingPasswordEncoder
    @Bean
    public PasswordEncoder encoder() {
        String idForEncode = inMemoryUserProperties.getIdForEncoder();
        if (!Arrays.asList(SUPPORTED_ENCODER_IDS).contains(idForEncode)) {
            throw new IllegalArgumentException(idForEncode + " is not supported id for encoder");
        }
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final UserDetailsManagerConfigurer manager = auth.inMemoryAuthentication();
        inMemoryUserProperties.getInMemoryUsers().forEach(
                (final String usrPwdRole) -> manager.withUser(extractUserDetails(usrPwdRole)));
    }

    private UserDetails extractUserDetails(final String usrPwdRole) {
        if (usrPwdRole.isEmpty()) {
            return null;
        }

        StringBuilder sbd = new StringBuilder(usrPwdRole);
        String usr, pwd, role;

        int deli = sbd.indexOf(":");
        if (deli <= 0) {
            return null;
        }
        usr = sbd.substring(0, deli);

        deli = sbd.delete(0, deli + 1).indexOf(":");
        if (deli <= 0) {
            if (sbd.length() <= 0) {
                return null;
            }
            pwd = sbd.toString();
        } else {
            pwd = sbd.substring(0, deli);
        }

        // TODO extract roles
        if (deli != sbd.length() - 1 && deli > 0) {
            role = sbd.substring(deli + 1);
        } else {
            role = "USER";
        }

        if (!StringUtils.isEmpty(usr)
                && !StringUtils.isEmpty(pwd)
                && !StringUtils.isEmpty(role)
                && ("USER".equals(role) || "ADMIN".equals(role))) {
            return User.withUsername(usr).password(passwordEncoder.encode(pwd)).roles(role).build();
        }
        return null;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                // configure authorize requests
                .authorizeRequests()
                .antMatchers("/anonymous").anonymous()
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated()

                // configure login form
                .and()
                .formLogin()
                // if you call this method, the default login page will be override
                .loginPage("/login.html")
                // default process url is /login, must be the same as action of login form
                .loginProcessingUrl("/login_perform")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/login.html?error=true")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                // TODO need to figure out why the cookie's name is 'JSESSIONID'
                .deleteCookies("JSESSIONID")

                .and()
                // TODO need to figure out the how does rememberMe() work
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
                .and();

        if (!csrfEnabled) {
            httpSecurity.csrf().disable();
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/webjars/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/img/**");
        super.configure(web);
    }

    protected InMemoryUserProperties getInMemoryUserProperties() {
        return inMemoryUserProperties;
    }

    @Autowired
    protected void setInMemoryUserProperties(InMemoryUserProperties inMemoryUserProperties) {
        this.inMemoryUserProperties = inMemoryUserProperties;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    protected void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @ConfigurationProperties(prefix = "demo.auth")
    @Profile("dev")
    static class InMemoryUserProperties {
        private List<String> inMemoryUsers;
        private String idForEncoder = "bcrypt";

        public List<String> getInMemoryUsers() {
            return inMemoryUsers;
        }

        public void setInMemoryUsers(List<String> inMemoryUsers) {
            this.inMemoryUsers = inMemoryUsers;
        }

        public String getIdForEncoder() {
            return idForEncoder;
        }

        public void setIdForEncoder(String idForEncoder) {
            this.idForEncoder = idForEncoder;
        }
    }
}
