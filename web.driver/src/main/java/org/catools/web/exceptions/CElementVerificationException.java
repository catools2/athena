package org.catools.web.exceptions;

import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;

public class CElementVerificationException extends RuntimeException {
  public CElementVerificationException(
      CDriver driver, CWebElement control, String verificationMessage, Throwable cause) {
    super(
        "ElementVerificationException ::> Current Page Title: '"
            + driver.getTitle()
            + "' Current Url:'"
            + driver.getUrl()
            + "' Control Name:'"
            + control.getName()
            + "' Control locator:'"
            + control.getLocator()
            + "'Verification Message:'"
            + verificationMessage
            + "'",
        cause);
  }
}
