package org.catools.athena.kube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaKubeApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaKubeApplication.class, args);
  }

}
