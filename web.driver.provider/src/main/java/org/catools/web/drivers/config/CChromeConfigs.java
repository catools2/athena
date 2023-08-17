package org.catools.web.drivers.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.openqa.selenium.PageLoadStrategy;

import java.util.List;

@UtilityClass
public class CChromeConfigs {

  public static String getBinaryPath() {
    return CHocon.asString(Configs.CATOOLS_WEB_CHROME_BINARY_PATH);
  }

  public static List<String> getDefaultArguments() {
    return CHocon.asStrings(Configs.CATOOLS_WEB_CHROME_DEFAULT_ARGUMENTS);
  }

  public static PageLoadStrategy getPageLoadStrategy() {
    return CHocon.asEnum(Configs.CATOOLS_WEB_CHROME_PAGE_LOAD_STRATEGY, PageLoadStrategy.class);
  }

  public static List<String> getPluginsToDisable() {
    return CHocon.asStrings(Configs.CATOOLS_WEB_CHROME_PLUGINS_TO_DISABLE);
  }

  public static List<String> getPluginsToEnable() {
    return CHocon.asStrings(Configs.CATOOLS_WEB_CHROME_PLUGINS_TO_ENABLE);
  }

  public static boolean isInHeadLessMode() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_CHROME_HEADLESS_ENABLE);
  }

  public static List<String> getHeadLessArguments() {
    return CHocon.asStrings(Configs.CATOOLS_WEB_CHROME_HEADLESS_ARGUMENTS);
  }

  public static String getChromeMobileEmulationDeviceName() {
    return CHocon.asString(Configs.CATOOLS_WEB_CHROME_MOBILE_EMULATION_DEVICE_NAME);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_CHROME_BINARY_PATH("catools.web.chrome.binary_path"),
    CATOOLS_WEB_CHROME_DEFAULT_ARGUMENTS("catools.web.chrome.default_arguments"),
    CATOOLS_WEB_CHROME_PAGE_LOAD_STRATEGY("catools.web.chrome.page_load_strategy"),
    CATOOLS_WEB_CHROME_PLUGINS_TO_DISABLE("catools.web.chrome.plugins_to_disable"),
    CATOOLS_WEB_CHROME_PLUGINS_TO_ENABLE("catools.web.chrome.plugins_to_enable"),
    CATOOLS_WEB_CHROME_HEADLESS_ENABLE("catools.web.chrome.headless_enable"),
    CATOOLS_WEB_CHROME_HEADLESS_ARGUMENTS("catools.web.chrome.headless_arguments"),
    CATOOLS_WEB_CHROME_MOBILE_EMULATION_DEVICE_NAME("catools.web.chrome.mobile_emulation_device_name");

    private final String path;
  }
}
