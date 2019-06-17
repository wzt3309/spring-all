package github.wzt3309;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

public class JavaSerializationConverter extends AbstractHttpMessageConverter<Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(JavaSerializationConverter.class);

    /**
     * Construct an {@code AbstractHttpMessageConverter} with no supported media types.
     *
     * @see #setSupportedMediaTypes
     */
    public JavaSerializationConverter() {
        super(new MediaType("application", "x-java-serialization", UTF_8));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        // if return false the converter will not work
        return true;
    }

    @Override
    protected Serializable readInternal(Class<? extends Serializable> aClass, HttpInputMessage httpInputMessage)
            throws IOException, HttpMessageNotReadableException {
        byte[] original = StreamUtils.copyToByteArray(httpInputMessage.getBody());
        return (Serializable) deserialize(original);
    }

    @Override
    protected void writeInternal(Serializable serializable, HttpOutputMessage httpOutputMessage)
            throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getBody().write(Objects.requireNonNull(serialize(serializable)));
    }
}
