package org.catools.common.tests.verify.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CBooleanWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class BooleanWaitTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(new CBooleanWait().waitEquals(true, true, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(new CBooleanWait().waitEquals(false, false, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    CVerify.Bool.isTrue(new CBooleanWait().waitEquals(null, false, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    CVerify.Bool.isTrue(new CBooleanWait().waitEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    CVerify.Bool.isTrue(new CBooleanWait().waitEquals(true, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    CVerify.Bool.isTrue(new CBooleanWait().waitEquals(true, false, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFalse() {
    CVerify.Bool.isTrue(new CBooleanWait().waitIsFalse(false, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsFalse_N() {
    CVerify.Bool.isTrue(new CBooleanWait().waitIsFalse(true, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTrue() {
    CVerify.Bool.isTrue(new CBooleanWait().waitIsTrue(true, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsTrue_N() {
    CVerify.Bool.isTrue(new CBooleanWait().waitIsTrue(false, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(new CBooleanWait().waitNotEquals(false, true, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        new CBooleanWait().waitNotEquals(true, Boolean.valueOf(false), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ActualNull() {
    CVerify.Bool.isTrue(new CBooleanWait().waitNotEquals(null, false, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    CVerify.Bool.isTrue(new CBooleanWait().waitNotEquals(null, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals_ExpectedNull() {
    CVerify.Bool.isTrue(new CBooleanWait().waitNotEquals(true, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_N() {
    CVerify.Bool.isTrue(new CBooleanWait().waitNotEquals(true, true, 0, 100), "%s#%s", getParams());
  }
}
