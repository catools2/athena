package org.catools.web.pages;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CTest;
import org.catools.web.collections.CWebElements;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;
import org.catools.web.drivers.CDriverSession;
import org.catools.web.exceptions.CPageNotOpenedException;
import org.catools.web.factory.CFindBy;
import org.catools.web.factory.CFindBys;
import org.catools.web.forms.CWebForm;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

public class CWebPageTest extends CTest {

  @Test
  public void buildPageWithValidField() {
    WebTest1 webTest1 = new WebTest1(new CDriver(new CDriverSession(null)));
    CVerify.Object.isNotNull(webTest1.element1);
    CVerify.Object.isNotNull(webTest1.elements1);
  }

  @Test(expectedExceptions = CPageNotOpenedException.class)
  public void buildPageWithWebElementWithoutAnnotation() {
    WebTest2 webTest2 = new WebTest2(new CDriver(new CDriverSession(null)), ".*");
    CVerify.Object.isNotNull(webTest2.element1);
    CVerify.Object.isNotNull(webTest2.element2);
    CVerify.Object.isNotNull(webTest2.element3);
    CVerify.Object.isNotNull(webTest2.elements4);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void buildPageWithWebElementsWithoutAnnotation() {
    new WebTest3(new CDriver(new CDriverSession(null)), ".*");
  }

  public static class WebTest1 extends CWebForm<CDriver> {
    @CFindBy(findBy = @FindBy(name = "name"), name = "elemName", waitInSeconds = 10)
    private CWebElement<CDriver> element1;
    @CFindBys(xpath = "//input", name = "elemName", waitForFirstElementInSecond = 10)
    private CWebElements<CDriver> elements1;

    public WebTest1(CDriver driver) {
      super(driver);
    }
  }

  public static class WebTest2 extends CWebPage<CDriver> {
    private CWebElement<CDriver> element1;
    @FindBy(name = "name")
    private CWebElement<CDriver> element2;
    @CFindBy(findBy = @FindBy(name = "name"), name = "elemName", waitInSeconds = 10)
    private CWebElement<CDriver> element3;
    @CFindBys(xpath = "//input", name = "elemName", waitForFirstElementInSecond = 10)
    private CWebElements<CDriver> elements4;

    public WebTest2(CDriver driver, String titlePattern) {
      super(driver, titlePattern, 2);
    }
  }

  public static class WebTest3 extends CWebPage<CDriver> {
    @CFindBy(findBy = @FindBy(name = "name"), name = "elemName", waitInSeconds = 10)
    private CWebElements<CDriver> elements2;

    public WebTest3(CDriver driver, String titlePattern) {
      super(driver, titlePattern, 2);
    }
  }
}