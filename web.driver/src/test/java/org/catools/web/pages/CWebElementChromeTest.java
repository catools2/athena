package org.catools.web.pages;

import org.catools.web.drivers.CDriver;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.CDriverSession;
import org.catools.web.enums.CBrowser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class CWebElementChromeTest extends CWebElementBaseTest {

  protected CDriver getDriver() {
    return new CDriver(new CDriverSession(new CDriverProvider() {
      @Override
      public Capabilities getCapabilities() {
        return new ChromeOptions();
      }

      @Override
      public RemoteWebDriver buildTestContainer() {
        BrowserWebDriverContainer<?> driverContainer = new BrowserWebDriverContainer<>("selenium/standalone-chrome:latest");
        ChromeOptions options = new ChromeOptions();
        driverContainer.withCapabilities(options);
        driverContainer.start();
        return new RemoteWebDriver(driverContainer.getSeleniumAddress(), options);
      }

      @Override
      public RemoteWebDriver buildLocalDriver() {
        return (RemoteWebDriver) ChromeDriver.builder().build();
      }

      @Override
      public RemoteWebDriver buildRemoteWebDrier() {
        return null;
      }

      @Override
      public CBrowser getBrowser() {
        return CBrowser.CHROME;
      }
    }));
  }
}