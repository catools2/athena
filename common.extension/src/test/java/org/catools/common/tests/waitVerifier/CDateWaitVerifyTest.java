package org.catools.common.tests.waitVerifier;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CDateWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Date;

public class CDateWaitVerifyTest extends CBaseWaitVerifyTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsByFormat(verifier, actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss"));
    verify(verifier -> toWaitVerifier(actual).verifyEqualsByFormat(verifier, actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(null).verifyEqualsByFormat(verifier, expected, "yyyy-MM-dd HH:mm:ss"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyEqualsByFormat(verifier, null, "yyyy-MM-dd HH:mm:ss"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsByFormat(verifier, null, "yyyy-MM-dd HH:mm:ss"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsByFormat(verifier, actual.clone().addMinutes(10), "yyyy-MM-dd HH:mm:ss"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsTimePortion(verifier, actual.clone().addDays(10)));
    verify(verifier -> toWaitVerifier(actual).verifyEqualsTimePortion(verifier, actual.clone().addDays(10), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(null).verifyEqualsTimePortion(verifier, actual.clone().addSeconds(10)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyEqualsTimePortion(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsTimePortion(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsTimePortion(verifier, actual.clone().addSeconds(10)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEquals(verifier, actual));
    verify(verifier -> toWaitVerifier(actual).verifyEquals(verifier, actual, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsDatePortion(verifier, actual.clone().addMinutes(10)));
    verify(verifier -> toWaitVerifier(actual).verifyEqualsDatePortion(verifier, actual.clone().addMinutes(10), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ActualNull() {
    CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(null).verifyEqualsDatePortion(verifier, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyEqualsDatePortion(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsDatePortion(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEqualsDatePortion(verifier, actual.clone().addDays(10)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyEquals(verifier, actual.clone().addMinutes(10)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsByFormat(verifier, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm:ss", 1));
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsByFormat(verifier, null, "yyyy-MM-dd HH:mm:ss"));
    verify(verifier -> toWaitVerifier(null).verifyNotEqualsByFormat(verifier, actual, "yyyy-MM-dd HH:mm:ss"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEqualsByFormat(verifier, null, "yyyy-MM-dd HH:mm"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsByFormat(verifier, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsDatePortion(verifier, actual.clone().addDays(10), 1));
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsDatePortion(verifier, null));
    verify(verifier -> toWaitVerifier(null).verifyNotEqualsDatePortion(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEqualsDatePortion(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsDatePortion(verifier, actual.clone().addSeconds(10)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsTimePortion(verifier, actual.clone().addMinutes(10), 1));
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsTimePortion(verifier, null));
    verify(verifier -> toWaitVerifier(null).verifyNotEqualsTimePortion(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEqualsTimePortion(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_Equals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEqualsTimePortion(verifier, actual.clone().addDays(10)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEquals(verifier, actual.clone().addSeconds(10),1));
    verify(verifier -> toWaitVerifier(actual).verifyNotEquals(verifier, null));
    verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(verifier -> toWaitVerifier(actual).verifyNotEquals(verifier, actual.clone()));
  }

  private CDateWaitVerifier toWaitVerifier(Date val) {
    return () -> val == null ? null : new CDate(val);
  }
}
