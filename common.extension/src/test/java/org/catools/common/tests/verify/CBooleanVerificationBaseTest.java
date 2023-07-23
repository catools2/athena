package org.catools.common.tests.verify;

import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class CBooleanVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(bool -> bool.equals(true, true));
    verify(bool -> bool.equals(true, true, "BooleanExpectationTest ::> equals ::> true==true"));
    verify(bool -> bool.equals(false, false));
    verify(bool -> bool.equals(false, false, "BooleanExpectationTest ::> equals ::> true==true"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(bool -> bool.equals(null, false));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(bool -> bool.equals(null, null));
    verify(bool -> bool.equals(null, null, "BooleanExpectationTest ::> negative equals ::> true==true"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(bool -> bool.equals(true, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(bool -> bool.equals(true, false, "BooleanExpectationTest ::> negative equals ::> true==true"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    verify(bool -> bool.isFalse(false));
    verify(bool -> bool.isFalse(false, "BooleanExpectationTest ::> verifyIsFalse ::> false"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    verify(bool -> bool.isFalse(true, "BooleanExpectationTest ::> negative verifyIsFalse ::> false"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    verify(bool -> bool.isTrue(true));
    verify(bool -> bool.isTrue(true, "BooleanExpectationTest ::> verifyIsTrue ::> true"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N1() {
    verify(bool -> bool.isTrue(false));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(bool -> bool.notEquals(false, true));
    verify(bool -> bool.notEquals(false, true, "BooleanExpectationTest ::> verifyNotEquals ::> false==true"));
    verify(bool -> bool.notEquals(true, Boolean.valueOf(false)));
    verify(bool -> bool.notEquals(true, Boolean.valueOf(false), "BooleanExpectationTest ::> verifyNotEquals ::> true==Boolean"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    verify(bool -> bool.notEquals(null, false));
    verify(bool -> bool.notEquals(null, false, "BooleanExpectationTest ::> negative equals ::> true==true"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(bool -> bool.notEquals(null, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    verify(bool -> bool.notEquals(true, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    verify(bool -> bool.notEquals(true, true, "BooleanExpectationTest ::> negative verifyNotEquals ::> false==true"));
  }

  public abstract void verify(Consumer<CBooleanVerification> action);
}
