package org.catools.common.tests.verify;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.extensions.verify.hard.CFileVerification;
import org.catools.common.io.CFile;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public abstract class CFileVerificationBaseTest extends CBaseUnitTest {
  // Due to file dependency we might have conflict so we need to put prefix for each class
  private String filePrefix;

  public CFileVerificationBaseTest(String filePrefix) {
    this.filePrefix = filePrefix;
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists() {
    verify(fileContent -> fileContent.exists(getPath("testExists", "anything")));
    verify(fileContent -> fileContent.exists(getPath("testExists", "anything"), "FileContentExpectationTest ::> testExists"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotExists() {
    verify(fileContent -> fileContent.notExists(getPath("testExists", "anything") + "InvalidPath"));
    verify(fileContent -> fileContent.notExists(getPath("testExists", "anything") + "InvalidPath", "FileContentExpectationTest ::> testNotExists"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.equalsStringContent(getPath("testEquals1", paragraphs), getPath("testEquals2", paragraphs)));
    verify(fileContent -> fileContent.equalsStringContent(getPath("testEquals1", paragraphs), getPath("testEquals2", paragraphs), "FileContentExpectationTest ::> testEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.equalsStringContent(getFile("testEquals11", paragraphs), getFile("testEquals12", paragraphs)));
    verify(fileContent -> fileContent.equalsStringContent(getFile("testEquals11", paragraphs), getFile("testEquals12", paragraphs), "FileContentExpectationTest ::> testEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testExistsWhenNot() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.exists(getFile("testExistsWhenNot", paragraphs) + "Invalid"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotExistsWhenIs() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notExists(getFile("testExistsWhenNot", paragraphs)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals1WhenNoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.equalsStringContent(getFile("testEquals1WhenNoDest1", paragraphs), CFile.fromTmp(RandomStringUtils.randomAlphabetic(10))));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals1WhenNoSource() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.equalsStringContent(CFile.fromTmp(RandomStringUtils.randomAlphabetic(10)), getFile("testEquals1WhenNoSource2", paragraphs + "1")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals1WhenNotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.equalsStringContent(getFile("testEquals1WhenNotEqual1", paragraphs), getFile("testEquals1WhenNotEqual2", paragraphs + "1")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.equalsStringContent(getPath("testEqualsWhenNotEqual1", paragraphs), getPath("testEqualsWhenNotEqual2", paragraphs + "1")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notEqualsStringContent(getPath("testNotEquals1", paragraphs), getPath("testNotEquals2", "1" + paragraphs)));
    verify(fileContent -> fileContent.notEqualsStringContent(getPath("testNotEquals1", paragraphs), getPath("testNotEquals2", "1" + paragraphs), "FileContentExpectationTest ::> notEqualsStringContent"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals1() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notEqualsStringContent(getFile("testNotEquals11", paragraphs), getFile("testNotEquals12", "1" + paragraphs)));
    verify(fileContent -> fileContent.notEqualsStringContent(getFile("testNotEquals11", paragraphs), getFile("testNotEquals12", "1" + paragraphs), "FileContentExpectationTest ::> notEqualsStringContent"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals1_NoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notEqualsStringContent(getFile("testNotEquals1_NoDest1", paragraphs), CFile.fromTmp(RandomStringUtils.randomAlphabetic(10))));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals1_NoSrc() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notEqualsStringContent(CFile.fromTmp(RandomStringUtils.randomAlphabetic(10)), getFile("testNotEquals1_NoSrc2", paragraphs)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals1_NotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notEqualsStringContent(getFile("testNotEquals1_NotEqual1", paragraphs), getFile("testNotEquals1_NotEqual2", paragraphs)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(fileContent -> fileContent.notEqualsStringContent(getPath("testNotEquals_NotEquals1", paragraphs), getPath("testNotEquals_NotEquals2", paragraphs)));
  }

  public abstract void verify(Consumer<CFileVerification> action);

  private CFile getFile(String filename, String content) {
    return CFile.fromTmp(filePrefix + filename).write(content);
  }

  private String getPath(String methodName, String content) {
    return getFile("FileContentExpectationTest." + methodName, content).getCanonicalPath();
  }
}
