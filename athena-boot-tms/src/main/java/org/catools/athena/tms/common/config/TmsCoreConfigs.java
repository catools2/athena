package org.catools.athena.tms.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.catools.athena.common.feign.FeignConfiguration;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.feign.VersionFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.catools.athena"})
@Import(FeignConfiguration.class)
@PropertySource("classpath:conf/core.properties")
public class TmsCoreConfigs {

  @Autowired
  private ObjectMapper objectMapper;

  @Value("feign.clients.athena.core.url")
  private String coreUrl;

  @Bean
  @Profile("!testContainers")
  public ProjectFeignClient projectFeignClient() {
    return Feign
        .builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .retryer(Retryer.NEVER_RETRY)
        .logger(new Slf4jLogger(ProjectFeignClient.class))
        .target(ProjectFeignClient.class, coreUrl);
  }

  @Bean
  @Profile("!testContainers")
  public VersionFeignClient versionFeignClient() {
    return Feign
        .builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .retryer(Retryer.NEVER_RETRY)
        .logger(new Slf4jLogger(VersionFeignClient.class))
        .target(VersionFeignClient.class, coreUrl);
  }

  @Bean
  @Profile("!testContainers")
  public UserFeignClient userFeignClient() {
    return Feign
        .builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .retryer(Retryer.NEVER_RETRY)
        .logger(new Slf4jLogger(UserFeignClient.class))
        .target(UserFeignClient.class, coreUrl);
  }
}