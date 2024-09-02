package org.catools.athena.kube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaKubeApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaKubeApplication::main)
        .with(TestAthenaKubeApplication.class)
        .run(args);
  }
}
