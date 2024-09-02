package org.catools.gatling;

import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import org.apache.logging.log4j.util.Strings;
import org.catools.athena.AthenaSpringBootIT;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public abstract class AthenaPerformanceIT extends AthenaSpringBootIT {

  @Test
  void performanceSanityCheck() {
    System.setProperty("APP_PORT", this.port + Strings.EMPTY);
    GatlingPropertiesBuilder props = new GatlingPropertiesBuilder();
    props.simulationClass(getSimulator());
    assertThat("Performance Sanity Check Passed Successfully For Core Api Spec", Gatling.fromMap(props.build()), equalTo(0));
  }

  protected abstract String getSimulator();

}