package org.catools.athena.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

  @Value("${cors.allowed.origins:}")
  private String additionalOrigins;

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();

    // Default allowed origins for development
    List<String> allowedOrigins = new ArrayList<>(List.of(
        "http://localhost:3000",
        "http://localhost:3001",
        "http://127.0.0.1:3000",
        "http://127.0.0.1:3001",
        "http://localhost:8080",
        "http://127.0.0.1:8080"
    ));

    // Add custom origins from environment/config
    if (additionalOrigins != null && !additionalOrigins.isEmpty()) {
      allowedOrigins.addAll(Arrays.asList(additionalOrigins.split(",")));
    }

    corsConfig.setAllowedOrigins(allowedOrigins);

    // Allow all HTTP methods
    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

    // Allow all headers
    corsConfig.setAllowedHeaders(List.of("*"));

    // Allow credentials (cookies, authorization headers)
    corsConfig.setAllowCredentials(true);

    // Cache preflight response for 1 hour
    corsConfig.setMaxAge(3600L);

    // Expose headers to the client
    corsConfig.setExposedHeaders(List.of(
        "Authorization",
        "Content-Type",
        "X-Requested-With",
        "Accept",
        "Origin",
        "Access-Control-Request-Method",
        "Access-Control-Request-Headers"
    ));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}

