package org.catools.web.exceptions;

import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;

public class CElementActionException extends RuntimeException {
  public CElementActionException(CDriver driver, CWebElement control, Throwable cause) {
    this(
        driver,
        control.getName(),
        control.getLocator() == null ? "" : control.getLocator().toString(),
        cause);
  }

  public CElementActionException(
      CDriver driver, String controlName, String controlLocator, Throwable cause) {
    super(
        "Current Page Title: '"
            + driver.getTitle()
            + "' Current Url:'"
            + driver.getUrl()
            + "' Control Name:'"
            + controlName
            + "' Control locator:'"
            + controlLocator
            + "'",
        cause);
  }
}
