package org.catools.athena.rest.feign.kube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KubeCliApplication {

  public static void main(String[] args) {
    SpringApplication.run(KubeCliApplication.class, args);
  }
}
