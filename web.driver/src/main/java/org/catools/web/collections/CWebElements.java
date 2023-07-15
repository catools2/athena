package org.catools.web.collections;

import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;
import org.openqa.selenium.By;

public class CWebElements<DR extends CDriver> extends CWebList<CWebElement<DR>> {
  public CWebElements(String name, DR driver, String xpathLocator) {
    super(
        name,
        xpathLocator,
        (idx, xpath) -> new CWebElement<>(name + "[" + idx + "]", driver, By.xpath(xpath)));
  }

  public CWebElements(String name, DR driver, String xpathLocator, int waitSecs) {
    super(
        name,
        xpathLocator,
        waitSecs,
        (idx, xpath) -> new CWebElement<>(name + "[" + idx + "]", driver, By.xpath(xpath)));
  }

  public CWebElements(
      String name,
      DR driver,
      String xpathLocator,
      int waitForFirstElementInSecond,
      int waitForOtherElementInSecond) {
    super(
        name,
        xpathLocator,
        waitForFirstElementInSecond,
        waitForOtherElementInSecond,
        (idx, xpath) -> new CWebElement<>(name + "[" + idx + "]", driver, By.xpath(xpath)));
  }
}
