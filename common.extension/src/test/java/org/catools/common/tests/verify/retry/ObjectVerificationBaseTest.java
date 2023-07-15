package org.catools.common.tests.verify.retry;

import org.catools.common.extensions.verify.hard.CObjectVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class ObjectVerificationBaseTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals() {
    verify(
        object -> {
          Object obj = new Object();
          object.equals(obj, obj, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals_BothNull() {
    verify(
        object -> {
          object.equals(null, null, "%s#%s", getParams());
        });
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N1() {
    verify(
        object -> {
          object.equals(new Object(), new Object(), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N2() {
    verify(
        object -> {
          object.equals(new Object(), null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N3() {
    verify(
        object -> {
          object.equals(null, new Object(), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotNull() {
    verify(object -> object.isNotNull(new Object(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotNull_N() {
    verify(object -> object.isNotNull(null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNull() {
    verify(object -> object.isNull(null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNull_N() {
    verify(object -> object.isNull(new Object(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEquals() {
    verify(object -> object.notEquals(new Object(), new Object(), "%s#%s", getParams()));
    verify(object -> object.notEquals(null, new Object(), "%s#%s", getParams()));
    verify(object -> object.notEquals(new Object(), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_BothNull() {
    verify(
        object -> {
          object.notEquals(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_N() {
    verify(
        object -> {
          Object obj = new Object();
          object.notEquals(obj, obj, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(
        object -> {
          Object obj = new Object();
          object.equals(obj, obj, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(
        object -> {
          object.equals(null, null, "%s#%s", getParams());
        });
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    verify(
        object -> {
          object.equals(new Object(), new Object(), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(
        object -> {
          object.equals(new Object(), null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(
        object -> {
          object.equals(null, new Object(), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    verify(object -> object.isNotNull(new Object(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N() {
    verify(object -> object.isNotNull(null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    verify(object -> object.isNull(null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N() {
    verify(object -> object.isNull(new Object(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(object -> object.notEquals(new Object(), new Object(), "%s#%s", getParams()));
    verify(object -> object.notEquals(null, new Object(), "%s#%s", getParams()));
    verify(object -> object.notEquals(new Object(), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(
        object -> {
          object.notEquals(null, null, "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    verify(
        object -> {
          Object obj = new Object();
          object.notEquals(obj, obj, "%s#%s", getParams());
        });
  }

  public abstract void verify(Consumer<CObjectVerification> action);
}
