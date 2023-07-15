package org.catools.common.tests.logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.security.CSecurityConfigs;
import org.catools.common.security.CSensitiveDataMaskingManager;
import org.catools.common.tests.CBaseUnitTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class CSensitiveDataMaskingManagerTest extends CBaseUnitTest {
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(ITestResult result) {
    CSecurityConfigs.setMaskSensitiveData(true);
    CSensitiveDataMaskingManager.clear();
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod(ITestResult result) {
    CSecurityConfigs.setMaskSensitiveData(false);
    CSensitiveDataMaskingManager.clear();
  }

  @Test
  public void testAddSecurityMasks() {
    String mask1 = RandomStringUtils.randomAlphabetic(10);
    String mask2 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, mask2);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + mask2 + input2 + " " + mask1 + " "
            + input1 + " ";
    String expectedResult =
        input1 + " " + input2 + " ****** " + input2 + "******" + input2 + " ****** " + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), expectedResult, "String masked correctly");
  }

  @Test
  public void testAddDuplicateSecurityMasks() {
    String mask1 = RandomStringUtils.randomAlphabetic(10);
    String mask2 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, mask2, mask1);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + mask2 + input2 + " " + mask1 + " "
            + input1 + " ";
    String expectedResult =
        input1 + " " + input2 + " ****** " + input2 + "******" + input2 + " ****** " + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), expectedResult, "String masked correctly");
  }

  @Test
  public void testIgnoreAfterAddingMask() {
    String mask1 = RandomStringUtils.randomAlphabetic(10);
    String ignore1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, ignore1);

    CSensitiveDataMaskingManager.ignore(ignore1);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + ignore1 + input2 + " " + mask1 + input1
            + " ";
    String expectedResult =
        input1 + " " + input2 + " ****** " + input2 + ignore1 + input2 + " ******" + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), expectedResult, "String masked correctly");
  }

  @Test
  public void testIgnoreBeforeAddingMask() {
    String ignore1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.ignore(ignore1);

    String mask1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, ignore1);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + ignore1 + input2 + mask1 + " " + input1
            + " ";
    String expectedResult =
        input1 + " " + input2 + " ****** " + input2 + ignore1 + input2 + "****** " + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), expectedResult, "String masked correctly");
  }

  @Test
  public void testDuplicateIgnore() {
    String ignore1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.ignore(ignore1);

    String mask1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, ignore1);

    // Adding ignore for second time
    CSensitiveDataMaskingManager.ignore(ignore1);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + ignore1 + input2 + mask1 + " " + input1
            + " ";
    String expectedResult =
        input1 + " " + input2 + " ****** " + input2 + ignore1 + input2 + "****** " + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), expectedResult, "String masked correctly");
  }

  @Test
  public void testEmptyMask() {
    String mask1 = " ";
    CSensitiveDataMaskingManager.addMask(mask1);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + input2 + mask1 + " " + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), inputToMask, "String masked correctly");
  }

  @Test
  public void testEmptyIgnore() {
    String ignore1 = " ";
    CSensitiveDataMaskingManager.ignore(ignore1);

    String mask1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, ignore1);

    String input1 = RandomStringUtils.randomAlphabetic(10);
    String input2 = RandomStringUtils.randomAlphabetic(10);

    String inputToMask =
        input1 + " " + input2 + " " + mask1 + " " + input2 + ignore1 + input2 + mask1 + " " + input1
            + " ";
    String expectedResult =
        input1 + " " + input2 + " ****** " + input2 + ignore1 + input2 + "****** " + input1 + " ";

    CVerify.String.equals(
        CSensitiveDataMaskingManager.mask(inputToMask), expectedResult, "String masked correctly");
  }

  @Test
  public void testMaskEmptyString() {
    String ignore1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.ignore(ignore1);

    String mask1 = RandomStringUtils.randomAlphabetic(10);
    CSensitiveDataMaskingManager.addMask(mask1, ignore1);

    CVerify.String.equals(CSensitiveDataMaskingManager.mask(null), "", "String masked correctly");
  }
}
