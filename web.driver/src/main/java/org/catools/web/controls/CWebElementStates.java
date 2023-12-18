package org.catools.web.controls;

import org.apache.commons.lang3.StringUtils;
import org.catools.common.date.CDate;
import org.catools.common.utils.CStringUtil;
import org.catools.media.utils.CImageUtil;
import org.catools.web.config.CDriverConfigs;
import org.catools.web.drivers.CDriver;
import org.openqa.selenium.*;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public interface CWebElementStates<DR extends CDriver> {
  int DEFAULT_TIMEOUT = CDriverConfigs.getTimeout();

  DR getDriver();

  int getWaitSec();

  By getLocator();

  // Getters
  default Integer getOffset() {
    return getOffset(DEFAULT_TIMEOUT);
  }

  default Integer getOffset(int waitSec) {
    return waitUntil(
        "Get Offset", waitSec, null, el -> Integer.valueOf(el.getAttribute("offsetTop")));
  }

  default boolean isStaleness() {
    return isStaleness(DEFAULT_TIMEOUT);
  }

  default boolean isStaleness(int waitSec) {
    return waitUntil(
        "Is Staleness",
        waitSec,
        false,
        el -> {
          try {
            return !el.isEnabled();
          } catch (StaleElementReferenceException | NullPointerException var3) {
            return true;
          }
        });
  }

  default boolean isNotStaleness() {
    return isNotStaleness(DEFAULT_TIMEOUT);
  }

  default boolean isNotStaleness(int waitSec) {
    return isEnabled(waitSec);
  }

  default boolean isPresent() {
    return isPresent(DEFAULT_TIMEOUT);
  }

  default boolean isPresent(int waitSec) {
    return waitUntil("Is Present", waitSec, false, Objects::nonNull);
  }

  default boolean isNotPresent() {
    return isNotPresent(DEFAULT_TIMEOUT);
  }

  default boolean isNotPresent(int waitSec) {
    return waitUntil(
        "Is Not Present",
        waitSec,
        false,
        webDriver -> {
          try {
            return webDriver.findElement(getLocator()) == null;
          } catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException e) {
            return true;
          }
        });
  }

  default boolean isVisible() {
    return isVisible(DEFAULT_TIMEOUT);
  }

  default boolean isVisible(int waitSec) {
    return waitUntil("Is Visible", waitSec, false, WebElement::isDisplayed);
  }

  default boolean isNotVisible() {
    return isNotVisible(DEFAULT_TIMEOUT);
  }

  default boolean isNotVisible(int waitSec) {
    return waitUntil(
        "Is Not Visible",
        waitSec,
        false,
        webDriver -> {
          try {
            WebElement el = webDriver.findElement(getLocator());
            return el == null || !el.isDisplayed();
          } catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException e) {
            return true;
          }
        });
  }

  default boolean isEnabled() {
    return isEnabled(DEFAULT_TIMEOUT);
  }

  default boolean isEnabled(int waitSec) {
    return waitUntil(
        "Is Enable",
        waitSec,
        false,
        el -> {
          return el != null
              && el.isEnabled()
              && StringUtils.isBlank(el.getAttribute("readonly"))
              && StringUtils.isBlank(el.getAttribute("disabled"))
              && el.findElements(
                  By.xpath(
                      "./ancestor::*[contains(@class, 'disabled') or contains(@class, 'readonly') or contains(@class, 'hidden')]"))
              .isEmpty();
        });
  }

  default boolean isNotEnabled() {
    return isNotEnabled(DEFAULT_TIMEOUT);
  }

  default boolean isNotEnabled(int waitSec) {
    return waitUntil(
        "Is Not Enabled",
        waitSec,
        el -> {
          return el != null
              && !(el.isEnabled()
              && StringUtils.isBlank(el.getAttribute("readonly"))
              && StringUtils.isBlank(el.getAttribute("disabled"))
              && el.findElements(By.xpath("./ancestor::*[contains(@class, 'disabled') or contains(@class, 'readonly') or contains(@class, 'hidden')]"))
              .isEmpty());
        });
  }

  default boolean isSelected() {
    return isSelected(DEFAULT_TIMEOUT);
  }

  default boolean isSelected(int waitSec) {
    return isVisible(waitSec) && waitUntil("Is Selected", 1, false, WebElement::isSelected);
  }

  default boolean isNotSelected() {
    return isNotSelected(DEFAULT_TIMEOUT);
  }

  default boolean isNotSelected(int waitSec) {
    return waitUntil(
        "Is Not Selected",
        waitSec,
        false,
        webDriver -> {
          try {
            WebElement el = webDriver.findElement(getLocator());
            return el != null && !el.isSelected();
          } catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException e) {
            return true;
          }
        });
  }

  default boolean isClickable() {
    return isClickable(DEFAULT_TIMEOUT);
  }

  default boolean isClickable(int waitSec) {
    return isEnabled(waitSec);
  }

  default boolean isNotClickable() {
    return isNotClickable(DEFAULT_TIMEOUT);
  }

  default boolean isNotClickable(int waitSec) {
    return isNotEnabled(waitSec);
  }

  default String getText() {
    return getText(DEFAULT_TIMEOUT);
  }

  default String getText(int waitSec) {
    return waitUntil("Get Text", waitSec, "", WebElement::getText);
  }

  default Date getDate(String dateFormat) {
    String text = getText();
    return CStringUtil.isBlank(text) ? null : CDate.of(text, dateFormat);
  }

  default Date getDate(String dateFormat, int waitSec) {
    String text = getText(waitSec);
    return CStringUtil.isBlank(text) ? null : CDate.of(text, dateFormat);
  }

  default String getValue() {
    return getValue(DEFAULT_TIMEOUT);
  }

  default String getValue(int waitSec) {
    return waitUntil("Get Value", waitSec, "", el -> el.getAttribute("value"));
  }

  default String getInnerHTML() {
    return getInnerHTML(DEFAULT_TIMEOUT);
  }

  default String getInnerHTML(int waitSec) {
    return waitUntil(
        "Get Inner HTML",
        waitSec,
        "",
        el -> getDriver().executeScript("return arguments[0].innerHTML", el));
  }

  default String getTagName() {
    return getTagName(DEFAULT_TIMEOUT);
  }

  default String getTagName(int waitSec) {
    return waitUntil("Get Tag", waitSec, "", WebElement::getTagName);
  }

  default String getCss(final String cssKey) {
    return getCss(cssKey, DEFAULT_TIMEOUT);
  }

  default String getCss(final String cssKey, int waitSec) {
    return waitUntil("Get Css", waitSec, "", el -> el.getCssValue(cssKey));
  }

  default String getAttribute(final String attribute) {
    return getAttribute(attribute, DEFAULT_TIMEOUT);
  }

  default String getAttribute(final String attribute, int waitSec) {
    return waitUntil("Get Attribute", waitSec, "", el -> el.getAttribute(attribute));
  }

  default String getAriaRole() {
    return getAriaRole(DEFAULT_TIMEOUT);
  }

  default String getAriaRole(int waitSec) {
    return waitUntil("Get AriaRole", waitSec, "", WebElement::getAriaRole);
  }

  default BufferedImage getScreenShot() {
    return getScreenShot(DEFAULT_TIMEOUT);
  }

  default BufferedImage getScreenShot(int waitSec) {
    return waitUntil(
        "Get ScreenShot",
        waitSec,
        el -> CImageUtil.readImageOrNull(el.getScreenshotAs(OutputType.BYTES)));
  }

  // Protected
  default <C> C waitUntil(String actionName, int waitSec, Function<WebElement, C> action) {
    return getDriver()
        .waitUntil(
            actionName,
            waitSec,
            webDriver -> {
              WebElement element = webDriver.findElement(getLocator());
              if (element == null) {
                return null;
              }
              return action.apply(element);
            });
  }

  default <C> C waitUntil(String actionName, int waitSec, C defaultTo, Function<WebElement, C> action) {
    return getDriver()
        .waitUntil(
            actionName,
            waitSec,
            defaultTo,
            webDriver -> {
              WebElement element = webDriver.findElement(getLocator());
              if (element == null) {
                return null;
              }
              return action.apply(element);
            });
  }
}
