package org.catools.athena.spec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaSpecApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaSpecApplication.class, args);
  }

}
