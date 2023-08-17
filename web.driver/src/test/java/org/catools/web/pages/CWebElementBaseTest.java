package org.catools.web.pages;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CTest;
import org.catools.web.config.CBrowserConfigs;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;
import org.catools.web.listeners.CDriverListener;
import org.catools.web.listeners.CWebMetricCollectorListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public abstract class CWebElementBaseTest extends CTest {
  private CDriver driver;

  @BeforeClass
  public void beforeClass() {
    driver = getDriver();
    driver.getDriverSession().addListeners(new CDriverListener(){});
    driver.getDriverSession().addListeners(new CWebMetricCollectorListener());
    driver.open("https://mdbootstrap.com/docs/b4/jquery/forms/basic/");
  }

  @AfterClass
  public void afterClass() {
    driver.getScreenShot();
    driver.quit();
  }

  @Test
  public void testElementBasics() {
    CWebElement<?> email = driver.$("//input[@id='defaultLoginFormEmail']");
    email.Visible.verifyIsTrue();
    email.Present.verifyIsTrue();
    email.Clickable.verifyIsTrue();
    email.Enabled.verifyIsTrue();
    email.Selected.verifyIsFalse();
    email.Staleness.verifyIsFalse();
    email.Text.verifyIsEmpty();
    email.TagName.verifyEquals("input");

    email.moveTo();
    email.clear();

    email.type(CDate.of("2012/21/10", "YYYY/dd/MM"), "YYYY/dd/MM");
    email.Value.verifyEquals("2012/21/10");

    email.clear();

    email.setValue(CDate.of("2012/21/10", "YYYY/dd/MM"), "YYYY/dd/MM");
    email.Value.verifyEquals("2012/21/10");

    email.clear();

    email.typeAndTab("YYYY/dd/MM");
    email.Value.verifyEquals("YYYY/dd/MM");

    email.clear();

    email.setValueAndTab("YYYY/dd/MM");
    email.Value.verifyEquals("YYYY/dd/MM");

    email.clear();
  }

  @Test
  public void testGetDownloadFolder() {
    CVerify.Object.isNotNull(CBrowserConfigs.getDownloadFolder(driver.getSessionId()));
  }

  protected abstract CDriver getDriver();
}