package com.github.akwei.demo1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.function.Supplier;

public class JsonUtil {

    public static final ObjectMapper JSON_MAPPER = initObjectMapper(new ObjectMapper());

    private static ObjectMapper initObjectMapper(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }

    public static <T> String object2json(T t) {
        try {
            return JSON_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String object2json(T t, boolean pretty) {
        try {
            if (pretty) {
                return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(t);
            }
            return JSON_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Object(String json, TypeReference<T> t) {
        return json2Object(json, t, false, () -> null);
    }

    public static <T> T json2Object(String json, TypeReference<T> t, boolean throwException, Supplier<T> defaultValueSupplier) {
        try {
            return JSON_MAPPER.readValue(json, t);
        } catch (JsonProcessingException e) {
            if (throwException) {
                throw new RuntimeException(e);
            }
            return defaultValueSupplier.get();
        }
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> json2Map(String json) {
        return json2Object(json, new TypeReference<Map<String, Object>>() {
        });
    }
}
