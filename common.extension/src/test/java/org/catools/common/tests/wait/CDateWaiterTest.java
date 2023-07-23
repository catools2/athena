package org.catools.common.tests.wait;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.interfaces.CDateWaiter;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Date;

public class CDateWaiterTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsByFormat(actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss"),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isFalse(
        toWaiter(null).waitEqualsByFormat(expected, "yyyy-MM-dd HH:mm:ss"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_BothNull() {
    CVerify.Bool.isTrue(
        toWaiter(null).waitEqualsByFormat(null, "yyyy-MM-dd HH:mm:ss"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsByFormat(null, "yyyy-MM-dd HH:mm:ss"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsByFormat(actual.clone().addMinutes(10), "yyyy-MM-dd HH:mm:ss"),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsTimePortion(actual.clone().addDays(10)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isFalse(
        toWaiter(null).waitEqualsTimePortion(actual.clone().addSeconds(10)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitEqualsTimePortion(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter(actual).waitEqualsTimePortion(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsTimePortion(actual.clone().addSeconds(10)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter(actual).waitEquals(actual), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsDatePortion(actual.clone().addMinutes(10)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isFalse(toWaiter(null).waitEqualsDatePortion(expected), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitEqualsDatePortion(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter(actual).waitEqualsDatePortion(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEqualsDatePortion(actual.clone().addDays(10)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isFalse(toWaiter(null).waitEquals(actual), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter(actual).waitEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitEquals(actual.clone().addMinutes(10)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual)
            .waitNotEqualsByFormat(actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm:ss"),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEqualsByFormat(null, "yyyy-MM-dd HH:mm:ss"), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(null).waitNotEqualsByFormat(actual, "yyyy-MM-dd HH:mm:ss"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat_BothNull() {
    CVerify.Bool.isFalse(
        toWaiter(null).waitNotEqualsByFormat(null, "yyyy-MM-dd HH:mm"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEqualsByFormat(actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm"),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEqualsDatePortion(actual.clone().addDays(10)),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter(actual).waitNotEqualsDatePortion(null), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(null).waitNotEqualsDatePortion(actual), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion_BothNull() {
    CVerify.Bool.isFalse(toWaiter(null).waitNotEqualsDatePortion(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEqualsDatePortion(actual.clone().addSeconds(10)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEqualsTimePortion(actual.clone().addMinutes(10)),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter(actual).waitNotEqualsTimePortion(null), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(null).waitNotEqualsTimePortion(actual), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion_BothNull() {
    CVerify.Bool.isFalse(toWaiter(null).waitNotEqualsTimePortion(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEqualsTimePortion(actual.clone().addDays(10)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(
        toWaiter(actual).waitNotEquals(actual.clone().addSeconds(10)), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(actual).waitNotEquals(null), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(null).waitNotEquals(actual), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isFalse(toWaiter(null).waitNotEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    CVerify.Bool.isTrue(toWaiter(actual).waitNotEquals(actual.clone()), "%s#%s", getParams());
  }

  private CDateWaiter toWaiter(Date val) {
    return () -> val == null ? null : new CDate(val);
  }
}
