package com.bci.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class TestUtil {

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper().findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JavaTimeModule());
    }

    public static String jsonToString(Object value) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(value);
    }

    public static MappingJackson2HttpMessageConverter getMessageConverters() {
        MappingJackson2HttpMessageConverter messageConverters = new MappingJackson2HttpMessageConverter();
        messageConverters.setObjectMapper(getObjectMapper());
        return messageConverters;
    }
}
