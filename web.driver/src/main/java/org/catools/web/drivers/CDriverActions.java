package org.catools.web.drivers;

import org.catools.common.utils.CRetry;
import org.catools.common.utils.CSleeper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;
import java.util.function.Predicate;

public interface CDriverActions extends CDriverWaiter {
  default <T extends CDriverActions> T pressEnter() {
    return performActionOnDriver(
        "Press ENTER",
        webDriver -> {
          new Actions(webDriver).sendKeys(Keys.ENTER).perform();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T pressEnter(int waitAfterPressEnter) {
    pressEnter();
    CSleeper.sleepTightInSeconds(waitAfterPressEnter);
    return (T) this;
  }

  default <T extends CDriverActions> T pressEnter(Predicate<CDriverActions> postCondition) {
    return pressEnter(postCondition, 3, 1000);
  }

  default <T extends CDriverActions> T pressEnter(
      Predicate<CDriverActions> postCondition, int retryTimes, int intervalInSeconds) {
    CRetry.retryIfNot(integer -> pressEnter(), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default <T extends CDriverActions> T pressEscape() {
    return performActionOnDriver(
        "Press Scape",
        webDriver -> {
          new Actions(webDriver).sendKeys(Keys.ESCAPE).perform();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T pressEscape(int waitAfterPressEscape) {
    pressEscape();
    CSleeper.sleepTightInSeconds(waitAfterPressEscape);
    return (T) this;
  }

  default <T extends CDriverActions> T pressEscape(Predicate<CDriverActions> postCondition) {
    return pressEscape(postCondition, 3, 1000);
  }

  default <T extends CDriverActions> T pressEscape(
      Predicate<CDriverActions> postCondition, int retryTimes, int intervalInSeconds) {
    CRetry.retryIfNot(integer -> pressEscape(), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default <T extends CDriverActions> T pressTab() {
    return performActionOnDriver(
        "Press Tab",
        webDriver -> {
          new Actions(webDriver).sendKeys(Keys.TAB).perform();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T pressTab(int waitAfterPressTab) {
    pressTab();
    CSleeper.sleepTightInSeconds(waitAfterPressTab);
    return (T) this;
  }

  default <T extends CDriverActions> T pressTab(Predicate<CDriverActions> postCondition) {
    return pressTab(postCondition, 3, 1000);
  }

  default <T extends CDriverActions> T pressTab(
      Predicate<CDriverActions> postCondition, int retryTimes, int intervalInSeconds) {
    CRetry.retryIfNot(integer -> pressTab(), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default <T extends CDriverActions> T clickJS(By locator, int waitSec) {
    waitUntil(
        "Click",
        waitSec,
        webDriver -> {
          WebElement el = webDriver.findElement(locator);
          if (el == null) return el;
          JavascriptExecutor executor = (JavascriptExecutor) webDriver;
          executor.executeScript("arguments[0].click();", el);
          return el;
        });
    return (T) this;
  }

  default <T extends CDriverActions> T click(By locator, int waitSec) {
    waitUntil(
        "Click",
        waitSec,
        webDriver -> {
          WebElement el = webDriver.findElement(locator);
          if (el == null) return el;
          JavascriptExecutor executor = (JavascriptExecutor) webDriver;
          try {
            executor.executeScript("arguments[0].scrollIntoView(true);", el);
          } catch (Throwable t) {
          }
          el.click();
          return el;
        });
    return (T) this;
  }

  default <T extends CDriverActions> T click(
      By locator, int waitSec, Predicate<CDriverActions> postCondition) {
    return click(locator, waitSec, postCondition, 3, 1000);
  }

  default <T extends CDriverActions> T click(
      By locator,
      int waitSec,
      Predicate<CDriverActions> postCondition,
      int retryTimes,
      int intervalInSeconds) {
    CRetry.retryIfNot(
        integer -> click(locator, waitSec), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default <T extends CDriverActions> T mouseClick(By locator) {
    return mouseClick(locator, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T mouseClick(By locator, int waitSec) {
    return performActionOnDriver(
        "Mouse Click",
        webDriver -> {
          moveTo(locator, waitSec);
          new Actions(webDriver).click();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T mouseDoubleClick(By locator) {
    return mouseDoubleClick(locator, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T mouseDoubleClick(By locator, int waitSec) {
    return performActionOnDriver(
        "Double Mouse Click",
        webDriver -> {
          moveTo(locator, waitSec);
          new Actions(webDriver).doubleClick();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T moveTo(By locator) {
    return moveTo(locator, 0, 0, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T moveTo(By locator, int xOffset, int yOffset) {
    return moveTo(locator, xOffset, yOffset, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T moveTo(By locator, int waitSec) {
    return moveTo(locator, 0, 0, waitSec);
  }

  default <T extends CDriverActions> T moveTo(By locator, int xOffset, int yOffset, int waitSec) {
    moveToElement(locator, xOffset, yOffset, waitSec);
    return (T) this;
  }

  default <T extends CDriverActions> T scrollIntoView(By locator, boolean scrollDown) {
    return scrollIntoView(locator, scrollDown, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T scrollIntoView(By locator, boolean scrollDown, int waitSec) {
    executeScript("arguments[0].scrollIntoView(" + scrollDown + ");", getElement(locator, waitSec));
    return (T) this;
  }

  default <T extends CDriverActions> T scrollIntoViewAndClick(By locator, boolean scrollDown) {
    return scrollIntoViewAndClick(locator, scrollDown, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T scrollIntoViewAndClick(
      By locator, boolean scrollDown, int waitSec) {
    scrollIntoView(locator, scrollDown, waitSec);
    return click(locator, waitSec);
  }

  default <T extends CDriverActions> T clickInvisible(By locator, int waitSec) {
    waitUntil(
        "Click Invisible",
        waitSec,
        webDriver -> {
          WebElement el = webDriver.findElement(locator);
          if (el != null && el.isEnabled()) {
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", el);
            return el;
          }
          return null;
        });
    return (T) this;
  }

  default <T extends CDriverActions> T clickInvisible(
      By locator, int waitSec, Predicate<CDriverActions> postCondition) {
    return clickInvisible(locator, waitSec, postCondition, 3, 1000);
  }

  default <T extends CDriverActions> T clickInvisible(
      By locator,
      int waitSec,
      Predicate<CDriverActions> postCondition,
      int retryTimes,
      int intervalInSeconds) {
    CRetry.retryIfNot(
        integer -> clickInvisible(locator, waitSec), postCondition, retryTimes, intervalInSeconds);
    return (T) this;
  }

  default <T extends CDriverActions> T sendKeys(int loopCount, CharSequence... keysToSend) {
    return performActionOnDriver(
        "Send Keys",
        webDriver -> {
          Actions actions = new Actions(webDriver);

          for (int i = 0; i < loopCount; ++i) {
            actions = actions.sendKeys(keysToSend);
          }

          actions.perform();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T sendKeys(
      By locator, int waitSec, CharSequence... keysToSend) {
    return waitUntil(
        "Send Keys",
        waitSec,
        webDriver -> {
          WebElement el = webDriver.findElement(locator);
          if (el == null || !el.isDisplayed() || !el.isEnabled()) {
            return null;
          }
          el.sendKeys(keysToSend);
          return (T) this;
        });
  }

  default <T extends CDriverActions> T sendKeys(CharSequence... keysToSend) {
    return performActionOnDriver(
        "Send Keys",
        webDriver -> {
          new Actions(webDriver).sendKeys(keysToSend).perform();
          return (T) this;
        });
  }

  default <T extends CDriverActions> T deleteAllCookies() {
    return performActionOnDriver(
        "Delete All Cookies",
        webDriver -> {
          webDriver.manage().deleteAllCookies();
          return (T) this;
        });
  }

  default Cookie getCookie(String name) {
    return performActionOnDriver(
        "Get Cookie", webDriver -> webDriver.manage().getCookieNamed(name));
  }

  default Set<Cookie> getCookies() {
    return performActionOnDriver("Get Cookies", webDriver -> webDriver.manage().getCookies());
  }

  default Cookie addCookie(String name, String value) {
    return addCookie(new Cookie(name, value));
  }

  default Cookie addCookie(Cookie cookie) {
    return performActionOnDriver(
        "Add Cookie",
        webDriver -> {
          webDriver.manage().addCookie(cookie);
          return cookie;
        });
  }

  default <T extends CDriverActions> T setCaretColorForAllInputs(String color) {
    return setStyleForAll("input", "caret-color", color);
  }

  default <T extends CDriverActions> T setStyleForAll(String xpath, String style, String value) {
    executeScript(
        String.format(
            "document.querySelectorAll(\"%s\").forEach(function(a) {\n"
                + "  a.style[\"%s\"]=\"%s\";\n"
                + "});",
            xpath, style, value));
    return (T) this;
  }

  default <R> R executeScript(String script, Object... args) {
    return performActionOnDriver(
        "Execute Script",
        webDriver -> {
          return (R) ((JavascriptExecutor) webDriver).executeScript(script, args);
        });
  }

  default <R> R executeScript(By locator, int waitSec, String script) {
    WebElement elem = waitUntil("Execute Script", waitSec, webDriver -> webDriver.findElement(locator));
    return executeScript(script, elem);

  }

  default <R> R executeAsyncScript(String script, Object... args) {
    return performActionOnDriver(
        "Execute Async Script",
        webDriver -> (R) ((JavascriptExecutor) webDriver).executeAsyncScript(script, args));
  }

  default <R> R executeAsyncScript(By locator, int waitSec, String script) {
    return waitUntil(
        "Execute Async Script",
        waitSec,
        webDriver -> {
          WebElement el = webDriver.findElement(locator);
          if (el == null) {
            return null;
          }
          return (R) ((JavascriptExecutor) webDriver).executeAsyncScript(script, el);
        });
  }

  default <T extends CDriverActions> T scrollBy(int x, int y) {
    executeScript("window.scrollBy(" + x + "," + y + ")");
    return (T) this;
  }

  default <T extends CDriverActions> T focus(final By locator) {
    return focus(locator, DEFAULT_TIMEOUT);
  }

  default <T extends CDriverActions> T focus(final By locator, int waitSec) {
    waitUntil(
        "Focus",
        waitSec,
        webDriver -> {
          WebElement el = webDriver.findElement(locator);
          if (el == null) {
            return null;
          }
          focus(el);
          return el;
        });
    return (T) this;
  }

  default <T extends CDriverActions> T focus(final WebElement webElement) {
    try {
      return performActionOnDriver(
          "Focus",
          webDriver -> {
            new Actions(webDriver).moveToElement(webElement).perform();
            return (T) this;
          });
    } catch (Throwable t) {
      executeScript("arguments[0].focus();", webElement);
      return (T) this;
    }
  }

  default WebElement getElement(By locator) {
    return getElement(locator, DEFAULT_TIMEOUT);
  }

  default WebElement getElement(By locator, int waitSec) {
    return waitUntil("Get Element", waitSec, null, webdriver -> webdriver.findElement(locator));
  }

  default WebElement dropTo(final By locator, int xOffset, int yOffset, int waitSec) {
    return waitUntil(
        "Drop To",
        waitSec,
        driver -> {
          WebElement elem = driver.findElement(locator);
          if (elem != null) {
            try {
              new Actions(driver)
                  .moveToElement(elem)
                  .clickAndHold()
                  .moveByOffset(xOffset, yOffset)
                  .release()
                  .build()
                  .perform();
            } catch (Throwable t) {
            }
            return elem;
          }
          return null;
        });
  }

  default WebElement dragAndDropTo(
      final By locator, int xOffset1, int yOffset1, int xOffset2, int yOffset2, int waitSec) {
    return waitUntil(
        "Drag And Drop To",
        waitSec,
        driver -> {
          WebElement elem = driver.findElement(locator);
          if (elem != null) {
            try {
              new Actions(driver)
                  .moveToElement(elem, xOffset1, yOffset1)
                  .clickAndHold()
                  .moveByOffset(xOffset2, yOffset2)
                  .release()
                  .build()
                  .perform();
            } catch (Throwable t) {
            }
            return elem;
          }
          return null;
        });
  }

  default WebElement moveToElement(final By locator, int xOffset, int yOffset, int waitSec) {
    return waitUntil(
        "Move To Element",
        waitSec,
        driver -> {
          WebElement elem = driver.findElement(locator);
          if (elem != null) {
            try {
              new Actions(driver).moveToElement(elem).moveByOffset(xOffset, yOffset).perform();
            } catch (Throwable t) {
            }
            return elem;
          }
          return null;
        });
  }
}
