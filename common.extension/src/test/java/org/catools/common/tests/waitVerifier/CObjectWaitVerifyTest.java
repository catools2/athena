package org.catools.common.tests.waitVerifier;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CObjectWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CObjectWaitVerifyTest extends CBaseWaitVerifyTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    verify(verifier -> toWaitVerifier(null).verifyIsNull(verifier));
    verify(verifier -> toWaitVerifier(null).verifyIsNull(verifier, 1));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N() {
    verify(verifier -> toWaitVerifier("").verifyIsNull(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    Object obj = new Object();
    verify(verifier -> toWaitVerifier(obj).verifyEquals(verifier, obj));
    verify(verifier -> toWaitVerifier(obj).verifyEquals(verifier, obj, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, null));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    verify(verifier -> toWaitVerifier(new Object()).verifyEquals(verifier, new Object()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(verifier -> toWaitVerifier(new Object()).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, new Object()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    verify(verifier -> toWaitVerifier(new Object()).verifyIsNotNull(verifier));
    verify(verifier -> toWaitVerifier(new Object()).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N() {
    verify(verifier -> toWaitVerifier(null).verifyIsNotNull(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(verifier -> toWaitVerifier(new Object()).verifyNotEquals(verifier, new Object(), 1));
    verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, new Object()));
    verify(verifier -> toWaitVerifier(new Object()).verifyNotEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    Object obj = new Object();
    verify(verifier -> toWaitVerifier(obj).verifyNotEquals(verifier, obj));
  }

  private CObjectWaitVerifier<Object, CObjectState<Object>> toWaitVerifier(Object val) {
    return new CObjectWaitVerifier<>() {
      @Override
      public CObjectState<Object> _toState(Object o) {
        return () -> val;
      }

      @Override
      public Object _get() {
        return val;
      }
    };
  }
}
