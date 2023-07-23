package org.catools.common.tests.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.interfaces.CBooleanWaiter;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CBooleanWaiterTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(toWaiter(true).waitEquals(true), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(false).waitEquals(false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitEquals(false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CVerify.Bool.isTrue(toWaiter(true).waitEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    CVerify.Bool.isTrue(toWaiter(true).waitEquals(false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    CVerify.Bool.isTrue(toWaiter(false).waitIsFalse(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    CVerify.Bool.isTrue(toWaiter(true).waitIsFalse(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    CVerify.Bool.isTrue(toWaiter(true).waitIsTrue(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N() {
    CVerify.Bool.isTrue(toWaiter(false).waitIsTrue(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(toWaiter(false).waitNotEquals(true), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(true).waitNotEquals(Boolean.valueOf(false)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitNotEquals(false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter(null).waitNotEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    CVerify.Bool.isTrue(toWaiter(true).waitNotEquals(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    CVerify.Bool.isTrue(toWaiter(true).waitNotEquals(true), "%s#%s", getParams());
  }

  private CBooleanWaiter toWaiter(Boolean val) {
    return () -> val;
  }
}
