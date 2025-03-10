package org.catools.athena.common.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Retryer;
import feign.TypedResponse;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@UtilityClass
public class FeignUtils {

  public <T> T defaultBuilder(Class<T> apiType, ObjectMapper objectMapper, String targetUrl) {
    return FeignUtils.defaultBuilder(apiType, objectMapper)
        .target(apiType, targetUrl);
  }

  public <T> T defaultBuilder(Class<T> apiType, ObjectMapper objectMapper, String host, Integer port) {
    return defaultBuilder(apiType, objectMapper, String.format("http://%s:%s", host, port));
  }

  public <T> Feign.Builder defaultBuilder(Class<T> apiType, ObjectMapper objectMapper) {
    return Feign
        .builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .retryer(Retryer.NEVER_RETRY)
        .logger(new Slf4jLogger(apiType));
  }

  public static Long getIdFromLocationHeader(TypedResponse<?> response) {
    String location = response.headers().get("location").stream().findFirst().orElseThrow();
    Objects.requireNonNull(location);
    return Long.valueOf(StringUtils.substringAfterLast(location, "/"));
  }

}