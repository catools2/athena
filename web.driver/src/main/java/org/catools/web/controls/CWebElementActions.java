package org.catools.web.controls;

import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CList;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.common.utils.CFileUtil;
import org.catools.common.utils.CRetry;
import org.catools.common.utils.CSleeper;
import org.catools.web.config.CBrowserConfigs;
import org.catools.web.config.CGridConfigs;
import org.catools.web.drivers.CDriver;
import org.catools.web.utils.CGridUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;

import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

public interface CWebElementActions<DR extends CDriver> extends CWebElementStates<DR> {

  // Actions
  default void moveTo() {
    moveTo(0, 0);
  }

  default void moveTo(int waitSec) {
    moveTo(0, 0, waitSec);
  }

  default void moveTo(int xOffset, int yOffset) {
    moveTo(xOffset, yOffset, getWaitSec());
  }

  default void moveTo(int xOffset, int yOffset, int waitSec) {
    isPresent(waitSec);
    getDriver().moveToElement(getLocator(), xOffset, yOffset, 0);
  }

  default void dropTo(int xOffset, int yOffset) {
    dropTo(xOffset, yOffset, getWaitSec());
  }

  default void dropTo(int xOffset, int yOffset, int waitSec) {
    isPresent(waitSec);
    getDriver().dropTo(getLocator(), xOffset, yOffset, 0);
  }

  default void dragAndDropTo(int xOffset2, int yOffset2) {
    dragAndDropTo(0, 0, xOffset2, yOffset2);
  }

  default void dragAndDropTo(int xOffset2, int yOffset2, int waitSec) {
    dragAndDropTo(0, 0, xOffset2, yOffset2, waitSec);
  }

  default void dragAndDropTo(int xOffset1, int yOffset1, int xOffset2, int yOffset2) {
    dragAndDropTo(xOffset1, yOffset1, xOffset2, yOffset2, getWaitSec());
  }

  default void dragAndDropTo(int xOffset1, int yOffset1, int xOffset2, int yOffset2, int waitSec) {
    isPresent(waitSec);
    getDriver().dragAndDropTo(getLocator(), xOffset1, yOffset1, xOffset2, yOffset2, 0);
  }

  default <R> R executeScript(String script) {
    return executeScript(script, getWaitSec());
  }

  default <R> R executeScript(String script, int waitSec) {
    return getDriver().executeScript(getLocator(), waitSec, script);
  }

  default Point getLocation() {
    org.openqa.selenium.Point p = waitUntil("Get Position", getWaitSec(), WebElement::getLocation);
    return new Point(p.x, p.y);
  }

  default void sendKeys(CharSequence... keys) {
    getDriver().sendKeys(getLocator(), getWaitSec(), keys);
  }

  default void mouseClick() {
    mouseClick(getWaitSec());
  }

  default void mouseClick(int waitSec) {
    moveTo(waitSec);
    getDriver().mouseClick(getLocator(), waitSec);
  }

  default void mouseDoubleClick() {
    mouseDoubleClick(getWaitSec());
  }

  default void mouseDoubleClick(int waitSec) {
    moveTo(waitSec);
    getDriver().mouseDoubleClick(getLocator(), waitSec);
  }

  default void scrollIntoView(boolean scrollDown) {
    scrollIntoView(scrollDown, getWaitSec());
  }

  default void scrollIntoView(boolean scrollDown, int waitSec) {
    getDriver().scrollIntoView(getLocator(), scrollDown, waitSec);
  }

  default void scrollLeft() {
    scrollLeft(900, getWaitSec());
  }

  default void scrollLeft(int scrollSize, int waitSec) {
    getDriver().scrollLeft(getLocator(), scrollSize, waitSec);
  }

  default void scrollRight() {
    scrollRight(900, getWaitSec());
  }

  default void scrollRight(int scrollSize, int waitSec) {
    getDriver().scrollRight(getLocator(), scrollSize, waitSec);
  }

  default void setStyle(String style, String color) {
    setStyle(style, color, getWaitSec());
  }

  default void setStyle(String style, String color, int waitSec) {
    isPresent(waitSec);
    executeScript(String.format("arguments[0][%s][%s]=%s;", Quotes.escape("style"), Quotes.escape(style), Quotes.escape(color)));
  }

