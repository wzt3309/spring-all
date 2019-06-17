package github.wzt3309;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;

@ConfigurationProperties("acme")
@Validated
public class AcmeProperties {

    @NotNull
    private InetAddress remoteAddress;

    @NestedConfigurationProperty
    @Valid
    private Security2 security;

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Security2 getSecurity() {
        return security;
    }

    public void setSecurity(Security2 security) {
        this.security = security;
    }

    public static class Security2 {
        @NotNull
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
