package github.wzt3309.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties("app.system")
public class AppSystemProperties {
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sessionTimeout;
    private Duration readTimeout;
    // Default, failed to convert java.lang.String to java.time.LocalDate.
    // Using custom ConversionService to convert
    private LocalDate localDate;

    public Duration getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Duration sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
