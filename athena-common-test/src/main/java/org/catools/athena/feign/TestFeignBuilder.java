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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TestFeignBuilder {

  private final TestRestTemplate testRestTemplate;

  public <C> C getClient(Class<C> clazz) {
    return getClient(clazz, 1, TimeUnit.MINUTES);
  }

  public <C> C getClient(Class<C> clazz, int readTimeout, TimeUnit readTimeoutUnit) {
    ObjectMapper mapper = JacksonUtil.objectMapper();
    return Feign.builder()
        .encoder(new JacksonEncoder(mapper))
        .decoder(new JacksonDecoder(mapper))
        .options(new Request.Options(10, TimeUnit.MILLISECONDS, readTimeout, readTimeoutUnit, true))
        .retryer(Retryer.NEVER_RETRY)
        .logger(new Slf4jLogger(clazz))
        .logLevel(Logger.Level.FULL)
        .target(clazz, testRestTemplate.getRootUri());
  }
}
