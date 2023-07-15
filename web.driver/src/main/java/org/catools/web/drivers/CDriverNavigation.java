package org.catools.web.drivers;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.utils.CRetry;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface CDriverNavigation extends CDriverWaiter {
  default <T extends CDriverNavigation> T back() {
    return performActionOnDriver(
        "Back",
        webDriver -> {
          webDriver.navigate().back();
          return (T) this;
        });
  }

  default <T extends CDriverNavigation> T back(Predicate<CDriverNavigation> postCondition) {
    return back(postCondition, 3, 1000);
  }

  default <T extends CDriverNavigation> T back(
      Predicate<CDriverNavigation> postCondition, int retryTimes, int intervalInSeconds) {
    CRetry.retryIfNot(integer -> back(), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default <T extends CDriverNavigation> T forward() {
    return performActionOnDriver(
        "Forward",
        webDriver -> {
          webDriver.navigate().forward();
          return (T) this;
        });
  }

  default <T extends CDriverNavigation> T forward(Predicate<CDriverNavigation> postCondition) {
    return forward(postCondition, 3, 1000);
  }

  default <T extends CDriverNavigation> T forward(
      Predicate<CDriverNavigation> postCondition, int retryTimes, int intervalInSeconds) {
    CRetry.retryIfNot(integer -> forward(), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default void switchToPage(String title) {
    performActionOnDriver(
        "Switch To Page",
        webDriver -> {
          if (!webDriver.getTitle().matches("^" + title + "$")) {
            for (String window : webDriver.getWindowHandles()) {
              if (webDriver.switchTo().window(window).getTitle().matches("^" + title + "$")) {
                break;
              }
            }
          }
          return true;
        });
  }

  default void switchToFirstPage() {
    performActionOnDriver(
        "Switch To First Page",
        webDriver -> {
          webDriver.switchTo().window(new ArrayList<>(webDriver.getWindowHandles()).get(0));
          return true;
        });
  }

  default void switchToLastPage() {
    performActionOnDriver(
        "Switch To Last Page",
        webDriver -> {
          Iterator<String> iterator = webDriver.getWindowHandles().iterator();
          String lastHandle = "";
          while (iterator.hasNext()) {
            lastHandle = iterator.next();
          }
          webDriver.switchTo().window(lastHandle);
          return true;
        });
  }

  default void switchToNextPage() {
    performActionOnDriver(
        "Switch To Next Page",
        webDriver -> {
          if (webDriver.getWindowHandles().size() == 1) {
            return false;
          }
          String currentTitle = webDriver.getTitle();
          for (String window : webDriver.getWindowHandles()) {
            if (!webDriver.switchTo().window(window).getTitle().equals(currentTitle)) {
              break;
            }
          }
          return true;
        });
  }

  default void switchToFrame(String frameName) {
    switchToFrame(frameName, DEFAULT_TIMEOUT);
  }

  default void switchToFrame(String frameName, int waitSec) {
    waitUntil("Switch To Frame", waitSec, webDriver -> webDriver.switchTo().frame(frameName));
  }

  default void switchToFrame(By locator) {
    switchToFrame(locator, DEFAULT_TIMEOUT);
  }

  default void switchToFrame(By locator, int waitSec) {
    waitUntil(
        "Switch To Frame", waitSec, ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
  }

  default void switchToFrame(int frameIndex) {
    switchToFrame(frameIndex, DEFAULT_TIMEOUT);
  }

  default void switchToFrame(int frameIndex, int waitSec) {
    waitUntil(
        "Switch To Frame", waitSec, ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
  }

  default void switchToDefaultContent() {
    performActionOnDriver(
        "Switch To Default Content",
        webDriver -> {
          webDriver.switchTo().defaultContent();
          return true;
        });
  }

  default void open(String url) {
    open(url, null);
  }

  default <P> P open(String url, Supplier<P> expectedPage) {
    CVerify.String.isNotBlank(url, "URL is not blank.");
    if (!isSessionStarted()) getDriverSession().startSession();
    return performActionOnDriver(
        "Open",
        webDriver -> {
          webDriver.navigate().to(url);
          return expectedPage == null ? null : expectedPage.get();
        });
  }

  private boolean isSessionStarted() {
    return performActionOnDriver("Open", Objects::nonNull) != null;
  }
}
