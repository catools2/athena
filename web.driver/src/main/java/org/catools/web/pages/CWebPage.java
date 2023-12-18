package org.catools.web.pages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.catools.common.extensions.verify.CVerify;
import org.catools.web.drivers.CDriver;
import org.catools.web.drivers.CWebAlert;
import org.catools.web.exceptions.CPageNotOpenedException;
import org.catools.web.factory.CWebElementFactory;

import static org.catools.web.drivers.CDriverWaiter.DEFAULT_TIMEOUT;

public abstract class CWebPage<DR extends CDriver> implements CWebComponent<DR> {

  @Getter
  @Setter(AccessLevel.NONE)
  protected final DR driver;
  protected String titlePattern;

  public CWebPage(DR driver, String titlePattern) {
    this(driver, titlePattern, DEFAULT_TIMEOUT);
  }

  public CWebPage(DR driver, String titlePattern, int waitSecs) {
    this(driver, titlePattern, waitSecs, false);
  }

  public CWebPage(DR driver, String titlePattern, int waitSecs, boolean tryRefreshIfNotDisplayed) {
    this(driver, titlePattern, waitSecs, tryRefreshIfNotDisplayed, waitSecs);
  }

  public CWebPage(DR driver, String titlePattern, int waitSecs, boolean tryRefreshIfNotDisplayed, int waitSecsAfterRefresh) {
    this.driver = driver;
    CWebElementFactory.initElements(this);
    this.titlePattern = titlePattern;
    try {
      driver.waitCompleteReadyState();
      verifyDisplayed(waitSecs);
    } catch (Throwable t) {
      if (!tryRefreshIfNotDisplayed) {
        throw new CPageNotOpenedException(driver, t);
      }
      try {
        driver.refresh();
        verifyDisplayed(waitSecsAfterRefresh);
      } catch (Throwable e) {
        throw new CPageNotOpenedException(driver, e);
      }
    }
  }

  public boolean isDisplayed() {
    return isDisplayed(DEFAULT_TIMEOUT);
  }

  public boolean isDisplayed(int waitSecs) {
    driver.switchToPage(this.titlePattern);
    return driver.Title.waitMatches("^" + titlePattern + "$", waitSecs);
  }

  public <T extends CWebPage<DR>> T verifyDisplayed() {
    CVerify.Bool.isTrue(isDisplayed(DEFAULT_TIMEOUT), String.format("Verify %s page is displayed", getClass().getSimpleName()));
    return (T) this;
  }

  public <T extends CWebPage<DR>> T verifyDisplayed(int waitSecs) {
    CVerify.Bool.isTrue(isDisplayed(waitSecs), String.format("Verify %s page is displayed", getClass().getSimpleName()));
    return (T) this;
  }

  public CWebAlert<DR> getAlert() {
    return new CWebAlert<>(driver);
  }

  public void refresh() {
    driver.refresh();
  }

  public DR getDriver() {
    return driver;
  }
}
