package org.catools.web.exceptions;

import org.catools.web.drivers.CDriver;

public class CPageNotOpenedException extends RuntimeException {
  public CPageNotOpenedException(CDriver driver, Throwable cause) {
    super(
        "Current Page Title: '" + driver.getTitle() + "' Current Url:'" + driver.getUrl() + "'",
        cause);
  }
}
