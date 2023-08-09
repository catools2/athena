package org.catools.web.pages;

import org.catools.web.config.CGridConfigs;
import org.catools.web.drivers.CDriver;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.CDriverSession;
import org.catools.web.enums.CBrowser;
import org.catools.web.listeners.CDriverListener;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

public class CWebElementEdgeTest extends CWebElementBaseTest {

  protected CDriver getDriver() {
    return new CDriver(new CDriverSession(new CDriverProvider() {
      @Override
      public RemoteWebDriver build(List<CDriverListener> listeners) {
        return CGridConfigs.isUseRemoteDriver() ?
            (RemoteWebDriver) RemoteWebDriver.builder().oneOf(new EdgeOptions()).address("http://edge:4444/wd/hub").build() :
            (RemoteWebDriver) EdgeDriver.builder().build();
      }

      @Override
      public CBrowser getBrowser() {
        return CBrowser.EDGE;
      }
    }));
  }
}