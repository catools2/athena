package org.catools.athena.metric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaMetricApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaMetricApplication::main)
        .with(TestAthenaMetricApplication.class)
        .run(args);
  }
}
