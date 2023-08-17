package org.catools.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

@UtilityClass
public class CSecurityConfigs {

  public static void setMaskSensitiveData(boolean flag) {
    System.setProperty(Configs.CATOOLS_SECURITY_MASK_SENSITIVE_DATA.name(), Boolean.toString(flag));
  }

  public static boolean maskSensitiveData() {
    return CHocon.get(Configs.CATOOLS_SECURITY_MASK_SENSITIVE_DATA).asBoolean(true);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_SECURITY_MASK_SENSITIVE_DATA("catools.logger.mask_sensitive_data");

    private final String path;
  }
}
