package org.catools.athena.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(AthenaGatewayApplication.class, args);
  }
}
