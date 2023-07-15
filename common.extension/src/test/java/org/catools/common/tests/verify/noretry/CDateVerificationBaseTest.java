package org.catools.common.tests.verify.noretry;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.hard.CDateVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class CDateVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual,
              actual.clone().addMinutes(10),
              "yyyy-MM-dd HH:ss",
              "DateExpectationTest ::> equalsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ActualNull() {
    verify(
        date -> {
          CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              null, expected, "yyyy-MM-dd HH:mm:ss", "DateExpectationTest ::> equalsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_BothNull() {
    verify(
        date -> {
          date.equalsByFormat(
              null, null, "yyyy-MM-dd HH:mm:ss", "DateExpectationTest ::> equalsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual, null, "yyyy-MM-dd HH:mm:ss", "DateExpectationTest ::> equalsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual,
              actual.clone().addMinutes(10),
              "yyyy-MM-dd HH:mm:ss",
              "DateExpectationTest ::> equalsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(
              actual, actual.clone().addDays(10), "DateExpectationTest ::> equalsTimePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ActualNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(
              null, actual.clone().addSeconds(10), "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_BothNull() {
    verify(
        date -> {
          date.equalsTimePortion(null, null, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, null, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(
              actual, actual.clone().addSeconds(10), "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, actual, "DateExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(
              actual, actual.clone().addMinutes(10), "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ActualNull() {
    verify(
        date -> {
          CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(null, expected, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_BothNull() {
    verify(
        date -> {
          date.equalsDatePortion(null, null, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, null, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(
              actual, actual.clone().addDays(10), "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(null, actual, "DateExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(
        date -> {
          date.equals(null, null, "DateExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, null, "DateExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, actual.clone().addMinutes(10), "DateExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsByFormat(
              actual,
              actual.clone().addSeconds(10),
              "yyyy-MM-dd HH:mm:ss",
              "DateExpectationTest ::> notEqualsByFormat ");
        });
    verify(
        date -> {
          date.notEqualsByFormat(
              actual, null, "yyyy-MM-dd HH:mm:ss", "DateExpectationTest ::> notEqualsByFormat ");
        });
    verify(
        date -> {
          date.notEqualsByFormat(
              null, actual, "yyyy-MM-dd HH:mm:ss", "DateExpectationTest ::> notEqualsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_BothNull() {
    verify(
        date -> {
          date.notEqualsByFormat(
              null, null, "yyyy-MM-dd HH:mm", "DateExpectationTest ::> notEqualsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsByFormat(
              actual,
              actual.clone().addSeconds(10),
              "yyyy-MM-dd HH:mm",
              "DateExpectationTest ::> notEqualsByFormat ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsDatePortion(
              actual, actual.clone().addDays(10), "DateExpectationTest ::> notEqualsDatePortion ");
        });
    verify(
        date -> {
          date.notEqualsDatePortion(actual, null, "DateExpectationTest ::> notEqualsDatePortion ");
        });
    verify(
        date -> {
          date.notEqualsDatePortion(null, actual, "DateExpectationTest ::> notEqualsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_BothNull() {
    verify(
        date -> {
          date.notEqualsDatePortion(null, null, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsDatePortion(
              actual, actual.clone().addSeconds(10), "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsTimePortion(
              actual,
              actual.clone().addMinutes(10),
              "DateExpectationTest ::> notEqualsTimePortion ");
        });
    verify(
        date -> {
          date.notEqualsTimePortion(actual, null, "DateExpectationTest ::> notEqualsTimePortion ");
        });
    verify(
        date -> {
          date.notEqualsTimePortion(null, actual, "DateExpectationTest ::> notEqualsTimePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_BothNull() {
    verify(
        date -> {
          date.notEqualsTimePortion(null, null, "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsTimePortion(
              actual, actual.clone().addDays(10), "DateExpectationTest ::> equalsDatePortion ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEquals(
              actual, actual.clone().addSeconds(10), "DateExpectationTest ::> verifyNotEquals ");
        });
    verify(
        date -> {
          date.notEquals(actual, null, "DateExpectationTest ::> verifyNotEquals ");
        });
    verify(
        date -> {
          date.notEquals(null, actual, "DateExpectationTest ::> verifyNotEquals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(
        date -> {
          date.notEquals(null, null, "DateExpectationTest ::> verifyNotEquals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEquals(actual, actual.clone(), "DateExpectationTest ::> verifyNotEquals ");
        });
  }

  public abstract void verify(Consumer<CDateVerification> action);
}
