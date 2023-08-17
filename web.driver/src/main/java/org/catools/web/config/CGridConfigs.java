package org.catools.web.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

@Slf4j
@UtilityClass
public class CGridConfigs {

  public static boolean isUseRemoteDriver() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_GRID_USE_REMOTE_DRIVER);
  }

  public static URL getHubURL() {
    String hubUrl = CHocon.get(Configs.CATOOLS_WEB_GRID_HUB_URL).asString(null);
    try {
      if (StringUtils.isNotBlank(hubUrl)) {
        return new URL(hubUrl);
      }

      if (!StringUtils.isBlank(getGridHubIP())) {
        return new URL(String.format("http://%s:%s/wd/hub", getGridHubIP(), getGridHubPort()));
      }
    } catch (MalformedURLException e) {
      log.error("Failed to get hub url", e);
    }
    return null;
  }

  public static int getGridHubPort() {
    return CHocon.asInteger(Configs.CATOOLS_WEB_GRID_HUB_PORT);
  }

  public static String getGridHubIP() {
    return CHocon.asString(Configs.CATOOLS_WEB_GRID_HUB_IP);
  }

  public static boolean isUseHubFolderModeIsOn() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_GRID_USE_HUB_FOLDERS);
  }

  public static boolean isUseLocalFileDetectorInOn() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_GRID_USE_LOCAL_FILE_DETECTOR);
  }

  public static Level getLogLevel() {
    return Level.parse(CHocon.asString(Configs.CATOOLS_WEB_GRID_LOG_LEVEL));
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_GRID_HUB_URL("catools.web.grid.hub.url"),
    CATOOLS_WEB_GRID_HUB_IP("catools.web.grid.hub.ip"),
    CATOOLS_WEB_GRID_HUB_PORT("catools.web.grid.hub.port"),
    CATOOLS_WEB_GRID_LOG_LEVEL("catools.web.grid.log_level"),
    CATOOLS_WEB_GRID_USE_REMOTE_DRIVER("catools.web.grid.use_remote_driver"),
    CATOOLS_WEB_GRID_USE_HUB_FOLDERS("catools.web.grid.use_hub_folders"),
    CATOOLS_WEB_GRID_USE_LOCAL_FILE_DETECTOR("catools.web.grid.use_local_file_detector");

    private final String path;
  }
}
