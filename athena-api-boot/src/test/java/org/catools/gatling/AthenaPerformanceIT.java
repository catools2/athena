package org.catools.gatling;

import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import org.catools.athena.core.controller.CoreControllerIT;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AthenaPerformanceIT extends CoreControllerIT {

  @Test
  @Order(1)
  void performanceSanityCheck_ApiSpec() {
    System.setProperty("APP_PORT", this.port + "");
    GatlingPropertiesBuilder props = new GatlingPropertiesBuilder();
    props.simulationClass(AthenaSimulator.class.getName());
    assertThat("Performance Sanity Check Passed Successfully For Api Spec", Gatling.fromMap(props.build()), equalTo(0));
  }

}