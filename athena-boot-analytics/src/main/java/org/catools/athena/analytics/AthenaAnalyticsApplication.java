package org.catools.athena.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Read-only analytics service over the {@code athena} reporting schema.
 *
 * <p>It owns no entities and runs no migrations: every read goes through the query registry against
 * views another service populated. Its one connection pool is configured in
 * {@code application.properties} and binds as a read-only role, which is what keeps SQL authored in
 * Grafana from being able to write.
 *
 * <p>The pool fails fast. The warehouse is expected to be up, so a service that cannot reach it has
 * nothing to serve and should say so at startup rather than accept traffic and answer every request
 * with an error.
 */
@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaAnalyticsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaAnalyticsApplication.class, args);
  }
}
