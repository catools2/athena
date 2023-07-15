package org.catools.common.tests.verify.retry;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.hard.CDateVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class CDateVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualByFormat() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual, actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualByFormat_ActualNull() {
    verify(
        date -> {
          CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(null, expected, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualByFormat_BothNull() {
    verify(
        date -> {
          date.equalsByFormat(null, null, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualByFormat_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(actual, null, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualByFormat_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual, actual.clone().addMinutes(10), "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualTimePortion() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualTimePortion_ActualNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(null, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualTimePortion_BothNull() {
    verify(
        date -> {
          date.equalsTimePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualTimePortion_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualTimePortion_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsDatePortion() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, actual.clone().addMinutes(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsDatePortion_ActualNull() {
    verify(
        date -> {
          CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(null, expected, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsDatePortion_BothNull() {
    verify(
        date -> {
          date.equalsDatePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsDatePortion_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsDatePortion_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_ActualNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals_BothNull() {
    verify(
        date -> {
          date.equals(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, actual.clone().addMinutes(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsByFormat(
              actual, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsByFormat(actual, null, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsByFormat(null, actual, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualByFormat_BothNull() {
    verify(
        date -> {
          date.notEqualsByFormat(null, null, "yyyy-MM-dd HH:mm", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualByFormat_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsByFormat(
              actual, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsDatePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsDatePortion(actual, null, "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsDatePortion(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualDatePortion_BothNull() {
    verify(
        date -> {
          date.notEqualsDatePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualDatePortion_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsDatePortion(actual, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsTimePortion(actual, actual.clone().addMinutes(10), "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsTimePortion(actual, null, "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsTimePortion(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualTimePortion_BothNull() {
    verify(
        date -> {
          date.notEqualsTimePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualTimePortion_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsTimePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEquals(actual, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEquals(actual, null, "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEquals(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_BothNull() {
    verify(
        date -> {
          date.notEquals(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEquals(actual, actual.clone(), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual, actual.clone().addMinutes(10), "yyyy-MM-dd HH:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ActualNull() {
    verify(
        date -> {
          CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(null, expected, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualByFormat_BothNull() {
    verify(
        date -> {
          date.equalsByFormat(null, null, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(actual, null, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualByFormat_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsByFormat(
              actual, actual.clone().addMinutes(10), "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ActualNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(null, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualTimePortion_BothNull() {
    verify(
        date -> {
          date.equalsTimePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualTimePortion_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsTimePortion(actual, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, actual.clone().addMinutes(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ActualNull() {
    verify(
        date -> {
          CDate expected = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(null, expected, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsDatePortion_BothNull() {
    verify(
        date -> {
          date.equalsDatePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDatePortion_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equalsDatePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(
        date -> {
          date.equals(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.equals(actual, actual.clone().addMinutes(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualByFormat() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsByFormat(
              actual, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsByFormat(actual, null, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsByFormat(null, actual, "yyyy-MM-dd HH:mm:ss", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_BothNull() {
    verify(
        date -> {
          date.notEqualsByFormat(null, null, "yyyy-MM-dd HH:mm", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualByFormat_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsByFormat(
              actual, actual.clone().addSeconds(10), "yyyy-MM-dd HH:mm", "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualDatePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsDatePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsDatePortion(actual, null, "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsDatePortion(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_BothNull() {
    verify(
        date -> {
          date.notEqualsDatePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualDatePortion_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsDatePortion(actual, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualTimePortion() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEqualsTimePortion(actual, actual.clone().addMinutes(10), "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsTimePortion(actual, null, "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEqualsTimePortion(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_BothNull() {
    verify(
        date -> {
          date.notEqualsTimePortion(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualTimePortion_Equals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEqualsTimePortion(actual, actual.clone().addDays(10), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
    verify(
        date -> {
          date.notEquals(actual, actual.clone().addSeconds(10), "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEquals(actual, null, "%s#%s", getParams());
        });
    verify(
        date -> {
          date.notEquals(null, actual, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(
        date -> {
          date.notEquals(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    verify(
        date -> {
          CDate actual = CDate.valueOf("2018-01-01", "yyyy-MM-dd");
          date.notEquals(actual, actual.clone(), "%s#%s", getParams());
        });
  }

  public abstract void verify(Consumer<CDateVerification> action);
}
