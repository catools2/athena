package org.catools.athena.rest.feign.common.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FeignConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static int connectTimeout;

  @Setter
  @Getter
  private static int readTimeout;

  public static void reload() {
    connectTimeout = ConfigUtils.getInteger("athena.feign.connect_timeout", 90);
    readTimeout = ConfigUtils.getInteger("athena.feign.read_timeout", 900);
  }

}
