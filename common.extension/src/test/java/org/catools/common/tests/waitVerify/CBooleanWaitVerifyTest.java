package org.catools.common.tests.waitVerify;

import org.catools.common.extensions.verify.interfaces.waitVerify.CBooleanWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CBooleanWaitVerifyTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    toWaitVerify(true).verifyEquals(true);
    toWaitVerify(false).verifyEquals(false);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    toWaitVerify(null).verifyEquals(false);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    toWaitVerify(null).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    toWaitVerify(true).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    toWaitVerify(true).verifyEquals(false);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    toWaitVerify(false).verifyIsFalse();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    toWaitVerify(true).verifyIsFalse();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    toWaitVerify(true).verifyIsTrue();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N() {
    toWaitVerify(false).verifyIsTrue();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    toWaitVerify(false).verifyNotEquals(true);
    toWaitVerify(true).verifyNotEquals(Boolean.valueOf(false));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    toWaitVerify(null).verifyNotEquals(false);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    toWaitVerify(null).verifyNotEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    toWaitVerify(true).verifyNotEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    toWaitVerify(true).verifyNotEquals(true);
  }

  private CBooleanWaitVerify toWaitVerify(Boolean val) {
    return () -> val;
  }
}
