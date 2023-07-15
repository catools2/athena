package org.catools.web.utils;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebDriver;

@UtilityClass
public class CWebDriverUtil {

  public static RemoteWebDriver setDriverWindowsSize(
      RemoteWebDriver webDriver, Point windowsPosition, Dimension windowsSize) {
    try {
      if (windowsPosition != null) {
        webDriver.manage().window().setPosition(windowsPosition);
      } else {
        webDriver.manage().window().setPosition(new Point(0, 0));
      }

      if (windowsSize != null) {
        webDriver.manage().window().setSize(windowsSize);
      } else {
        webDriver.manage().window().maximize();
      }
    } catch (Throwable t) {
      if (webDriver != null) {
        webDriver.quit();
      }
      throw t;
    }
    return webDriver;
  }
}
