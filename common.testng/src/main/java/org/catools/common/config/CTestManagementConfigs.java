package org.catools.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CStringUtil;

public class CTestManagementConfigs {
  public static String getProjectName() {
    return CHocon.asString(Configs.CATOOLS_TMS_PROJECT_NAME);
  }

  public static String getVersionName() {
    return CHocon.asString(Configs.CATOOLS_TMS_VERSION_NAME);
  }

  public static String getUrlToTest() {
    return CHocon.asString(Configs.CATOOLS_TMS_URL_FORMAT_TO_TEST);
  }

  public static String getUrlToTest(String testKey) {
    String string = getUrlToTest();
    return CStringUtil.isBlank(string) ? CStringUtil.EMPTY : CStringUtil.format(string, testKey);
  }

  public static String getUrlToDefect() {
    return CHocon.asString(Configs.CATOOLS_TMS_URL_FORMAT_TO_DEFECT);
  }

  public static String getUrlToDefect(String testKey) {
    String string = getUrlToDefect();
    return CStringUtil.isBlank(string) ? CStringUtil.EMPTY : CStringUtil.format(string, testKey);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_TMS_URL_FORMAT_TO_DEFECT("catools.tms.url_format_to_defect"),
    CATOOLS_TMS_URL_FORMAT_TO_TEST("catools.tms.url_format_to_test"),
    CATOOLS_TMS_PROJECT_NAME("catools.tms.project_name"),
    CATOOLS_TMS_VERSION_NAME("catools.tms.version_name");

    private final String path;
  }
}
