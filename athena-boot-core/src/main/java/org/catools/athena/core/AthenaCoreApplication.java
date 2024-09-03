package org.catools.athena.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class AthenaCoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaCoreApplication.class, args);
  }

}
