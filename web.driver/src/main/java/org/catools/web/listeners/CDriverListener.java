package org.catools.web.listeners;

import org.catools.common.date.CDate;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.entities.CWebPageInfo;
import org.catools.web.metrics.CWebPageTransitionInfo;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Driver listener to manage event handling before and after driver initialization
 * and before and after each driver action (click, getText,...).
 */
public interface CDriverListener {
  /**
   * Triggers before driver initialization
   *
   * @param capabilities
   */
  default void beforeInit(Capabilities capabilities) {
  }

  /**
   * Triggers after driver initialization
   *
   * @param driverProvider
   * @param remoteWebDriver
   */
  default void afterInit(CDriverProvider driverProvider, RemoteWebDriver remoteWebDriver) {
  }

  /**
   * Triggers before each interaction with driver
   *
   * @param webDriver
   * @param currentPage
   * @param actionName
   */
  default void beforeAction(RemoteWebDriver webDriver, CWebPageInfo currentPage, String actionName) {
  }

  /**
   * Triggers after each interaction with driver
   *
   * @param actionName
   * @param webDriver
   * @param pageBeforeAction
   * @param pageAfterAction
   * @param driverMetricInfo
   * @param startTime
   * @param durationInNano
   */
  default void afterAction(
      String actionName,
      RemoteWebDriver webDriver,
      CWebPageInfo pageBeforeAction,
      CWebPageInfo pageAfterAction,
      CWebPageTransitionInfo driverMetricInfo,
      CDate startTime,
      long durationInNano) {
  }

  /**
   * Triggers on after page transaction
   *
   * @param webDriver
   * @param driverMetricInfo
   * @param startTime
   * @param durationInNano
   */
  default void onPageChanged(
      RemoteWebDriver webDriver,
      CWebPageTransitionInfo driverMetricInfo,
      CDate startTime,
      long durationInNano) {
  }
}
