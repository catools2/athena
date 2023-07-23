package org.catools.common.tests.extension;

import org.catools.common.extensions.types.CDynamicBooleanExtension;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public class CBooleanWaitVerifierTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(verifier -> toWaitVerifier(true).verifyEquals(verifier, true));
    verify(verifier -> toWaitVerifier(false).verifyEquals(verifier, false));
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
    verify(verifier -> toWaitVerifier(true).verifyNotEquals(verifier, Boolean.valueOf(false)));
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

  public void verify(Consumer<CVerifier> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier);
    verifier.verify();
  }

  private CDynamicBooleanExtension toWaitVerifier(Boolean val) {
    return new CDynamicBooleanExtension() {
      @Override
      public Boolean _get() {
        return val;
      }
    };
  }
}