package github.wzt3309.converter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ConditionalOnMissingBean(ConversionService.class)
@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }

        return LocalDate.parse(s, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }
}
