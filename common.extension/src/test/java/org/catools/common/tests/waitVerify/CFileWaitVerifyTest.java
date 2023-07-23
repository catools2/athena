package org.catools.common.tests.waitVerify;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.extensions.verify.interfaces.waitVerify.CFileWaitVerify;
import org.catools.common.io.CFile;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CFileWaitVerifyTest extends CBaseUnitTest {
  private String filePrefix = "R1";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists() {
    toWaitVerify(getPath("testExists", "anything")).verifyExists();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotExists() {
    toWaitVerify(getPath("testNotExists", "anything") + "1").verifyIsNotExists();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testEquals", paragraphs)).verifyEqualsStringContent(getFile("testEquals2", paragraphs));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testExistsWhenNot() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testExistsWhenNot", paragraphs) + "Invalid").verifyExists();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotExistsWhenIs() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testExistsWhenNot", paragraphs)).verifyIsNotExists();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testEquals1WhenNoDest1", paragraphs)).verifyEqualsStringContent(new CFile(getPath("testEquals1WhenNoDest12", paragraphs) + "Invalid"), 1, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoSource() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(new CFile(getPath("testEquals1WhenNoDest1", paragraphs) + "Invalid").getCanonicalPath()).verifyEqualsStringContent(getFile("testEquals1WhenNoDest12", paragraphs));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testEquals1WhenNotEqual1", paragraphs)).verifyEqualsStringContent(getFile("testEquals1WhenNotEqual12", paragraphs + "1"), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testNotEquals", paragraphs + "1")).verifyNotEqualsStringContent(getFile("testNotEquals2", paragraphs));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testNotEquals_NoDest", paragraphs + "1")).verifyNotEqualsStringContent(new CFile(getPath("testNotEquals_NoDest2", paragraphs) + "Invalid"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoSrc() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(new CFile(getPath("testNotEquals_NoSrc", paragraphs + "1") + "Invalid").getCanonicalPath()).verifyNotEqualsStringContent(getFile("testNotEquals_NoSrc2", paragraphs));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    toWaitVerify(getPath("testNotEquals_NotEqual", paragraphs)).verifyNotEqualsStringContent(getFile("testNotEquals_NotEqual2", paragraphs), 0, 100);
  }

  private CFileWaitVerify toWaitVerify(String filename) {
    return () -> new CFile(filename);
  }

  private CFile getFile(String filename, String content) {
    return CFile.fromTmp(filePrefix + "RetryFileContentExpectationTest." + filename).write(content);
  }

  private String getPath(String methodName, String content) {
    return getFile(methodName, content).getCanonicalPath();
  }
}
