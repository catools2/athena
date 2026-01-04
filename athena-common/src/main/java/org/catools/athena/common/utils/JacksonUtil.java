package org.catools.athena.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.jackson.JacksonDecoder;
import lombok.experimental.UtilityClass;
import org.catools.athena.common.exception.JacksonException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

@UtilityClass
public class JacksonUtil {
  public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String UTC = "UTC";


  @SuppressWarnings("unchecked")
  public static <T> T toT(Response response, Class<T> clazz) {
    try {
      return (T) new JacksonDecoder().decode(response, clazz);
    } catch (IOException e) {
      throw new JacksonException(e);
    }
  }

  public static <T> T toT(String response, Class<T> clazz) {
    try {
      return objectMapper().readValue(response, clazz);
    } catch (JsonProcessingException e) {
      throw new JacksonException(e);
    }
  }

  public static JsonNode toJsonNode(Response response) {
    return toT(response, JsonNode.class);
  }

  public static JsonNode toJsonNode(String response) {
    return toT(response, JsonNode.class);
  }

  public static Map<String, Object> toMap(JsonNode input) {
    try {
      return objectMapper().readValue(input.toString(), new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      throw new JacksonException(e);
    }
  }

  public static String toJsonString(Object object) {
    try {
      return objectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new JacksonException(e);
    }
  }

  public static ObjectMapper objectMapper() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getTimeZone(UTC));
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    objectMapper.setDateFormat(dateFormat);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
}
