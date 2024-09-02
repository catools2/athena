package org.catools.gatling.configs;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SimulatorConfig {

  public static String getPort() {
    return System.getProperty("APP_PORT");
  }

  public static String getApiHost() {
    return "http://localhost:" + getPort();
  }

}