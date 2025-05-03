package org.catools.athena.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaCoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaCoreApplication.class, args);
  }

}
