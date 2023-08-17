package org.catools.web.axe.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

@UtilityClass
public class CWebAxeConfigs {
  public static boolean isAxeAnalyserEnable() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_AXE_ANALYSER_ENABLE);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_AXE_ANALYSER_ENABLE("catools.web.axe_analyser_enable");

    private final String path;
  }
}
