package org.catools.web.drivers;

import org.catools.web.enums.CBrowser;
import org.catools.web.listeners.CDriverListener;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

public interface CDriverProvider {
  RemoteWebDriver build(List<CDriverListener> listeners);

  CBrowser getBrowser();
}
