package com.leverx.proxypets.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpJsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String jsonWrapper(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
