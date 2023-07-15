package org.catools.web.drivers;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.common.utils.CObjectUtil;
import org.catools.web.config.CBrowserConfigs;
import org.catools.web.entities.CWebPageInfo;
import org.catools.web.listeners.CDriverListener;
import org.catools.web.utils.CWebDriverUtil;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Date;
import java.util.function.Function;

/**
 * Driver close the alert when it opens and we try to get Title or URL. So to avoid any impact on
 * flow we do not perform any session update and we do not call listeners
 */
@Slf4j
@Data
@Accessors(chain = true)
public class CDriverSession {
  private static final CWebPageInfo BLANK_PAGE = new CWebPageInfo();

  private final CList<CDriverListener> listeners = new CList<>();

  @Setter(AccessLevel.NONE)
  private CWebPageInfo previousPage = BLANK_PAGE;

  @Setter(AccessLevel.PRIVATE)
  private CWebPageInfo currentPage = BLANK_PAGE;

  @Setter(AccessLevel.PRIVATE)
  @Getter(AccessLevel.PRIVATE)
  private RemoteWebDriver webDriver;

  private CDriverProvider driverProvider;
  private Dimension windowsSize = CBrowserConfigs.getWindowsDimension();
  private Point windowsPosition = CBrowserConfigs.getWindowsPosition();

  public CDriverSession(CDriverProvider driverProvider) {
    this.driverProvider = driverProvider;
  }

  public void startSession() {
    setWebDriver(driverProvider.build(listeners));
    CWebDriverUtil.setDriverWindowsSize(webDriver, windowsPosition, windowsSize);
  }

  public <T> T performActionOnDriver(String actionName, Function<RemoteWebDriver, T> consumer) {
    if (webDriver == null) {
      log.trace("Cannot perform action on driver, web driver is not set.");
      return null;
    }

    if (listeners.isNotEmpty()) {
      listeners.forEach(event -> event.beforeAction(webDriver, currentPage, actionName));
    }

    if (noAlertPresent()) {
      updatePageInfo();
    }

    Date startTime = new Date();
    CWebPageInfo pageBeforeAction = CObjectUtil.clone(currentPage);
    T apply = consumer.apply(webDriver);

    if (noAlertPresent()) {
      updatePageInfo();
    }

    if (listeners.isNotEmpty()) {
      listeners.forEach(
          event ->
              event.afterAction(
                  actionName,
                  webDriver,
                  pageBeforeAction,
                  CObjectUtil.clone(currentPage),
                  startTime));
    }

    return apply;
  }

  public void reset() {
    this.previousPage = this.currentPage = BLANK_PAGE;
    this.webDriver = null;
  }

  public CDriverSession addListeners(CDriverListener... listeners) {
    this.listeners.addAll(CList.of(listeners).getAll(e -> e != null));
    return this;
  }

  public CDriverSession updatePageInfo() {
    if (webDriver == null) {
      previousPage = BLANK_PAGE;
      currentPage = BLANK_PAGE;
      return this;
    }

    CWebPageInfo tmpPage = getPageInfo();
    if (!BLANK_PAGE.equals(tmpPage) && !currentPage.equals(tmpPage)) {
      previousPage = currentPage;
      currentPage = tmpPage;
      onPageChangeEvents();
    }
    return this;
  }

  private CDriverSession onPageChangeEvents() {
    if (listeners.isNotEmpty()) {
      listeners.forEach(event -> event.onPageChanged(webDriver, previousPage, currentPage));
    }
    return this;
  }

  private CWebPageInfo getPageInfo() {
    try {
      return webDriver == null
          ? BLANK_PAGE
          : new CWebPageInfo(webDriver.getCurrentUrl(), webDriver.getTitle());
    } catch (Throwable t) {
      return BLANK_PAGE;
    }
  }

  private boolean noAlertPresent() {
    try {
      return webDriver != null && webDriver.switchTo().alert() == null;
    } catch (Throwable t) {
      return true;
    }
  }
}
