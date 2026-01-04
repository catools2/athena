package org.catools.athena.tms.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.feign.VersionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.catools.athena"})
@PropertySource("classpath:defaults/open_feign.properties")
public class TmsCoreConfigs {

  @Value("${feign.clients.athena.core.url}")
  private String coreUrl;

  @Bean
  @Profile("!testContainers")
  public ProjectFeignClient projectFeignClient(ObjectMapper objectMapper) {
    return FeignUtils.defaultBuilder(ProjectFeignClient.class, objectMapper, coreUrl);
  }

  @Bean
  @Profile("!testContainers")
  public VersionFeignClient versionFeignClient(ObjectMapper objectMapper) {
    return FeignUtils.defaultBuilder(VersionFeignClient.class, objectMapper, coreUrl);
  }

  @Bean
  @Profile("!testContainers")
  public UserFeignClient userFeignClient(ObjectMapper objectMapper) {
    return FeignUtils.defaultBuilder(UserFeignClient.class, objectMapper, coreUrl);
  }

  @Bean
  @Profile("!testContainers")
  public EnvironmentFeignClient environmentFeignClient(ObjectMapper objectMapper) {
    return FeignUtils.defaultBuilder(EnvironmentFeignClient.class, objectMapper, coreUrl);
  }
}