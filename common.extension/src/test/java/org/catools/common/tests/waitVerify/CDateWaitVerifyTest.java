package org.catools.common.tests.waitVerify;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.interfaces.waitVerify.CDateWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Date;

public class CDateWaitVerifyTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsByFormat(actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(null).verifyEqualsByFormat(expected, "yyyy-MM-dd HH:mm:ss");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_BothNull() {
    toWaitVerify(null).verifyEqualsByFormat(null, "yyyy-MM-dd HH:mm:ss");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsByFormat(null, "yyyy-MM-dd HH:mm:ss");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsByFormat(actual.clone().addMinutes(10), "yyyy-MM-dd HH:mm:ss");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsTimePortion(actual.clone().addDays(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(null).verifyEqualsTimePortion(actual.clone().addSeconds(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_BothNull() {
    toWaitVerify(null).verifyEqualsTimePortion(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsTimePortion(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsTimePortion(actual.clone().addSeconds(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEquals(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsDatePortion(actual.clone().addMinutes(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(null).verifyEqualsDatePortion(expected);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_BothNull() {
    toWaitVerify(null).verifyEqualsDatePortion(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsDatePortion(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEqualsDatePortion(actual.clone().addDays(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(null).verifyEquals(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    toWaitVerify(null).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyEquals(actual.clone().addMinutes(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEqualsByFormat(actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm:ss");
    toWaitVerify(actual).verifyNotEqualsByFormat(null, "yyyy-MM-dd HH:mm:ss");
    toWaitVerify(null).verifyNotEqualsByFormat(actual, "yyyy-MM-dd HH:mm:ss");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_BothNull() {
    toWaitVerify(null).verifyNotEqualsByFormat(null, "yyyy-MM-dd HH:mm");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEqualsByFormat(actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEqualsDatePortion(actual.clone().addDays(10));
    toWaitVerify(actual).verifyNotEqualsDatePortion(null);
    toWaitVerify(null).verifyNotEqualsDatePortion(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_BothNull() {
    toWaitVerify(null).verifyNotEqualsDatePortion(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEqualsDatePortion(actual.clone().addSeconds(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEqualsTimePortion(actual.clone().addMinutes(10));
    toWaitVerify(actual).verifyNotEqualsTimePortion(null);
    toWaitVerify(null).verifyNotEqualsTimePortion(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_BothNull() {
    toWaitVerify(null).verifyNotEqualsTimePortion(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEqualsTimePortion(actual.clone().addDays(10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEquals(actual.clone().addSeconds(10));
    toWaitVerify(actual).verifyNotEquals(null);
    toWaitVerify(null).verifyNotEquals(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    toWaitVerify(null).verifyNotEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    toWaitVerify(actual).verifyNotEquals(actual.clone());
  }

  private CDateWaitVerify toWaitVerify(Date val) {
    return () -> val == null ? null : new CDate(val);
  }
}
