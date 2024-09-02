package org.catools.athena.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAthenaGitApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(AthenaGitApplication::main)
        .with(TestAthenaGitApplication.class)
        .run(args);
  }
}
