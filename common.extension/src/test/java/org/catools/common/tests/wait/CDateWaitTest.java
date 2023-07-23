package org.catools.common.tests.wait;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CDateWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CDateWaitTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsByFormat(actual, actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsByFormat(null, expected, "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_BothNull() {
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsByFormat(null, null, "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsByFormat(actual, null, "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsByFormat(
                actual, actual.clone().addMinutes(10), "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsTimePortion(actual, actual.clone().addDays(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsTimePortion(null, actual.clone().addSeconds(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_BothNull() {
    CVerify.Bool.isTrue(toWaiter().waitEqualsTimePortion(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsTimePortion(actual, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsTimePortion(actual, actual.clone().addSeconds(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter().waitEquals(actual, actual, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsDatePortion(actual, actual.clone().addMinutes(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsDatePortion(null, expected, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_BothNull() {
    CVerify.Bool.isTrue(toWaiter().waitEqualsDatePortion(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsDatePortion(actual, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsDatePortion(actual, actual.clone().addDays(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter().waitEquals(null, actual, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter().waitEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter().waitEquals(actual, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(actual, actual.clone().addMinutes(10), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsByFormat(
                actual, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsByFormat(actual, null, "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsByFormat(null, actual, "yyyy-MM-dd HH:mm:ss", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_BothNull() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsByFormat(null, null, "yyyy-MM-dd HH:mm", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsByFormat(
                actual, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsDatePortion(actual, actual.clone().addDays(10), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsDatePortion(actual, null, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsDatePortion(null, actual, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_BothNull() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsDatePortion(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsDatePortion(actual, actual.clone().addSeconds(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsTimePortion(actual, actual.clone().addMinutes(10), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsTimePortion(actual, null, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsTimePortion(null, actual, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_BothNull() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsTimePortion(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsTimePortion(actual, actual.clone().addDays(10), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitNotEquals(actual, actual.clone().addSeconds(10), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(actual, null, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(null, actual, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter().waitNotEquals(actual, actual.clone(), 0, 100), "%s#%s", getParams());
  }

  private CDateWait toWaiter() {
    return new CDateWait();
  }
}
