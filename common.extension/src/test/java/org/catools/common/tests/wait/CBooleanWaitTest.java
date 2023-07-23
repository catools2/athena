package org.catools.common.tests.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CBooleanWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CBooleanWaitTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(CBooleanWait.waitEquals(true, true), "%s#%s", getParams());
    CVerify.Bool.isTrue(CBooleanWait.waitEquals(false, false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    CVerify.Bool.isTrue(CBooleanWait.waitEquals(null, false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(CBooleanWait.waitEquals(null, null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CVerify.Bool.isTrue(CBooleanWait.waitEquals(true, null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    CVerify.Bool.isTrue(CBooleanWait.waitEquals(true, false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    CVerify.Bool.isTrue(CBooleanWait.waitIsFalse(false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    CVerify.Bool.isTrue(CBooleanWait.waitIsFalse(true), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    CVerify.Bool.isTrue(CBooleanWait.waitIsTrue(true), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N() {
    CVerify.Bool.isTrue(CBooleanWait.waitIsTrue(false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(CBooleanWait.waitNotEquals(false, true), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CBooleanWait.waitNotEquals(true, Boolean.valueOf(false)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    CVerify.Bool.isTrue(CBooleanWait.waitNotEquals(null, false), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isTrue(CBooleanWait.waitNotEquals(null, null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    CVerify.Bool.isTrue(CBooleanWait.waitNotEquals(true, null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    CVerify.Bool.isTrue(CBooleanWait.waitNotEquals(true, true), "%s#%s", getParams());
  }
}
