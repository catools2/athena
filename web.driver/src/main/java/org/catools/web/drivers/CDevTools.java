package org.catools.web.drivers;

import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.web.config.CDriverConfigs;
import org.catools.web.config.CGridConfigs;
import org.catools.web.entities.CWebPageInfo;
import org.catools.web.metrics.CWebPageTransitionInfo;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.devtools.v119.log.Log;
import org.openqa.selenium.devtools.v119.performance.Performance;
import org.openqa.selenium.devtools.v119.performance.model.Metric;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Optional;

public class CDevTools {

  private DevTools devTools;

  public void startRecording(CDriverProvider driverProvider, RemoteWebDriver webDriver) {
    if (!isEnable()) return;

    init(driverProvider, webDriver);
    devTools.createSession();
    devTools.send(Log.disable());
    devTools.send(Performance.enable(Optional.empty()));
  }

  public synchronized CWebPageTransitionInfo stopRecording(CWebPageInfo previousPage, CWebPageInfo currentPage) {
    if (!isEnable())
      return new CWebPageTransitionInfo("Page Transition", previousPage, currentPage);

    try {
      CList<Metric> metricList = readPerformanceMetrics();
      return new CWebPageTransitionInfo("Page Transition", previousPage, currentPage, metricList, CDate.now());
    } catch (DevToolsException exception) {
      return null;
    }
  }

  private CList<Metric> readPerformanceMetrics() {
    if (devTools == null)
      return CList.of();

    try {
      return new CList<>(devTools.send(Performance.getMetrics()));
    } finally {
      devTools.disconnectSession();
    }
  }

  private void init(CDriverProvider driverProvider, RemoteWebDriver webDriver) {
    if (devTools != null) return;

    if (driverProvider.getBrowser().isEdge()) {
      devTools = ((EdgeDriver) webDriver).getDevTools();
    } else if (driverProvider.getBrowser().isFirefox()) {
      devTools = ((FirefoxDriver) webDriver).getDevTools();
    } else {
      devTools = ((ChromeDriver) webDriver).getDevTools();
    }
  }

  private boolean isEnable() {
    return CDriverConfigs.isCollectPerformanceMetricsEnable() && !CGridConfigs.isUseRemoteDriver() && !CGridConfigs.isUseTestContainer();
  }
}
