package org.catools.web.drivers.providers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.catools.common.utils.CStringUtil;
import org.catools.web.config.CGridConfigs;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.config.CFireFoxConfigs;
import org.catools.web.drivers.config.CWebDriverManagerConfigs;
import org.catools.web.enums.CBrowser;
import org.catools.web.listeners.CDriverListener;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.Objects;

import static org.catools.web.config.CGridConfigs.getHubURL;
import static org.catools.web.config.CGridConfigs.isUseRemoteDriver;

public class CFireFoxDriverProvider implements CDriverProvider {
  private FirefoxOptions options = new FirefoxOptions();
  private FirefoxProfile profile = getFirefoxProfile(options);

  static {
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(CGridConfigs.getLogLevel());
    if (CWebDriverManagerConfigs.isEnabled()) {
      WebDriverManager.firefoxdriver().setup();
    }
  }

  public CFireFoxDriverProvider() {
    if (CStringUtil.isNotBlank(CFireFoxConfigs.getBinaryPath())) {
      setBinary(CFireFoxConfigs.getBinaryPath());
    }
    addArguments(CFireFoxConfigs.getDefaultArguments());
    setPageLoadStrategy(CFireFoxConfigs.getPageLoadStrategy());
    setOpenPdfInNewTab(true);

    if (CFireFoxConfigs.isInHeadLessMode()) {
      addArguments(CFireFoxConfigs.getHeadLessArguments());
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
            : new FirefoxDriver(options.setProfile(profile));

    if (listeners != null) {
      listeners.forEach(b -> b.afterInit(this, webDriver));
    }
    return webDriver;
  }

  @Override
  public CBrowser getBrowser() {
    return CBrowser.FIREFOX;
  }

  public CFireFoxDriverProvider setBinary(String path) {
    options.setBinary(path);
    return this;
  }

  public CFireFoxDriverProvider addArguments(Iterable<String> args) {
    for (String arg : args) {
      options.addArguments(arg);
    }
    return this;
  }

  public CFireFoxDriverProvider setHeadless(boolean value) {
    options.setHeadless(value);
    return this;
  }

  public CFireFoxDriverProvider setOpenPdfInNewTab(boolean value) {
    profile.setPreference("pdfjs.disabled", value);
    return this;
  }

  public CFireFoxDriverProvider setPageLoadStrategy(PageLoadStrategy pageLoadStrategy) {
    options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, pageLoadStrategy.toString());
    return this;
  }

  private FirefoxProfile getFirefoxProfile(FirefoxOptions options) {
    setSystemProperties();

    FirefoxProfile profile = new FirefoxProfile();
    profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
    profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");

    options.setProfile(profile);

    profile.setAcceptUntrustedCertificates(true);
    profile.setAlwaysLoadNoFocusLib(true);

    options.setLogLevel(FirefoxDriverLogLevel.FATAL);
    return profile;
  }

  private void setSystemProperties() {
    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
  }
}
