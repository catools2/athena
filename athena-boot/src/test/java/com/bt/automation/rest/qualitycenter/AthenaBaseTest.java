package com.bt.automation.rest.qualitycenter;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AthenaBaseTest.SpringTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AthenaBaseTest {

  @Configuration
  @ComponentScan({"org.catools.athena"})
  @PropertySource("classpath:application.properties")
  public static class SpringTestConfig {
  }

}