package org.catools.common.tests.waitVerifier;

import org.catools.common.extensions.verify.interfaces.waitVerifier.CBooleanWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CBooleanWaitVerifyTest extends CBaseWaitVerifyTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(verifier -> toWaitVerifier(true).verifyEquals(verifier, true));
    verify(verifier -> toWaitVerifier(false).verifyEquals(verifier, false, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, false));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(verifier -> toWaitVerifier(true).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(verifier -> toWaitVerifier(true).verifyEquals(verifier, false));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    verify(verifier -> toWaitVerifier(false).verifyIsFalse(verifier));
    verify(verifier -> toWaitVerifier(false).verifyIsFalse(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    verify(verifier -> toWaitVerifier(true).verifyIsFalse(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    verify(verifier -> toWaitVerifier(true).verifyIsTrue(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N() {
    verify(verifier -> toWaitVerifier(false).verifyIsTrue(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(verifier -> toWaitVerifier(false).verifyNotEquals(verifier, true));
    verify(verifier -> toWaitVerifier(true).verifyNotEquals(verifier, Boolean.valueOf(false), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, false));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    verify(verifier -> toWaitVerifier(true).verifyNotEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    verify(verifier -> toWaitVerifier(true).verifyNotEquals(verifier, true));
  }

  private CBooleanWaitVerifier toWaitVerifier(Boolean val) {
    return () -> val;
  }
}
