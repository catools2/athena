package org.catools.common.tests.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CObjectWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CObjectWaitTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNull() {
    CVerify.Bool.isTrue(CObjectWait.waitIsNull(null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N1() {
    CVerify.Bool.isTrue(CObjectWait.waitIsNull(new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    Object obj = new Object();
    CVerify.Bool.isTrue(CObjectWait.waitEquals(obj, obj, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(CObjectWait.waitEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N1() {
    CVerify.Bool.isTrue(CObjectWait.waitEquals(new Object(), new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    CVerify.Bool.isTrue(CObjectWait.waitEquals(new Object(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_N3() {
    CVerify.Bool.isFalse(CObjectWait.waitEquals(null, new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    CVerify.Bool.isTrue(CObjectWait.waitIsNotNull(new Object(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull_N() {
    CVerify.Bool.isFalse(CObjectWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(CObjectWait.waitNotEquals(new Object(), new Object(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CObjectWait.waitNotEquals(null, new Object(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CObjectWait.waitNotEquals(new Object(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isFalse(CObjectWait.waitNotEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    Object obj = new Object();
    CVerify.Bool.isTrue(CObjectWait.waitNotEquals(obj, obj, 0, 100), "%s#%s", getParams());
  }
}
