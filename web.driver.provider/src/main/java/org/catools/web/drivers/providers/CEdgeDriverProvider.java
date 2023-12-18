package org.catools.web.drivers.providers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.catools.common.utils.CStringUtil;
import org.catools.web.config.CGridConfigs;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.config.CEdgeConfigs;
import org.catools.web.drivers.config.CWebDriverManagerConfigs;
import org.catools.web.enums.CBrowser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.util.Objects;

import static org.catools.web.config.CGridConfigs.getHubURL;

public class CEdgeDriverProvider implements CDriverProvider {

  static {
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(CGridConfigs.getLogLevel());
    System.setProperty("webdriver.http.factory", "jdk-http-client");
    if (CWebDriverManagerConfigs.isEnabled()) {
      WebDriverManager.edgedriver().setup();
    }
  }

  private EdgeOptions options = new EdgeOptions();

  public CEdgeDriverProvider() {
    if (CStringUtil.isNotBlank(CEdgeConfigs.getBinaryPath())) {
      setBinary(CEdgeConfigs.getBinaryPath());
    }
    addArguments(CEdgeConfigs.getDefaultArguments());
    setPageLoadStrategy(CEdgeConfigs.getPageLoadStrategy());

    if (CEdgeConfigs.isInHeadLessMode()) {
      addArguments(CEdgeConfigs.getHeadLessArguments());
    }
  }

  @Override
  public Capabilities getCapabilities() {
    return options;
  }

  @Override
  public RemoteWebDriver buildTestContainer() {
    BrowserWebDriverContainer<?> driverContainer = new BrowserWebDriverContainer<>("selenium/standalone-edge:latest");
    driverContainer.withCapabilities(options);
    driverContainer.start();
    return new RemoteWebDriver(driverContainer.getSeleniumAddress(), options);
  }

  @Override
  public RemoteWebDriver buildLocalDriver() {
    return new EdgeDriver(options);
  }

  @Override
  public RemoteWebDriver buildRemoteWebDrier() {
    return new RemoteWebDriver(Objects.requireNonNull(getHubURL()), options);
  }

  @Override
  public CBrowser getBrowser() {
    return CBrowser.EDGE;
  }

  public CEdgeDriverProvider setPageLoadStrategy(PageLoadStrategy pageLoadStrategy) {
    options.setPageLoadStrategy(pageLoadStrategy);
    return this;
  }

  public CEdgeDriverProvider addArguments(Iterable<String> args) {
    for (String arg : args) {
      options.addArguments(arg);
    }
    return this;
  }

  public CEdgeDriverProvider setBinary(String path) {
    options.setBinary(path);
    return this;
  }
}
