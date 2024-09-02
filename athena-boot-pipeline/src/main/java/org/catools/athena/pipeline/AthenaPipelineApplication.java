package org.catools.athena.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaPipelineApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaPipelineApplication.class, args);
  }

}
