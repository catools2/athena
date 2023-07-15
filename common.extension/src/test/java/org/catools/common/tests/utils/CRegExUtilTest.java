package org.catools.common.tests.utils;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CRegExUtil;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class CRegExUtilTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGroups() {
    CList<String> groups =
        CList.of(
            CRegExUtil.groups(
                "kimiak2000@gmail.com",
                Pattern.compile("([A-Za-z0-9\\+_-]+)@([A-Za-z0-9]+)\\.([A-Za-z0-9]+)")));
    groups.verifySizeEquals(3, "All group found");
    groups.verifyContains("kimiak2000", "first group found");
    groups.verifyContains("gmail", "second group found");
    groups.verifyContains("com", "third group found");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaNumeric() {
    CVerify.Bool.isFalse(
        CRegExUtil.isAlphaNumeric("kimiak2000@gmail.com"), "isAlphaNumeric works fine");
    CVerify.Bool.isTrue(
        CRegExUtil.isAlphaNumeric("kimiak2000gmailcom"), "isAlphaNumeric works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatch() {
    CVerify.Bool.isTrue(
        CRegExUtil.isMatch("kimiak2000@gmail.com", "[A-Za-z0-9\\+_-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+"),
        "isMatch works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchPattern() {
    CVerify.Bool.isTrue(
        CRegExUtil.isMatch(
            "kimiak2000@gmail.com",
            Pattern.compile("[A-Za-z0-9\\+_-]+\\@[A-Za-z0-9]+\\.[A-Za-z0-9]+")),
        "isMatch works " + "fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    CVerify.Bool.isFalse(CRegExUtil.isNumeric("12er567"), "isNumeric works fine");
    CVerify.Bool.isTrue(CRegExUtil.isNumeric("23456789"), "isNumeric works fine");
  }
}
