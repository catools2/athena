package org.catools.athena.core;

import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SpringBootApplication(
    scanBasePackages = "org.catools.athena",
    exclude = {
        SpringDocDataRestConfiguration.class,
        SpringDocHateoasConfiguration.class
    }
)
public class AthenaCoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaCoreApplication.class, args);
  }

}
