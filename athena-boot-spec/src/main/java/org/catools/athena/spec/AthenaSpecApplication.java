package org.catools.athena.spec;

import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(
    scanBasePackages = "org.catools.athena",
    exclude = {
        SpringDocDataRestConfiguration.class,
        SpringDocHateoasConfiguration.class
    }
)
public class AthenaSpecApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaSpecApplication.class, args);
  }

}
