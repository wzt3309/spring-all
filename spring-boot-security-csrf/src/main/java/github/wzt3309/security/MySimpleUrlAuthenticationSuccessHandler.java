package github.wzt3309.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * This is my implement of AuthenticationSuccessHandler.
 * ROLE_ADMIN redirect to console.html and ROLE_USER redirect to homepage.html
 *
 * @see SimpleUrlAuthenticationSuccessHandler
 */
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(MySimpleUrlAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest httpServletRequest,
                                        final HttpServletResponse httpServletResponse,
                                        final Authentication authentication) throws IOException, ServletException {
        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttributes(httpServletRequest);
    }

    protected void handle(final HttpServletRequest httpServletRequest,
                          final HttpServletResponse httpServletResponse,
                          final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);
        if (httpServletResponse.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to '{}'", targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {
        boolean isAdmin = false;
        boolean isUser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            }
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (isAdmin) {
            return "/console.html";
        } else if (isUser) {
            return "/homepage.html";
        } else {
            throw new IllegalArgumentException("Authentication with illegal authority, must be ROLE_USER" +
                    "or ROLE_ADMIN");
        }
    }

    protected void clearAuthenticationAttributes(final HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
