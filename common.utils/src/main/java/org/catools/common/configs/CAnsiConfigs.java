package org.catools.common.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CAnsiUtil;

public class CAnsiConfigs {
  public static boolean isPrintInAnsiEnable() {
    return CHocon.asBoolean(Configs.CATOOLS_CONSOLE_PRINT_IN_ANSI_ENABLE);
  }

  public static boolean isPrintInColorAvailable() {
    return CAnsiUtil.isOutputSupportAnsi() && isPrintInAnsiEnable();
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_CONSOLE_PRINT_IN_ANSI_ENABLE("catools.console.print_in_ansi_enable");

    private final String path;
  }
}
