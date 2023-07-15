package org.catools.common.tests.utils;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CSystemUtil;
import org.testng.annotations.Test;

public class CSystemUtilTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetPlatform() {
    CVerify.String.isNotBlank(CSystemUtil.getPlatform().name(), "Method works as designed.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetUserName() {
    CVerify.String.isNotBlank(CSystemUtil.getUserName(), "Method works as designed.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetHostIP() {
    CVerify.String.isNotBlank(CSystemUtil.getHostIP(), "Method works as designed.");
  }
}
