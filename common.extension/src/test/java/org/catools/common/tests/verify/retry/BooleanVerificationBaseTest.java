package org.catools.common.tests.verify.retry;

import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class BooleanVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals() {
    verify(bool -> bool.equals(true, true, "%s#%s", getParams()));
    verify(bool -> bool.equals(false, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_ActualNull() {
    verify(bool -> bool.equals(null, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals_BothNull() {
    verify(bool -> bool.equals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_ExpectedNull() {
    verify(bool -> bool.equals(true, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N() {
    verify(bool -> bool.equals(true, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsFalse() {
    verify(bool -> bool.isFalse(false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsFalse_N() {
    verify(bool -> bool.isFalse(true, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsTrue() {
    verify(bool -> bool.isTrue(true, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsTrue_N() {
    verify(bool -> bool.isTrue(false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEquals() {
    verify(bool -> bool.notEquals(false, true, "%s#%s", getParams()));
    verify(bool -> bool.notEquals(true, Boolean.valueOf(false), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEquals_ActualNull() {
    verify(bool -> bool.notEquals(null, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_BothNull() {
    verify(bool -> bool.notEquals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEquals_ExpectedNull() {
    verify(bool -> bool.notEquals(true, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_N() {
    verify(bool -> bool.notEquals(true, true, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(bool -> bool.equals(true, true, "%s#%s", getParams()));
    verify(bool -> bool.equals(false, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(bool -> bool.equals(null, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(bool -> bool.equals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(bool -> bool.equals(true, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(bool -> bool.equals(true, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    verify(bool -> bool.isFalse(false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    verify(bool -> bool.isFalse(true, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    verify(bool -> bool.isTrue(true, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N() {
    verify(bool -> bool.isTrue(false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(bool -> bool.notEquals(false, true, "%s#%s", getParams()));
    verify(bool -> bool.notEquals(true, Boolean.valueOf(false), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    verify(bool -> bool.notEquals(null, false, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(bool -> bool.notEquals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    verify(bool -> bool.notEquals(true, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    verify(bool -> bool.notEquals(true, true, "%s#%s", getParams()));
  }

  public abstract void verify(Consumer<CBooleanVerification> action);
}
