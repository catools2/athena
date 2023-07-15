package org.catools.common.tests.utils;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CObjectUtil;
import org.testng.annotations.Test;

public class CObjectUtilTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCloneEquals() {
    CVerify.String.equals("Actual", CObjectUtil.clone("Actual"), "Objects are equal.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCloneNotEquals() {
    A a = new A();
    CVerify.Object.notEquals(a, CObjectUtil.clone(a), "Objects are equal.");
  }

  public static class A {
  }
}
