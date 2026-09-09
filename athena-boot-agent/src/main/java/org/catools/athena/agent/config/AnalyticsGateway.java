package org.catools.athena.agent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

/**
 * HTTP client for the analytics service.
 *
 * <p>The agent deliberately goes through the same REST API as the browser rather than opening its
 * own database connection. That keeps exactly one implementation of what a query means, and it
 * means the agent inherits the read-only role, the row cap and the statement timeout for free
 * instead of re-earning them.
 */
@Slf4j
@Configuration
public class AnalyticsGateway {

  @Bean
  public RestClient analyticsRestClient(
      @Value("${athena.agent.analytics.base-url}") String baseUrl,
      @Value("${athena.agent.analytics.timeout-seconds:60}") long timeoutSeconds) {
    log.info("Agent tools will call the analytics service at {}", baseUrl);
    var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(Duration.ofSeconds(10));
    factory.setReadTimeout(Duration.ofSeconds(timeoutSeconds));
    return RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
  }
}