  default void setAttribute(String attributeName, String value) {
    setAttribute(attributeName, value, getWaitSec());
  }

  default void setAttribute(String attributeName, String value, int waitSec) {
    isPresent(waitSec);
    executeScript(String.format("arguments[0][%s]=%s;", Quotes.escape(attributeName), Quotes.escape(value)));
  }

  default void removeAttribute(String attributeName) {
    removeAttribute(attributeName, getWaitSec());
  }

  default void removeAttribute(String attributeName, int waitSec) {
    isPresent(waitSec);
    executeScript(String.format("arguments[0].removeAttribute(%s);", Quotes.escape(attributeName)));
  }

  default void set(boolean value) {
    set(value, DEFAULT_TIMEOUT);
  }

  default void set(boolean value, int waitSec) {
    if (value)
      select(waitSec);
    else
      deselect(waitSec);
  }

  default void select() {
    select(getWaitSec());
  }

  default void select(int waitSec) {
    waitUntil("Select", waitSec, webElement -> {
      if (!webElement.isSelected()) {
        _click(false, webElement);
      }
      return true;
    });
  }

  default void selectInvisible() {
    selectInvisible(getWaitSec());
  }

  default void selectInvisible(int waitSec) {
    waitUntil("Select", waitSec, webElement -> {
      if (!webElement.isSelected()) {
        _click(true, webElement);
      }
      return true;
    });
  }

  default void deselect() {
    deselect(getWaitSec());
  }

  default void deselect(int waitSec) {
    waitUntil("Deselect", waitSec, webElement -> {
      if (webElement.isSelected()) {
        _click(false, webElement);
      }
      return true;
    });
  }

  default void deselectInvisible() {
    deselectInvisible(getWaitSec());
  }

  default void deselectInvisible(int waitSec) {
    waitUntil("Deselect", waitSec, webElement -> {
      if (webElement.isSelected()) {
        _click(true, webElement);
      }
      return true;
    });
  }

  default void click() {
    click(getWaitSec());
  }

  default void click(int waitSec) {
    waitUntil("Click", waitSec, webElement -> {
      _click(false, webElement);
      return true;
    });
  }

  default <R> R click(com.google.common.base.Function<DR, R> postCondition) {
    return click(2, 2000, postCondition);
  }

  default <R> R click(int retryCount, int interval, com.google.common.base.Function<DR, R> postCondition) {
    return CRetry.retry(idx -> {
      click();
      return postCondition.apply(getDriver());
    }, retryCount, interval);
  }

  default void clickInvisible() {
    clickInvisible(getWaitSec());
  }

  default void clickInvisible(int waitSec) {
    getDriver().clickInvisible(getLocator(), waitSec);
  }

  default <R> R clickInvisible(com.google.common.base.Function<DR, R> postCondition) {
    return clickInvisible(2, 2000, postCondition);
  }

  default <R> R clickInvisible(int retryCount, int interval, com.google.common.base.Function<DR, R> postCondition) {
    return CRetry.retry(idx -> {
      clickInvisible();
      return postCondition.apply(getDriver());
    }, retryCount, interval);
  }

  default void openHref() {
    openHref(getWaitSec());
  }

  default void openHref(int waitSec) {
    getDriver().open(getAttribute("href", waitSec));
  }

  default <R> R openHref(com.google.common.base.Function<DR, R> postCondition) {
    return openHref(2, 2000, postCondition);
  }

  default <R> R openHref(int retryCount, int interval, com.google.common.base.Function<DR, R> postCondition) {
    return CRetry.retry(idx -> {
      openHref();
      return postCondition.apply(getDriver());
    }, retryCount, interval);
  }

  // Download File
  default CFile downloadFile(String filename, String renameTo) {
    return downloadFile(getWaitSec(), filename, renameTo, false);
  }

  default CFile downloadFile(int downloadWait, String filename, String renameTo) {
    return downloadFile(downloadWait, filename, renameTo, false);
  }

  default CFile downloadFile(int clickWait, int downloadWait, String filename, String renameTo) {
    return downloadFile(clickWait, downloadWait, filename, renameTo, false);
  }

  default CFile downloadFile(int downloadWait, String filename, String renameTo, boolean expectedAlert) {
    return downloadFile(getWaitSec(), downloadWait, filename, renameTo, expectedAlert);
  }

