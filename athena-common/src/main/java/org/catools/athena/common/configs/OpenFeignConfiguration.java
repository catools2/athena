package org.catools.athena.common.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Retryer;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.catools.athena.common.feign.IdempotentMethodRetryer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

@Configuration
public class OpenFeignConfiguration {

  @Autowired
  private ObjectMapper mapper;

  @Bean
  public Encoder encoder() {
    return new JacksonEncoder(mapper);
  }

  @Bean
  public Decoder decoder() {
    return new TypedResponseDecoder(new JacksonDecoder(mapper));
  }

  @Bean
  public Logger logger() {
    return new Slf4jLogger(getClass());
  }

  @Bean
  public Retryer retryer() {
    return new IdempotentMethodRetryer(100, TimeUnit.SECONDS.toMillis(1), 5);
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public Request.Options requestOptions() {
    return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
  }

  public static class TypedResponseDecoder implements Decoder {
    private final Decoder delegate;

    public TypedResponseDecoder(Decoder delegate) {
      this.delegate = delegate;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException {
      return delegate.decode(response, type);
    }
  }
}
