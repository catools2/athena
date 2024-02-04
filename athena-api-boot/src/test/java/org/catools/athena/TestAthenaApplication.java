package org.catools.athena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaApplication::main)
        .with(TestAthenaApplication.class)
        .run(args);
  }
}