  default CFile downloadFile(int clickWait, int downloadWait, String filename, String renameTo, boolean expectedAlert) {
    click(clickWait);
    if (expectedAlert) {
      getDriver().getAlert().accept();
    }
    CFile downloadFolder = CBrowserConfigs.getDownloadFolder(getDriver().getSessionId());
    CFile downloadedFile = new CFile(downloadFolder, filename);
    downloadedFile.verifyExists(downloadWait, 500, "File downloaded! file:" + downloadedFile);
    return downloadedFile.moveTo(CPathConfigs.getTempChildFile(renameTo));
  }

  default CFile downloadFile(Pattern filename, String renameTo) {
    return downloadFile(getWaitSec(), filename, renameTo, false);
  }

  default CFile downloadFile(int downloadWait, Pattern filename, String renameTo) {
    return downloadFile(downloadWait, filename, renameTo, false);
  }

  default CFile downloadFile(int clickWait, int downloadWait, Pattern filename, String renameTo) {
    return downloadFile(clickWait, downloadWait, filename, renameTo, false);
  }

  default CFile downloadFile(int downloadWait, Pattern filename, String renameTo, boolean expectedAlert) {
    return downloadFile(getWaitSec(), downloadWait, filename, renameTo, expectedAlert);
  }

  default CFile downloadFile(int clickWait, int downloadWait, Pattern filename, String renameTo, boolean expectedAlert) {
    click(clickWait);
    if (expectedAlert) {
      getDriver().getAlert().accept();
    }
    CFile downloadFolder = CBrowserConfigs.getDownloadFolder(getDriver().getSessionId());
    File downloadedFile = CRetry.retryIfFalse(idx -> new CList<>(downloadFolder.listFiles()).getFirstOrNull(file -> filename.matcher(file.getName()).matches()), downloadWait * 2, 500);
    CVerify.Bool.isTrue(downloadedFile.exists(), "File downloaded properly!");
    return new CFile(downloadedFile).moveTo(CPathConfigs.getTempChildFolder(renameTo));
  }

  // Upload

  default void uploadFile(File file) {
    uploadFile(CFileUtil.getCanonicalPath(file));
  }

  default void uploadFile(String filePath) {
    if (!CGridConfigs.isUseLocalFileDetectorInOn()) {
      String fullFileName = getDriver().performActionOnDriver("Copy File To Node", webDriver -> CGridUtil.copyFileToNode(webDriver.getSessionId(), new File(filePath)));
      sendKeys(fullFileName);
    } else {
      sendKeys(filePath);
    }
    getDriver().pressTab();
  }

  default void uploadResource(String resourceName, Class clazz) {
    uploadResource(new CResource(resourceName, clazz));
  }

  default void uploadResource(CResource resource) {
    CFile filePath = resource.saveToFolder(CPathConfigs.getTempFolder());
    uploadFile(filePath.getCanonicalPath());
  }

  // Input

  default void setText(Date date, String format) {
    setText(date, format, getWaitSec());
  }

  default void setText(Date date, String format, int waitSec) {
    setText(CDate.of(date).toFormat(format), waitSec);
  }

  default void setText(String text) {
    setText(text, getWaitSec());
  }

  default void setText(String text, int waitSec) {
    waitUntil("Set Text", waitSec, el -> {
      el.sendKeys(getClearKeys(), text);
      return true;
    });
  }

  default void setTextAndEnter(String text) {
    setTextAndEnter(text, getWaitSec());
  }

  default void setTextAndEnter(String text, int waitSec) {
    setTextAnd(text, Keys.ENTER, waitSec);
  }

  default void setTextAndTab(String text) {
    setTextAndTab(text, getWaitSec());
  }

  default void setTextAndTab(String text, int waitSec) {
    setTextAnd(text, Keys.TAB, waitSec);
  }

  default void setTextAnd(String text, Keys keys) {
    setTextAnd(text, keys, getWaitSec());
  }

  default void setTextAnd(String text, Keys keys, int waitSec) {
    waitUntil("Set Text And " + keys.name(), waitSec, el -> {
      el.sendKeys(getClearKeys(), text, keys);
      return true;
    });
  }

  default void setValue(Date date, String format) {
    setValue(date, format, getWaitSec());
  }

