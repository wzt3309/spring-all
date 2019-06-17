package github.wzt3309;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class SamplePropertiesValidator implements Validator {
    private final Pattern pattern = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");

    @Override
    public boolean supports(Class<?> type) {
        return type == SampleProperties.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "remoteAddress", "remote-address.empty");
        ValidationUtils.rejectIfEmpty(errors, "security.username", "security.username.empty");
        SampleProperties properties = (SampleProperties) o;
        if (properties.getRemoteAddress() != null
                && !this.pattern.matcher(properties.getRemoteAddress().getHostAddress()).matches()) {
            errors.rejectValue("remoteAddress", "Invalid inet address");
        }
    }
}
