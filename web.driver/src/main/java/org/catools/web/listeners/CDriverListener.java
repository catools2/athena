package org.catools.web.listeners;

import org.catools.common.date.CDate;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.entities.CWebPageInfo;
import org.catools.web.metrics.CWebPageTransitionInfo;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface CDriverListener {
  default void beforeInit(Capabilities capabilities) {
  }

  default void afterInit(CDriverProvider driverProvider, RemoteWebDriver remoteWebDriver) {
  }

  default void beforeAction(RemoteWebDriver webDriver, CWebPageInfo currentPage, String actionName) {
  }

  default void afterAction(
      String actionName,
      RemoteWebDriver webDriver,
      CWebPageInfo pageBeforeAction,
      CWebPageInfo pageAfterAction,
      CWebPageTransitionInfo driverMetricInfo,
      CDate startTime,
      long durationInNano) {
  }

  default void onPageChanged(
      RemoteWebDriver webDriver,
      CWebPageTransitionInfo driverMetricInfo,
      CDate startTime,
      long durationInNano) {
  }
}
