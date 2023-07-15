package org.catools.extentreport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CExtentReportConfigs {

  public static String getExtentReportFileName() {
    return CHocon.asString(Configs.CATOOLS_EXTENT_REPORT_FILE_NAME);
  }

  public static String getExtentReportName() {
    return CHocon.asString(Configs.CATOOLS_EXTENT_REPORT_NAME);
  }

  public static boolean isEnable() {
    return CHocon.asBoolean(Configs.CATOOLS_EXTENT_REPORT_ENABLE);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_EXTENT_REPORT_ENABLE("catools.extent_report.enable"),
    CATOOLS_EXTENT_REPORT_NAME("catools.extent_report.name"),
    CATOOLS_EXTENT_REPORT_FILE_NAME("catools.extent_report.file_name");

    private final String path;
  }
}
