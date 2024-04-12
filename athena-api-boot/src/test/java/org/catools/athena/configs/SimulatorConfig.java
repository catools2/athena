package org.catools.athena.configs;

import org.catools.athena.core.common.config.CorePathDefinitions;

public class SimulatorConfig {

  public static String getPort() {
    return System.getProperty("APP_PORT");
  }

  public static String getApiHost() {
    return "http://localhost:" + getPort() + CorePathDefinitions.ROOT_API;
  }
}