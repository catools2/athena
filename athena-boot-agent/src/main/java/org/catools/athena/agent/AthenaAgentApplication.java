package org.catools.athena.agent;

import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.data.rest.autoconfigure.DataRestAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

/**
 * Agent surface for Athena.
 *
 * <p>It owns no data of its own. Every tool it exposes calls the analytics service over HTTP, the
 * same endpoint the UI uses, so an answer given in chat and a number shown on a dashboard cannot
 * drift apart. Like the analytics service it has no persistence unit, so the JPA and Data REST
 * auto-configurations inherited from athena-boot-parent are excluded.
 */
@SpringBootApplication(
    scanBasePackages = "org.catools.athena",
    exclude = {
      DataSourceAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class,
      DataJpaRepositoriesAutoConfiguration.class,
      DataRestAutoConfiguration.class,
      SpringDocDataRestConfiguration.class,
      SpringDocHateoasConfiguration.class
    })
public class AthenaAgentApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaAgentApplication.class, args);
  }
}
