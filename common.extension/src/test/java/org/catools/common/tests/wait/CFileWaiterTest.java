package org.catools.common.tests.wait;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.interfaces.CFileWaiter;
import org.catools.common.io.CFile;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CFileWaiterTest extends CBaseUnitTest {
  private String filePrefix = "R1";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists() {
    CVerify.Bool.isTrue(
        toWaiter(getPath("testExists", "anything")).waitIsExists(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotExists() {
    CVerify.Bool.isTrue(
        toWaiter(getPath("testNotExists", "anything") + "1").waitIsNotExists(),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testEquals", paragraphs))
            .waitEqualsStringContent(getFile("testEquals2", paragraphs)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testExistsWhenNot() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testExistsWhenNot", paragraphs) + "Invalid").waitIsExists(1),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotExistsWhenIs() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testExistsWhenNot", paragraphs)).waitIsNotExists(1),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testEquals1WhenNoDest1", paragraphs))
            .waitEqualsStringContent(
                new CFile(getPath("testEquals1WhenNoDest12", paragraphs) + "Invalid"), 1, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoSource() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(
            new CFile(getPath("testEquals1WhenNoDest1", paragraphs) + "Invalid")
                .getCanonicalPath())
            .waitEqualsStringContent(getFile("testEquals1WhenNoDest12", paragraphs)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testEquals1WhenNotEqual1", paragraphs))
            .waitEqualsStringContent(
                getFile("testEquals1WhenNotEqual12", paragraphs + "1"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testNotEquals", paragraphs + "1"))
            .waitNotEqualsStringContent(getFile("testNotEquals2", paragraphs)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testNotEquals_NoDest", paragraphs + "1"))
            .waitNotEqualsStringContent(
                new CFile(getPath("testNotEquals_NoDest2", paragraphs) + "Invalid")),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoSrc() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(
            new CFile(getPath("testNotEquals_NoSrc", paragraphs + "1") + "Invalid")
                .getCanonicalPath())
            .waitNotEqualsStringContent(getFile("testNotEquals_NoSrc2", paragraphs)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter(getPath("testNotEquals_NotEqual", paragraphs))
            .waitNotEqualsStringContent(getFile("testNotEquals_NotEqual2", paragraphs), 0, 100),
        "%s#%s",
        getParams());
  }

  private CFileWaiter toWaiter(String filename) {
    return () -> new CFile(filename);
  }

  private CFile getFile(String filename, String content) {
    return CFile.fromTmp(filePrefix + "RetryFileContentExpectationTest." + filename).write(content);
  }

  private String getPath(String methodName, String content) {
    return getFile(methodName, content).getCanonicalPath();
  }
}
