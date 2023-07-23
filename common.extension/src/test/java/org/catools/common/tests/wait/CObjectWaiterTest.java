package org.catools.common.tests.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.interfaces.CObjectWaiter;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CObjectWaiterTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitIsNull(), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N() {
    CVerify.Bool.isTrue(toWaiter("").waitIsNull(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    Object obj = new Object();
    CVerify.Bool.isTrue(toWaiter(obj).waitEquals(obj), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitEquals(null), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    CVerify.Bool.isTrue(toWaiter(new Object()).waitEquals(new Object()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    CVerify.Bool.isTrue(toWaiter(new Object()).waitEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    CVerify.Bool.isTrue(toWaiter(null).waitEquals(new Object()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    CVerify.Bool.isTrue(toWaiter(new Object()).waitIsNotNull(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull_N() {
    CVerify.Bool.isFalse(toWaiter(null).waitIsNotNull(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(toWaiter(new Object()).waitNotEquals(new Object()), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(null).waitNotEquals(new Object()), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new Object()).waitNotEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitNotEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    Object obj = new Object();
    CVerify.Bool.isTrue(toWaiter(obj).waitNotEquals(obj), "%s#%s", getParams());
  }

  private CObjectWaiter toWaiter(Object val) {
    return () -> val;
  }
}
