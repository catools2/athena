package org.catools.athena.metric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SpringBootApplication(scanBasePackages = "org.catools.athena")
public class AthenaMetricApplication {

  public static void main(String[] args) {
    SpringApplication.run(AthenaMetricApplication.class, args);
  }

}
