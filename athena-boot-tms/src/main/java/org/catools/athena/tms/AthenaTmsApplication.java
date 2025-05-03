package org.catools.athena.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaTmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaTmsApplication.class, args);
  }

}
