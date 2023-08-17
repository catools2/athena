package org.catools.common.tests.utils;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CStringUtil;
import org.testng.annotations.Test;

public class CStringUtilTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveWhiteSpaces() {
    CVerify.String.equals(
        CStringUtil.removeWhiteSpaces("Q !\tw\r2E#r4"), "Q!w2E#r4", "removeWhiteSpaces works fine");
  }
}
