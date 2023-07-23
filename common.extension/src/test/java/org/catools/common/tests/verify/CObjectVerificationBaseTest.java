package org.catools.common.tests.verify;

import org.catools.common.extensions.verify.hard.CObjectVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class CObjectVerificationBaseTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    Object obj = new Object();
    verify(object -> object.equals(obj, obj));
    verify(object -> object.equals(obj, obj, "CollectionExpectationTest ::> equals "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(object -> object.equals(null, null));
    verify(object -> object.equals(null, null, "CollectionExpectationTest ::> equals "));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    verify(object -> object.equals(new Object(), new Object()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(object -> object.equals(new Object(), null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(object -> object.equals(null, new Object()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    verify(object -> object.isNotNull(new Object()));
    verify(object -> object.isNotNull(new Object(), "CollectionExpectationTest ::> isNotNull "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N() {
    verify(object -> object.isNotNull(null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    verify(object -> object.isNull(null));
    verify(object -> object.isNull(null, "CollectionExpectationTest ::> isNull "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N() {
    verify(object -> object.isNull(new Object()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(object -> object.notEquals(new Object(), new Object()));
    verify(object -> object.notEquals(new Object(), new Object(), "CollectionExpectationTest ::> verifyNotEquals "));
    verify(object -> object.notEquals(null, new Object()));
    verify(object -> object.notEquals(null, new Object(), "CollectionExpectationTest ::> verifyNotEquals "));
    verify(object -> object.notEquals(new Object(), null));
    verify(object -> object.notEquals(new Object(), null, "CollectionExpectationTest ::> verifyNotEquals "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(object -> object.notEquals(null, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    Object obj = new Object();
    verify(object -> object.notEquals(obj, obj));
  }

  public abstract void verify(Consumer<CObjectVerification> action);
}
