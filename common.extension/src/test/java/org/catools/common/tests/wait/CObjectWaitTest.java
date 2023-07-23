package org.catools.common.tests.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CObjectWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CObjectWaitTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    Object obj = new Object();
    CVerify.Bool.isTrue(toWaiter().waitEquals(obj, obj, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(toWaiter().waitEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new Object(), new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    CVerify.Bool.isTrue(toWaiter().waitEquals(new Object(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_N3() {
    CVerify.Bool.isFalse(toWaiter().waitEquals(null, new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotNull(new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull_N() {
    CVerify.Bool.isFalse(toWaiter().waitIsNotNull(null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEquals(new Object(), new Object(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(null, new Object(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(new Object(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isFalse(toWaiter().waitNotEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    Object obj = new Object();
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(obj, obj, 0, 100), "%s#%s", getParams());
  }

  private CObjectWait toWaiter() {
    return new CObjectWait();
  }
}
