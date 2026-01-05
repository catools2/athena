package org.catools.athena.spec.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.common.feign.FeignConfiguration;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.core.feign.ProjectFeignClient;
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
@PropertySource("classpath:defaults/open_feign.properties")
public class ApiSpecCoreConfigs {

  @Value("${feign.clients.athena.core.url}")
  private String coreUrl;

  @Bean
  @Profile("!testContainers")
  public ProjectFeignClient projectFeignClient(ObjectMapper objectMapper) {
    return FeignUtils.defaultBuilder(ProjectFeignClient.class, objectMapper, coreUrl);
  }
}