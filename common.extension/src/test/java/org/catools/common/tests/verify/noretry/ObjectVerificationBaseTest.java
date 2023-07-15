package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.hard.CObjectVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class ObjectVerificationBaseTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(
        object -> {
          Object obj = new Object();
          object.equals(obj, obj, "CollectionExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(
        object -> {
          object.equals(null, null, "CollectionExpectationTest ::> equals ");
        });
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    verify(
        object -> {
          object.equals(new Object(), new Object(), "CollectionExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(
        object -> {
          object.equals(new Object(), null, "CollectionExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(
        object -> {
          object.equals(null, new Object(), "CollectionExpectationTest ::> equals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    verify(object -> object.isNotNull(new Object(), "CollectionExpectationTest ::> isNotNull "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N() {
    verify(object -> object.isNotNull(null, "CollectionExpectationTest ::> isNotNull "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    verify(object -> object.isNull(null, "CollectionExpectationTest ::> isNull "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N() {
    verify(object -> object.isNull(new Object(), "CollectionExpectationTest ::> isNull "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(
        object ->
            object.notEquals(
                new Object(), new Object(), "CollectionExpectationTest ::> verifyNotEquals "));
    verify(
        object ->
            object.notEquals(null, new Object(), "CollectionExpectationTest ::> verifyNotEquals "));
    verify(
        object ->
            object.notEquals(new Object(), null, "CollectionExpectationTest ::> verifyNotEquals "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(
        object -> {
          object.notEquals(null, null, "CollectionExpectationTest ::> verifyNotEquals ");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    verify(
        object -> {
          Object obj = new Object();
          object.notEquals(obj, obj, "CollectionExpectationTest ::> verifyNotEquals ");
        });
  }

  public abstract void verify(Consumer<CObjectVerification> action);
}
