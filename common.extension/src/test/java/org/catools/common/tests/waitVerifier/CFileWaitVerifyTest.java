package org.catools.common.tests.waitVerifier;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CFileWaitVerifier;
import org.catools.common.io.CFile;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CFileWaitVerifyTest extends CBaseWaitVerifyTest {
  private String filePrefix = "R1";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists() {
    verify(verifier -> toWaitVerifier(getPath("testExists", "anything")).verifyExists(verifier));
    verify(verifier -> toWaitVerifier(getPath("testExists", "anything")).verifyExists(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotExists() {
    verify(verifier -> toWaitVerifier(getPath("testNotExists", "anything") + "1").verifyIsNotExists(verifier));
    verify(verifier -> toWaitVerifier(getPath("testNotExists", "anything") + "1").verifyIsNotExists(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testEquals", paragraphs))
        .verifyEqualsStringContent(verifier, getFile("testEquals2", paragraphs)));

    verify(verifier -> toWaitVerifier(getPath("testEquals", paragraphs))
        .verifyEqualsStringContent(verifier, getFile("testEquals2", paragraphs), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testExistsWhenNot() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testExistsWhenNot", paragraphs) + "Invalid").verifyExists(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotExistsWhenIs() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testExistsWhenNot", paragraphs)).verifyIsNotExists(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testEquals1WhenNoDest1", paragraphs))
        .verifyEqualsStringContent(verifier, new CFile(getPath("testEquals1WhenNoDest12", paragraphs) + "Invalid"), 1, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNoSource() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(new CFile(getPath("testEquals1WhenNoDest1", paragraphs) + "Invalid").getCanonicalPath())
        .verifyEqualsStringContent(verifier, getFile("testEquals1WhenNoDest12", paragraphs)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWhenNotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testEquals1WhenNotEqual1", paragraphs))
        .verifyEqualsStringContent(verifier, getFile("testEquals1WhenNotEqual12", paragraphs + "1"), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    String path = getPath("testNotEquals", paragraphs + "1");
    verify(verifier -> toWaitVerifier(path).verifyNotEqualsStringContent(verifier, getFile("testNotEquals2", paragraphs)));
    verify(verifier -> toWaitVerifier(path).verifyNotEqualsStringContent(verifier, getFile("testNotEquals2", paragraphs), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoDest() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testNotEquals_NoDest", paragraphs + "1"))
        .verifyNotEqualsStringContent(verifier, new CFile(getPath("testNotEquals_NoDest2", paragraphs) + "Invalid"), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NoSrc() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(new CFile(getPath("testNotEquals_NoSrc", paragraphs + "1") + "Invalid").getCanonicalPath())
        .verifyNotEqualsStringContent(verifier, getFile("testNotEquals_NoSrc2", paragraphs), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_NotEqual() {
    String paragraphs = RandomStringUtils.randomAlphabetic(50);
    verify(verifier -> toWaitVerifier(getPath("testNotEquals_NotEqual", paragraphs))
        .verifyNotEqualsStringContent(verifier, getFile("testNotEquals_NotEqual2", paragraphs), 1));
  }

  private CFileWaitVerifier toWaitVerifier(String filename) {
    return () -> new CFile(filename);
  }

  private CFile getFile(String filename, String content) {
    return CFile.fromTmp(filePrefix + "RetryFileContentExpectationTest." + filename).write(content);
  }

  private String getPath(String methodName, String content) {
    return getFile(methodName, content).getCanonicalPath();
  }
}
