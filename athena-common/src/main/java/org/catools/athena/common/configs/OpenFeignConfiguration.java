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

  /**
   * Retry budget for idempotent calls between athena components.
   *
   * <p>Sized against the real failure it has to absorb: a callee pod being replaced. A Service with
   * no ready endpoints answers with a kube-proxy REJECT, which arrives here as {@code
   * SocketException: Operation not permitted} wrapped in a {@link feign.RetryableException}. The
   * previous budget (100ms initial, 1s cap, 5 attempts) spent ~1.5s before giving up, so a caller
   * hitting that window returned 500 rather than riding out the gap.
   *
   * <p>The backoff below sleeps 250+500+1000+2000+4000ms across its 5 retries, ~7.75s in total
   * before the 6th attempt propagates. That is deliberately not sized to cover a full ~40s Spring
   * Boot startup - holding a request thread that long trades one failure for thread-pool exhaustion
   * under load. Surviving a brief endpoint gap is the retryer's job; surviving a full restart is
   * {@code default.replicas: 2} plus the PodDisruptionBudget in the Helm chart, which keep a ready
   * endpoint available throughout.
   */
  @Bean
  public Retryer retryer() {
    return new IdempotentMethodRetryer(250, TimeUnit.SECONDS.toMillis(5), 6);
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
