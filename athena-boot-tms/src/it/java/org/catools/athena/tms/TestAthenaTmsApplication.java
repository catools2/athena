package org.catools.athena.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaTmsApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaTmsApplication::main)
        .with(TestAthenaTmsApplication.class)
        .run(args);
  }
}
