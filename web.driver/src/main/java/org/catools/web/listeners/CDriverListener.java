package org.catools.web.listeners;

import org.catools.web.entities.CWebPageInfo;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Date;

public interface CDriverListener {
  default void beforeInit(Capabilities capabilities) {
  }

  default void afterInit(RemoteWebDriver remoteWebDriver) {
  }

  default void beforeAction(RemoteWebDriver webDriver, CWebPageInfo currentPage, String actionName) {
  }

  default void afterAction(
      String actionName,
      RemoteWebDriver webDriver,
      CWebPageInfo pageBeforeAction,
      CWebPageInfo pageAfterAction,
      Date startTime) {
  }

  default void onPageChanged(
      RemoteWebDriver webDriver,
      CWebPageInfo previousPage,
      CWebPageInfo currentPage) {
  }
}
