package org.catools.athena.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaCoreApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaCoreApplication::main)
        .with(TestAthenaCoreApplication.class)
        .run(args);
  }
}
