package org.catools.ws.rest.tests.api.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.experimental.UtilityClass;

/**
 * A utility class for configuration related method like load configuration and read values
 */
@UtilityClass
public class ConfigUtils {
  private static Config CONFIG = ConfigFactory.load();

  /**
   * Get url for the OMD end point
   *
   * @return OMD api end-point
   */
  public static String getApiHost() {
    return CONFIG.getString("catools.api.host");
  }

  /**
   * Get the API Key for the OMD end point
   *
   * @return the api key for the OMD api end-point
   */
  public static String getApiKey() {
    return CONFIG.getString("catools.api.key");
  }
}
