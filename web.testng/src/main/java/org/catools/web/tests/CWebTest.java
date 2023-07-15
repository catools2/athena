package org.catools.web.tests;

import org.apache.logging.log4j.Level;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.date.CDate;
import org.catools.common.io.CFile;
import org.catools.common.tests.CTest;
import org.catools.common.utils.CRetry;
import org.catools.media.utils.CImageUtil;
import org.catools.reportportal.utils.CReportPortalUtil;
import org.catools.web.config.CBrowserConfigs;
import org.catools.web.drivers.CDriver;
import org.catools.web.drivers.CDriverProvider;
import org.catools.web.drivers.CDriverSession;
import org.catools.web.drivers.providers.CChromeDriverProvider;
import org.catools.web.drivers.providers.CEdgeDriverProvider;
import org.catools.web.drivers.providers.CFireFoxDriverProvider;
import org.catools.web.listeners.CDriverListener;
import org.catools.web.pages.CWebPage;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Listeners(value = org.catools.web.listeners.CScreenshotOnFailureListener.class)
public class CWebTest<DR extends CDriver> extends CTest {
  private static final String DEFAULT_SESSION = "DEFAULT_SESSION";
  private String currentSession = DEFAULT_SESSION;
  private final CMap<String, DR> drivers = new CHashMap<>();

  public CWebTest() {
    super();
  }

  @AfterClass(alwaysRun = true)
  public void afterClass() {
    super.afterClass();
    quitAll();
  }

  public void switchSession(String currentSession) {
    this.currentSession = currentSession;
  }

  public DR getDriver() {
    String session = currentSession;
    if (!drivers.containsKey(session)) {
      drivers.put(session, getDefaultDriver());
    }
    return drivers.get(session);
  }

  public boolean isCurrentSessionActive() {
    try {
      return getDriver().isActive();
    } catch (Throwable t) {
      return false;
    }
  }

  public CFile takeScreenShot() {
    return takeScreenShot("");
  }

  public CFile takeScreenShot(String filename) {
    return takeScreenShot(getDriver(), filename);
  }

  public CFile takeScreenShot(CDriver driver, String filename) {
    if (driver == null) {
      return null;
    }
    String fileName = getName() + "-" + filename + "-" + CDate.now().toTimeStampForFileName();
    try {
      CFile output = CBrowserConfigs.getScreenShotsFolder().getChildFile(fileName.replaceAll("\\W", "_") + ".png");
      BufferedImage baseValue = driver.ScreenShot._get();
      if (baseValue != null) {
        CImageUtil.writePNG(baseValue, output);
        logger.info("ScreenShot saved to " + output);
      }
      if (output != null && output.exists()) {
        CReportPortalUtil.sendToReportPortal(Level.INFO, "ScreenShot from " + fileName, output);
      }
      return output;
    } catch (Throwable t) {
      logger.warn("Could not save screen shot image buffer to file.");
      logger.trace("Could not save screen shot image buffer to file.", t);
    }
    return null;
  }

  public void takeScreenShotIfFail(ITestResult result) {
    if (result.getStatus() != ITestResult.SUCCESS) {
      for (CDriver driver : drivers.values()) {
        if (driver != null) {
          takeScreenShot();
        }
      }
    }
  }

  public void takeScreenShotAndQuit() {
    try {
      takeScreenShot();
    } finally {
      quit();
    }
  }

  public void quitAll() {
    drivers.values().getAll(dr -> dr != null).forEach(dr -> {
      try {
        dr.quit();
      } catch (Throwable t) {
      }
    });
  }

  public void quit() {
    CDriver driver = getDriver();
    if (driver != null) {
      try {
        tryLogOut();
      } finally {
        try {
          CRetry.retry(idx -> {
            driver.quit();
            return true;
          }, 2, 1000);
        } catch (Throwable t) {
        }
      }
      drivers.remove(currentSession);
      logger.trace("Quit driver.");
    }
  }

  public void open(String url) {
    open(url, !isCurrentSessionActive());
  }

  public void open(String url, boolean restartSession) {
    open(url, restartSession, null);
  }

  public <P extends CWebPage<DR>> P open(String url, Supplier<P> expectedPage) {
    return open(url, !isCurrentSessionActive(), expectedPage);
  }

  public <P extends CWebPage<DR>> P open(String url, boolean restartSession, Supplier<P> expectedPage) {
    if (restartSession) {
      logger.trace("start new session");
      getDriver().startSession();
    }
    logger.info("Navigate to " + url);
    getDriver().open(url);
    return expectedPage == null ? null : expectedPage.get();
  }

  public void tryLogOut() {
  }

  protected final void addDriverListeners(CDriverListener... listeners) {
    getDriverSession().addListeners(listeners);
  }

  protected final void addBeforeDriverInitListener(Consumer<Capabilities> beforeInitDriverConsumer) {
    addDriverListeners(new CDriverListener() {
      @Override
      public void beforeInit(Capabilities capabilities) {
        beforeInitDriverConsumer.accept(capabilities);
      }
    });
  }

  protected final void addAfterDriverInitListener(Consumer<RemoteWebDriver> afterInitDriverConsumer) {
    addDriverListeners(new CDriverListener() {
      @Override
      public void afterInit(RemoteWebDriver remoteWebDriver) {
        afterInitDriverConsumer.accept(remoteWebDriver);
      }
    });
  }

  protected CDriverSession getDriverSession() {
    return getDriver().getDriverSession();
  }

  protected CDriverSession switchDriverProvider(CDriverProvider provider) {
    String session = currentSession;
    if (!drivers.containsKey(session)) {
      drivers.put(session, getDefaultDriver());
    }
    return drivers.get(session).getDriverSession().setDriverProvider(provider);
  }


  public void switchToFireFox() {
    switchDriverProvider(new CFireFoxDriverProvider());
  }

  public void switchToChrome() {
    switchDriverProvider(new CChromeDriverProvider());
  }

  public void switchToChromeHeadless() {
    switchDriverProvider(new CChromeDriverProvider().setHeadless());
  }

  public void switchToEdge() {
    switchDriverProvider(new CEdgeDriverProvider());
  }

  @SuppressWarnings("unchecked")
  protected DR getDefaultDriver() {
    CDriverProvider driverProvider;
    if (CBrowserConfigs.getCurrentBrowser().isFirefox()) {
      driverProvider = new CFireFoxDriverProvider();
    } else if (CBrowserConfigs.getCurrentBrowser().isEdge()) {
      driverProvider = new CEdgeDriverProvider();
    } else {
      driverProvider = new CChromeDriverProvider();
    }
    return (DR) new CDriver(new CDriverSession(driverProvider));
  }
}
