package org.catools.athena.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaGitApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaGitApplication.class, args);
  }

}
