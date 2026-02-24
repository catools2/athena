package org.catools.athena.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Database initialization application that runs Flyway migrations and exits.
 * Designed to be used as an init container in Kubernetes deployments.
 */
@Slf4j
@SpringBootApplication
public class AthenaInitApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaInitApplication.class, args);
  }

  /**
   * CommandLineRunner that shuts down the application after Spring Boot
   * completes initialization (including Flyway migrations).
   */
  @Bean
  public CommandLineRunner shutdownAfterInit(ApplicationContext context) {
    return args -> {
      log.info("Database initialization completed successfully. Shutting down...");
      System.exit(SpringApplication.exit(context, () -> 0));
    };
  }
}
