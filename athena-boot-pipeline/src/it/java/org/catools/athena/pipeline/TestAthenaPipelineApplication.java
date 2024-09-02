package org.catools.athena.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaPipelineApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaPipelineApplication::main)
        .with(TestAthenaPipelineApplication.class)
        .run(args);
  }
}
