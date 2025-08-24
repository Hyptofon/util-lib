package com.example.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public final class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules(); // JavaTimeModule тощо

    private JsonUtils() {}

    public static JsonNode parse(String json) {
        try { return MAPPER.readTree(json); }
        catch (JsonProcessingException e) { throw new IllegalArgumentException("Invalid JSON", e); }
    }

    public static String toPretty(Object value) {
        try { return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value); }
        catch (JsonProcessingException e) { throw new RuntimeException(e); }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try { return MAPPER.readValue(json, type); }
        catch (JsonProcessingException e) { throw new IllegalArgumentException("Invalid JSON", e); }
    }

    public static String toJson(Object value) {
        try { return MAPPER.writeValueAsString(value); }
        catch (JsonProcessingException e) { throw new RuntimeException(e); }
    }
}
