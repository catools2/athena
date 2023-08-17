package org.catools.common.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CAnsiUtil;
import org.catools.common.utils.CFileUtil;

import static org.catools.common.configs.CPathConfigs.getOutputChildFolder;

@UtilityClass
public class CLoggerConfigs {

  public static boolean logColoredOutput() {
    return isOutputSupportAnsi() && CHocon.asBoolean(Configs.CATOOLS_LOGGER_LOG_COLORED);
  }

  public static boolean isOutputSupportAnsi() {
    return CAnsiUtil.isConsoleAvailable() || CAnsiUtil.isSystemOutSupportAnsi() || isXterm();
  }

  public static boolean isXterm() {
    String term = System.getenv("TERM");
    return term != null && term.toLowerCase().startsWith("xterm");
  }

  public static String getLogFolderPath() {
    return CFileUtil.getCanonicalPath(getOutputChildFolder("log"));
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_LOGGER_LOG_COLORED("catools.logger.log_colored");

    private final String path;
  }
}
