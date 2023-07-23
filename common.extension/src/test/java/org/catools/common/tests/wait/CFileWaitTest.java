package org.catools.common.tests.wait;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CFileWait;
import org.catools.common.io.CFile;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

public class CFileWaitTest extends CBaseUnitTest {
  private String filePrefix = "R2";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsExists(getFile("testExists", "anything"), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists1() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsExists(getPath("testExists", "anything"), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotExists() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotExists(getFile("testNotExists1"), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotExists1() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotExists(getPath("testNotExists1"), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getFile("testEquals", paragraphs), getFile("testEquals2", paragraphs), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getPath("testEquals1", paragraphs), getPath("testEquals2", paragraphs), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testExistsWhenNot() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitIsExists(new CFile(getPath("testExistsWhenNot", paragraphs) + "Invalid"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testExistsWhenNot1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter().waitIsExists(getPath("testExistsWhenNot", paragraphs) + "Invalid", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotExistsWhenIs() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotExists(getFile("testExistsWhenNot", paragraphs), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotExistsWhenIs1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotExists(getPath("testExistsWhenNot", paragraphs), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getFile("testEquals1WhenNoDest1", paragraphs),
                new CFile(getPath("testEquals1WhenNoDest12", paragraphs) + "Invalid"),
                1,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals1WhenNoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getPath("testEquals1WhenNoDest1", paragraphs),
                getPath("testEquals1WhenNoDest12", paragraphs) + "Invalid",
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoSource() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getPath("testEquals1WhenNoDest1", paragraphs) + "Invalid",
                getPath("testEquals1WhenNoDest12", paragraphs),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoSource1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                new CFile(getPath("testEquals1WhenNoDest1", paragraphs) + "Invalid"),
                getFile("testEquals1WhenNoDest12", paragraphs),
                1,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getPath("testEquals1WhenNotEqual1", paragraphs),
                getPath("testEquals1WhenNotEqual12", paragraphs + "1"),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNotEqual1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsStringContent(
                getFile("testEquals1WhenNotEqual1", paragraphs),
                getFile("testEquals1WhenNotEqual12", paragraphs + "1"),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getPath("testNotEquals", paragraphs + "1"),
                getPath("testNotEquals2", paragraphs),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getFile("testNotEquals", paragraphs + "1"),
                getFile("testNotEquals2", paragraphs),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getPath("testNotEquals_NoDest", paragraphs + "1"),
                getPath("testNotEquals_NoDest2", paragraphs) + "Invalid",
                1,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals1_NoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getFile("testNotEquals_NoDest", paragraphs + "1"),
                new CFile(getPath("testNotEquals_NoDest2", paragraphs) + "Invalid"),
                1,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoSrc() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getPath("testNotEquals_NoSrc", paragraphs + "1") + "Invalid",
                getPath("testNotEquals_NoSrc2", paragraphs),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals1_NoSrc() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                new CFile(getPath("testNotEquals_NoSrc", paragraphs + "1") + "Invalid"),
                getFile("testNotEquals_NoSrc2", paragraphs),
                1,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getPath("testNotEquals_NotEqual", paragraphs),
                getPath("testNotEquals_NotEqual2", paragraphs),
                0,
                100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals1_NotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsStringContent(
                getFile("testNotEquals1_NotEqual", paragraphs),
                getFile("testNotEquals1_NotEqual2", paragraphs),
                0,
                100),
        "%s#%s",
        getParams());
  }

  private CFileWait toWaiter() {
    return new CFileWait();
  }

  private CFile getFile(String filename) {
    CFile cFile = CFile.fromTmp(filePrefix + "RetryFileContentExpectationTest." + filename);
    CSleeper.sleepTightInSeconds(1);
    return cFile;
  }

  private CFile getFile(String filename, String content) {
    CFile write = CFile.fromTmp(filePrefix + "RetryFileContentExpectationTest." + filename).write(content);
    CSleeper.sleepTightInSeconds(1);
    return write;
  }

  private String getPath(String methodName) {
    return getFile(methodName).getCanonicalPath();
  }

  private String getPath(String methodName, String content) {
    return getFile(methodName, content).getCanonicalPath();
  }
}