  default void setValue(Date date, String format, int waitSec) {
    setValue(CDate.of(date).toFormat(format), waitSec);
  }

  default void setValue(String text) {
    setValue(text, getWaitSec());
  }

  default void setValue(String text, int waitSec) {
    setAttribute("value", text, waitSec);
  }

  default void setValueAndEnter(String text) {
    setValueAndEnter(text, getWaitSec());
  }

  default void setValueAndEnter(String text, int waitSec) {
    setValue(text, waitSec);
    sendKeys(Keys.ENTER);
  }

  default void setValueAndTab(String text) {
    setValueAndTab(text, getWaitSec());
  }

  default void setValueAndTab(String text, int waitSec) {
    setValue(text, waitSec);
    sendKeys(Keys.TAB);
  }

  default void clear() {
    clear(getWaitSec());
  }

  default void clear(int waitSec) {
    waitUntil("Clear", waitSec, el -> {
      el.sendKeys(getClearKeys());
      return true;
    });
  }

  default void type(Date date, String format) {
    type(date, format, getWaitSec());
  }

  default void type(Date date, String format, int waitSec) {
    type(CDate.of(date).toFormat(format), waitSec);
  }

  default void type(String text) {
    type(text, getWaitSec());
  }

  default void type(String text, int waitSec) {
    type(text, waitSec, 0L);
  }

  default void type(String text, long intervalInMilliSeconds) {
    type(text, getWaitSec(), intervalInMilliSeconds);
  }

  default void type(String text, int waitSec, long intervalInMilliSeconds) {
    waitUntil("Type", waitSec, el -> {
      el.sendKeys(getClearKeys());

      if (intervalInMilliSeconds < 10) {
        el.sendKeys(StringUtils.defaultString(text).split(""));
      } else {
        for (String c : StringUtils.defaultString(text).split("")) {
          el.sendKeys(c);
          CSleeper.sleepTight(intervalInMilliSeconds);
        }
      }
      return true;
    });
  }

  default void typeAndTab(String text) {
    typeAndTab(text, getWaitSec());
  }

  default void typeAndTab(String text, int waitSec) {
    typeAndTab(text, waitSec, 0L);
  }

  default void typeAndTab(String text, long intervalInMilliSeconds) {
    typeAndTab(text, getWaitSec(), intervalInMilliSeconds);
  }

  default void typeAndTab(String text, int waitSec, long intervalInMilliSeconds) {
    typeAnd(text, Keys.TAB, waitSec, intervalInMilliSeconds);
  }

  default void typeAndEnter(String text) {
    typeAndEnter(text, getWaitSec());
  }

  default void typeAndEnter(String text, int waitSec) {
    typeAndEnter(text, waitSec, 0L);
  }

  default void typeAndEnter(String text, long intervalInMilliSeconds) {
    typeAndEnter(text, getWaitSec(), intervalInMilliSeconds);
  }

  default void typeAndEnter(String text, int waitSec, long intervalInMilliSeconds) {
    typeAnd(text, Keys.ENTER, waitSec, intervalInMilliSeconds);
  }

  default void typeAnd(String text, Keys keys) {
    typeAnd(text, keys, getWaitSec());
  }

  default void typeAnd(String text, Keys keys, int waitSec) {
    typeAnd(text, keys, waitSec, 0L);
  }

  default void typeAnd(String text, Keys keys, long intervalInMilliSeconds) {
    typeAnd(text, keys, getWaitSec(), intervalInMilliSeconds);
  }

  default void typeAnd(String text, Keys keys, int waitSec, long intervalInMilliSeconds) {
    type(text, waitSec, intervalInMilliSeconds);
    getDriver().sendKeys(keys);
  }

  private void _click(boolean useJS, WebElement webElement) {
    if (useJS) {
      _clickWithJS(webElement);
    } else {
      try {
        webElement.click();
      } catch (WebDriverException t) {
        _clickWithJS(webElement);
      }
    }
  }

  private void _clickWithJS(WebElement webElement) {
    getDriver().executeScript("arguments[0].click();", webElement);
  }


  private String getClearKeys() {
    if (getDriver().getPlatform().isMac())
      return Keys.chord(Keys.COMMAND, "a", Keys.DELETE);

    return Keys.chord(Keys.CONTROL, "a", Keys.DELETE);
  }
}
