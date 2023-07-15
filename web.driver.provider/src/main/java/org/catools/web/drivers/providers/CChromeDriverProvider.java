package org.catools.web.drivers.providers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.utils.CStringUtil;
import org.catools.web.config.CGridConfigs;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.config.CChromeConfigs;
import org.catools.web.drivers.config.CWebDriverManagerConfigs;
import org.catools.web.enums.CBrowser;
import org.catools.web.listeners.CDriverListener;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.catools.web.config.CGridConfigs.getHubURL;
import static org.catools.web.config.CGridConfigs.isUseRemoteDriver;

public class CChromeDriverProvider implements CDriverProvider {
  private static final Object LOCK = new Object();
  private CMap<String, Object> prefs = new CHashMap<>();
  private CList<CMap<String, Object>> plugins = new CList<>();
  private ChromeOptions options = new ChromeOptions();

  static {
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(CGridConfigs.getLogLevel());
    System.setProperty("webdriver.http.factory", "jdk-http-client");
    if (CWebDriverManagerConfigs.isEnabled()) {
      WebDriverManager.chromedriver().setup();
    }
  }

  public CChromeDriverProvider() {
    if (!CWebDriverManagerConfigs.isEnabled() && CStringUtil.isNotBlank(CChromeConfigs.getBinaryPath())) {
      setBinary(CChromeConfigs.getBinaryPath());
    }
    addArguments(CChromeConfigs.getDefaultArguments());
    addPluginsToEnable(CChromeConfigs.getPluginsToEnable());
    addPluginsToDisable(CChromeConfigs.getPluginsToDisable());
    setPageLoadStrategy(CChromeConfigs.getPageLoadStrategy());
    setOpenPdfInNewTab(true);

    prefs.put("plugins.plugins_list", plugins);

    if (CStringUtil.isNotBlank(CChromeConfigs.getChromeMobileEmulationDeviceName())) {
      setDeviceEmulation(CChromeConfigs.getChromeMobileEmulationDeviceName());
    }

    if (CChromeConfigs.isInHeadLessMode())
      setHeadless();
  }

  @Override
  public RemoteWebDriver build(List<CDriverListener> listeners) {
    buildChromeOptions();
    if (listeners != null) {
      listeners.forEach(b -> b.beforeInit(options));
    }

    // it happens that in high race condition we might have more than
    // multiple driver with one profile which causes different issues
    // specially with chrome
    RemoteWebDriver webDriver;
    synchronized (LOCK) {
      webDriver =
          isUseRemoteDriver()
              ? new RemoteWebDriver(Objects.requireNonNull(getHubURL()), options)
              : new ChromeDriver(options);
    }

    if (listeners != null) {
      listeners.forEach(b -> b.afterInit(webDriver));
    }
    return webDriver;
  }

  @Override
  public CBrowser getBrowser() {
    return CBrowser.CHROME;
  }

  public CChromeDriverProvider setBinary(String path) {
    options.setBinary(path);
    return this;
  }

  public CChromeDriverProvider addArguments(Iterable<String> args) {
    for (String arg : args) {
      options.addArguments(arg);
    }
    return this;
  }

  public CChromeDriverProvider setHeadless() {
    addArguments(CChromeConfigs.getHeadLessArguments());
    return this;
  }

  public CChromeDriverProvider setOpenPdfInNewTab(boolean value) {
    prefs.put("plugins.always_open_pdf_externally", value);
    return this;
  }

  public CChromeDriverProvider setPageLoadStrategy(PageLoadStrategy pageLoadStrategy) {
    options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, pageLoadStrategy.toString());
    return this;
  }

  public CChromeDriverProvider setDeviceEmulation(String deviceName) {
    Map<String, String> mobileEmulation = new HashMap<>();
    mobileEmulation.put("deviceName", deviceName);
    options.setExperimentalOption("mobileEmulation", mobileEmulation);
    return this;
  }

  public CChromeDriverProvider addPluginsToDisable(Iterable<String> pluginsToDisable) {
    new CList<>(pluginsToDisable).forEach(p -> addPlugin(false, p));
    return this;
  }

  public CChromeDriverProvider addPluginsToEnable(Iterable<String> pluginsToEnable) {
    new CList<>(pluginsToEnable).forEach(p -> addPlugin(true, p));
    return this;
  }

  public CChromeDriverProvider addPlugin(boolean flag, String pluginName) {
    CMap<String, Object> plugin = new CHashMap<>();
    plugin.put("enabled", flag);
    plugin.put("name", pluginName);
    plugins.add(plugin);
    return this;
  }

  private ChromeOptions buildChromeOptions() {
    setSystemProperties();
    options.setExperimentalOption("prefs", prefs);

    options.setAcceptInsecureCerts(true);

    options.setCapability(ChromeOptions.CAPABILITY, options);
    return options;
  }

  private void setSystemProperties() {
    System.setProperty("webdriver.chrome.silentOutput", "true");
  }
}
