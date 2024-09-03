package org.catools.athena.metric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaMetricApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaMetricApplication.class, args);
  }

}
