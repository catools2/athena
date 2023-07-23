package org.catools.common.tests.waitVerify;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.waitVerify.CObjectWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CObjectWaitVerifyTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    toWaitVerify(null).verifyIsNull();
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N() {
    toWaitVerify("").verifyIsNull();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    Object obj = new Object();
    toWaitVerify(obj).verifyEquals(obj);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    toWaitVerify(null).verifyEquals(null);
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    toWaitVerify(new Object()).verifyEquals(new Object());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    toWaitVerify(new Object()).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    toWaitVerify(null).verifyEquals(new Object());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    toWaitVerify(new Object()).verifyIsNotNull();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N() {
    toWaitVerify(null).verifyIsNotNull();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    toWaitVerify(new Object()).verifyNotEquals(new Object());
    toWaitVerify(null).verifyNotEquals(new Object());
    toWaitVerify(new Object()).verifyNotEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    toWaitVerify(null).verifyNotEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    Object obj = new Object();
    toWaitVerify(obj).verifyNotEquals(obj);
  }

  private CObjectWaitVerify<Object, CObjectState<Object>> toWaitVerify(Object val) {
    return new CObjectWaitVerify<>() {
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
