package org.catools.athena.spec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaSpecApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaSpecApplication::main)
        .with(TestAthenaSpecApplication.class)
        .run(args);
  }
}
