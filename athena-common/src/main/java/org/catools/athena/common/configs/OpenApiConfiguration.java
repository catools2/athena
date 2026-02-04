package org.catools.athena.common.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

  @Bean
  @ConditionalOnProperty(name = "springdoc.swagger-ui.server.url")
  public OpenAPI openAPI(
      @Value("${springdoc.swagger-ui.server.url}") final String url,
      @Value("${springdoc.swagger-ui.server.description:Default Server}") final String description
  ) {
    return new OpenAPI().servers(List.of(new Server().url(url).description(description)));
  }
}
