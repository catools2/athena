package org.catools.web.drivers.providers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.utils.CStringUtil;
import org.catools.web.config.CGridConfigs;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.config.CEdgeConfigs;
import org.catools.web.drivers.config.CWebDriverManagerConfigs;
import org.catools.web.enums.CBrowser;
import org.catools.web.listeners.CDriverListener;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.Objects;

import static org.catools.web.config.CGridConfigs.getHubURL;
import static org.catools.web.config.CGridConfigs.isUseRemoteDriver;

public class CEdgeDriverProvider implements CDriverProvider {

  private CMap<String, Object> prefs = new CHashMap<>();
  private EdgeOptions options = new EdgeOptions();

  static {
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(CGridConfigs.getLogLevel());
    System.setProperty("webdriver.http.factory", "jdk-http-client");
    if (CWebDriverManagerConfigs.isEnabled()) {
      WebDriverManager.edgedriver().setup();
    }
  }

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
  public RemoteWebDriver build(List<CDriverListener> listeners) {
    if (listeners != null) {
      listeners.forEach(b -> b.beforeInit(options));
    }

    RemoteWebDriver webDriver =
        isUseRemoteDriver()
            ? new RemoteWebDriver(Objects.requireNonNull(getHubURL()), options)
            : new EdgeDriver(options);

    if (listeners != null) {
      listeners.forEach(b -> b.afterInit(this, webDriver));
    }
    return webDriver;
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
