package org.catools.web.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.io.CFile;
import org.catools.web.enums.CBrowser;
import org.catools.web.utils.CGridUtil;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.SessionId;

import java.util.Objects;

@UtilityClass
public class CBrowserConfigs {

  public static CFile getScreenShotsFolder() {
    return CFile.of(CPathConfigs.getOutputChildFolder("screenshots"));
  }

  public static CBrowser getCurrentBrowser() {
    return CHocon.asEnum(Configs.CATOOLS_WEB_BROWSER_DEFAULT, CBrowser.class);
  }

  public static CFile getDownloadFolder(SessionId sessionId) {
    CFile downloadedFile;
    if (CGridConfigs.isUseRemoteDriver() && !CGridConfigs.isUseHubFolderModeIsOn()) {
      Objects.requireNonNull(sessionId);
      String[] hostNameAndPort = CGridUtil.getHostNameAndPort(sessionId);
      Objects.requireNonNull(hostNameAndPort);
      downloadedFile = CFile.fromRemote(hostNameAndPort[0], CPathConfigs.getUserDownloadFolder());
    } else {
      downloadedFile = CFile.of(CPathConfigs.getUserDownloadFolder());
    }
    if (!downloadedFile.exists()) {
      downloadedFile.mkdirs();
    }
    return downloadedFile;
  }

  public static Point getWindowsPosition() {
    String size = CHocon.asString(Configs.CATOOLS_WEB_BROWSER_WINDOWS_POSITION);
    if (StringUtils.isBlank(size)) {
      return null;
    }
    String[] split = size.split(",");
    return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
  }

  public static Dimension getWindowsDimension() {
    String size = CHocon.asString(Configs.CATOOLS_WEB_BROWSER_WINDOWS_DIMENSION);
    if (StringUtils.isBlank(size)) {
      return null;
    }
    String[] split = size.split(",");
    return new Dimension(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_BROWSER_DEFAULT("catools.web.browser.default"),
    CATOOLS_WEB_BROWSER_WINDOWS_POSITION("catools.web.browser.windows_position"),
    CATOOLS_WEB_BROWSER_WINDOWS_DIMENSION("catools.web.browser.windows_dimension");

    private final String path;
  }
}
