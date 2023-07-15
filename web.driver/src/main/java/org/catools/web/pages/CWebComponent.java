package org.catools.web.pages;

import org.catools.web.drivers.CDriver;

public interface CWebComponent<DR extends CDriver> {
  DR getDriver();
}
