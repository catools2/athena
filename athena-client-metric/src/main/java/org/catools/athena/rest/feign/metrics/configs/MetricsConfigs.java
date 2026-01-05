package org.catools.athena.rest.feign.metrics.configs;

import lombok.Getter;
import lombok.Setter;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;

public class MetricsConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static boolean enable;

  public static void reload() {
    enable = ConfigUtils.getBoolean("athena.metrics.enable", true);
  }
}
