package cn.perhome.snapha.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public final class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {}

    public static ObjectMapper getObjectMapper(){
        return mapper;
    }
    /**
     * Serialize any Java value as a String.
     */
    public static String generate(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
    /**
     * Deserialize JSON content from given JSON content String.
     */
    public static <T> T parse(String content, Class<T> valueType) throws IOException {
        if (content == null) return null;
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, valueType);
    }

    public static JsonNode toJsonNode(Object object) {
        return mapper.valueToTree(object);
    }
}