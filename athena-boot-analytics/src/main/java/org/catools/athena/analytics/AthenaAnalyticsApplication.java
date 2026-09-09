package org.catools.athena.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.data.rest.autoconfigure.DataRestAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

/**
 * Read-only analytics service over the {@code athena} reporting schema.
 *
 * <p>It owns no entities and runs no migrations: every read goes through the query registry against
 * views another service populated. The JPA, Data REST and default DataSource auto-configurations
 * are excluded because they are inherited from athena-boot-parent and assume a persistence unit -
 * Data REST in particular refuses to start on an empty JPA metamodel. Excluding them also leaves
 * exactly one connection pool in this service, the read-only one in
 * {@link org.catools.athena.analytics.config.AnalyticsDataSourceConfig}.
 */
@SpringBootApplication(
    scanBasePackages = "org.catools.athena",
    exclude = {
      DataSourceAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class,
      DataJpaRepositoriesAutoConfiguration.class,
      DataRestAutoConfiguration.class,
      // springdoc wires its own Data REST support off those beans, so it has to go too.
      SpringDocDataRestConfiguration.class,
      SpringDocHateoasConfiguration.class
    })
public class AthenaAnalyticsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaAnalyticsApplication.class, args);
  }
}
