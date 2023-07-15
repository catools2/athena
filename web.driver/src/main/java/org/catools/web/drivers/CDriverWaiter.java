package org.catools.web.drivers;

import org.catools.common.utils.CRetry;
import org.catools.web.config.CDriverConfigs;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.function.Function;

public interface CDriverWaiter {
  int DEFAULT_TIMEOUT = CDriverConfigs.getTimeout();

  CDriverSession getDriverSession();

  default <C> C waitUntil(String actionName, ExpectedCondition<C> condition) {
    return waitUntil(actionName, DEFAULT_TIMEOUT, condition);
  }

  default <C> C waitUntil(String actionName, int waitSec, ExpectedCondition<C> condition) {
    if (CDriverConfigs.waitCompleteReadyStateBeforeEachAction()) {
      waitCompleteReadyState();
    }

    C result = performActionOnDriver(
        actionName,
        webDriver -> getWebDriverWait(webDriver, waitSec).until(condition::apply));

    if (CDriverConfigs.waitCompleteReadyStateAfterEachAction()) {
      waitCompleteReadyState();
    }

    return result;
  }

  default <C> C waitUntil(String actionName, C defaultTo, ExpectedCondition<C> condition) {
    return waitUntil(actionName, DEFAULT_TIMEOUT, defaultTo, condition);
  }

  default <C> C waitUntil(String actionName, int waitSec, C defaultTo, ExpectedCondition<C> condition) {
    try {
      if (CDriverConfigs.waitCompleteReadyStateBeforeEachAction()) {
        waitCompleteReadyState();
      }

      C result = performActionOnDriver(
          actionName,
          webDriver -> getWebDriverWait(webDriver, waitSec).until(condition::apply));

      if (CDriverConfigs.waitCompleteReadyStateAfterEachAction()) {
        waitCompleteReadyState();
      }

      return result;
    } catch (TimeoutException t) {
      return defaultTo;
    }
  }

  default <T> T performActionOnDriver(String actionName, Function<RemoteWebDriver, T> consumer) {
    return getDriverSession().performActionOnDriver(actionName, consumer);
  }

  // Waiters
  default boolean waitCompleteReadyState() {
    return waitCompleteReadyState(DEFAULT_TIMEOUT, 100);
  }

  default boolean waitCompleteReadyState(int waitSec, int interval) {
    return CRetry.retryIfFalse(
        idx -> (Boolean) performActionOnDriver(
            "Wait Complete Ready State",
            webDriver -> webDriver.executeScript("return document.readyState === 'complete'")
        ),
        waitSec,
        interval,
        () -> false);
  }

  // private
  private Wait<RemoteWebDriver> getWebDriverWait(RemoteWebDriver webDriver, int waitSec) {
    return new FluentWait<>(webDriver)
        .withTimeout(Duration.ofSeconds(waitSec))
        .pollingEvery(Duration.ofMillis(100))
        .ignoring(StaleElementReferenceException.class)
        .ignoring(InvalidElementStateException.class)
        .ignoring(NoSuchElementException.class)
        .ignoring(NoSuchSessionException.class)
        .ignoring(NoSuchWindowException.class)
        .ignoring(NoSuchFrameException.class)
        .ignoring(WebDriverException.class)
        .ignoring(TimeoutException.class)
        .ignoring(AssertionError.class);
  }
}
