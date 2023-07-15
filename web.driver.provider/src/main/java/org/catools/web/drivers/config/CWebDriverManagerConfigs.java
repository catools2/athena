package org.catools.web.drivers.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CWebDriverManagerConfigs {
  public static boolean isEnabled() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_DRIVER_MANAGER_ENABLED);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_DRIVER_MANAGER_ENABLED("catools.web.driver_manager.enabled");

    private final String path;
  }
}
