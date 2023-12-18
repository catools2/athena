package org.catools.web.drivers;

import org.catools.web.enums.CBrowser;
import org.catools.web.listeners.CDriverListener;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

import static org.catools.web.config.CGridConfigs.isUseRemoteDriver;
import static org.catools.web.config.CGridConfigs.isUseTestContainer;

public interface CDriverProvider {
  default RemoteWebDriver build(List<CDriverListener> listeners) {
    if (listeners != null) {
      listeners.forEach(b -> b.beforeInit(getCapabilities()));
    }

    RemoteWebDriver webDriver;
    if (isUseRemoteDriver())
      webDriver = buildRemoteWebDrier();
    else if (isUseTestContainer())
      webDriver = buildTestContainer();
    else
      webDriver = buildLocalDriver();

    if (listeners != null) {
      listeners.forEach(b -> b.afterInit(this, webDriver));
    }
    return webDriver;
  }

  Capabilities getCapabilities();

  RemoteWebDriver buildTestContainer();

  RemoteWebDriver buildLocalDriver();

  RemoteWebDriver buildRemoteWebDrier();

  CBrowser getBrowser();
}
