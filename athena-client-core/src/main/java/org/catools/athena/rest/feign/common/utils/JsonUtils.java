package org.catools.athena.rest.feign.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.rest.feign.common.exception.JsonException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Slf4j
@UtilityClass
public class JsonUtils {

  public static <T> T readValue(final String input, Class<T> clazz) {
    try {
      return getObjectMapper().readValue(input, clazz);
    } catch (JsonProcessingException e) {
      throw new JsonException("Failed to read value from string", e);
    }
  }

  @Nullable
  public static String writeValueAsString(Object obj) {
    if (obj == null) return null;
    try {
      return getObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new JsonException("Failed to build value as string", e);
    }
  }

  @Nullable
  public static Integer buildHash(Object obj) {
    if (obj == null) return null;
    try {
      ObjectMapper mapper = getObjectMapper();
      return mapper.readTree(mapper.writeValueAsString(obj).getBytes()).hashCode();
    } catch (IOException e) {
      throw new JsonException("Failed to build hash", e);
    }
  }

  @NotNull
  private static ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }
}
