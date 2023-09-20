package org.catools.web.pages;

import org.catools.web.drivers.CDriver;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.CDriverSession;
import org.catools.web.enums.CBrowser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class CWebElementEdgeTest extends CWebElementBaseTest {

  protected CDriver getDriver() {
    return new CDriver(new CDriverSession(new CDriverProvider() {
      @Override
      public Capabilities getCapabilities() {
        return new EdgeOptions();
      }

      @Override
      public RemoteWebDriver buildTestContainer() {
        BrowserWebDriverContainer<?> driverContainer = new BrowserWebDriverContainer<>("selenium/standalone-edge:latest");
        EdgeOptions options = new EdgeOptions();
        driverContainer.withCapabilities(options);
        driverContainer.start();
        return new RemoteWebDriver(driverContainer.getSeleniumAddress(), options);
      }

      @Override
      public RemoteWebDriver buildLocalDriver() {
        return (RemoteWebDriver) EdgeDriver.builder().build();
      }

      @Override
      public RemoteWebDriver buildRemoteWebDrier() {
        return (RemoteWebDriver) EdgeDriver.builder().build();
      }

      @Override
      public CBrowser getBrowser() {
        return CBrowser.EDGE;
      }
    }));
  }
}