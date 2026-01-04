package org.catools.athena.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.JacksonUtil;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TestFeignBuilder {

  private final Environment environment;

  /**
   * Creates a Feign client for the specified interface using the server port from environment.
   * Port is fetched dynamically to ensure it's already set by Spring Boot.
   * Uses default timeout of 1 minute.
   */
  public <C> C getClient(Class<C> clazz) {
    return getClient(clazz, getServerPort(), 1, TimeUnit.MINUTES);
  }

  /**
   * Creates a Feign client with custom port and timeout settings.
   */
  public <C> C getClient(Class<C> clazz, int port, int readTimeout, TimeUnit readTimeoutUnit) {
    ObjectMapper mapper = JacksonUtil.objectMapper();
    String baseUrl = "http://localhost:%s".formatted(port);
    return Feign.builder()
        .encoder(new JacksonEncoder(mapper))
        .decoder(new JacksonDecoder(mapper))
        .options(new Request.Options(10, TimeUnit.SECONDS, readTimeout, readTimeoutUnit, true))
        .retryer(Retryer.NEVER_RETRY)
        .logger(new Slf4jLogger(clazz))
        .logLevel(Logger.Level.FULL)
        .target(clazz, baseUrl);
  }

  /**
   * Gets the server port dynamically from the environment.
   * This ensures the port is read after Spring Boot has assigned the random port.
   */
  private int getServerPort() {
    String port = environment.getProperty("local.server.port");
    if (port == null) {
      port = environment.getProperty("server.port", "8080");
    }
    return Integer.parseInt(port);
  }
}
