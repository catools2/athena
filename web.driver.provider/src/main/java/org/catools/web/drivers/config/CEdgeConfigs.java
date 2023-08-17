package org.catools.web.drivers.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.openqa.selenium.PageLoadStrategy;

import java.util.List;

@UtilityClass
public class CEdgeConfigs {

  public static String getBinaryPath() {
    return CHocon.asString(Configs.CATOOLS_WEB_EDGE_BINARY_PATH);
  }

  public static List<String> getDefaultArguments() {
    return CHocon.asStrings(Configs.CATOOLS_WEB_EDGE_DEFAULT_ARGUMENTS);
  }

  public static PageLoadStrategy getPageLoadStrategy() {
    return CHocon.asEnum(Configs.CATOOLS_WEB_EDGE_PAGE_LOAD_STRATEGY, PageLoadStrategy.class);
  }

  public static boolean isInHeadLessMode() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_EDGE_HEADLESS_ENABLE);
  }

  public static List<String> getHeadLessArguments() {
    return CHocon.asStrings(Configs.CATOOLS_WEB_EDGE_HEADLESS_ARGUMENTS);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_EDGE_BINARY_PATH("catools.web.edge.binary_path"),
    CATOOLS_WEB_EDGE_DEFAULT_ARGUMENTS("catools.web.edge.default_arguments"),
    CATOOLS_WEB_EDGE_PAGE_LOAD_STRATEGY("catools.web.edge.page_load_strategy"),
    CATOOLS_WEB_EDGE_HEADLESS_ENABLE("catools.web.edge.headless_enable"),
    CATOOLS_WEB_EDGE_HEADLESS_ARGUMENTS("catools.web.edge.headless_arguments");

    private final String path;
  }
}
