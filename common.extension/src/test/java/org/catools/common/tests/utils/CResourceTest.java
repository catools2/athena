package org.catools.common.tests.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.collections.CList;
import org.catools.common.exception.CResourceNotFoundException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CResourceTest extends CBaseUnitTest {

  private String VALID_RESOURCE_NAME = "testData/CResourceTest/CResourceTest.txt";
  private String INVALID_RESOURCE_NAME = "testData/CResourceTest/ResourceTest.txt";
  private CResource VALID_RESOURCE = new CResource(VALID_RESOURCE_NAME, CResourceTest.class);
  private CResource INVALID_RESOURCE = new CResource(INVALID_RESOURCE_NAME, CResourceTest.class);

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists() {
    CVerify.Bool.isTrue(VALID_RESOURCE.exists(), "exists works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists_Invalid() {
    CVerify.Bool.isFalse(INVALID_RESOURCE.exists(), "exists works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testExists_InvalidClassLoader() {
    CVerify.Bool.isTrue(
        new CResource(VALID_RESOURCE_NAME, null).exists(), "saveToFolder works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetLines() {
    CList<String> lines = VALID_RESOURCE.readLines();
    CVerify.Int.equals(lines.size(), 3, "getLines() works fine");
    CVerify.String.equals(
        lines.join("|").replaceAll("\r", ""),
        "1-Some text;|2-Some text;|3-Some text;",
        "getLines works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CResourceNotFoundException.class)
  public void testGetLines_Invalid() {
    INVALID_RESOURCE.readLines();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetResourceName() {
    CVerify.String.equals(
        VALID_RESOURCE.getResourceFullName(),
        VALID_RESOURCE_NAME,
        "getResourceFullName() works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetResourceName_Invalid() {
    CVerify.String.equals(
        INVALID_RESOURCE.getResourceFullName(),
        INVALID_RESOURCE_NAME,
        "getResourceFullName() works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetString() {
    CVerify.String.equals(
        VALID_RESOURCE.getString().replaceAll(System.lineSeparator(), "|"),
        "1-Some text;|2-Some text;|3-Some text;",
        "getLines works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CResourceNotFoundException.class)
  public void testGetString_Invalid() {
    INVALID_RESOURCE.getString();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSaveToFolder() {
    CFile file = VALID_RESOURCE.saveToFolder(CFile.fromTmp(RandomStringUtils.randomAlphabetic(10)));
    CVerify.Object.isNotNull(file, "saveToFolder works fine");
    CVerify.String.equals(
        file.readString().replaceAll(System.lineSeparator(), "|"),
        "1-Some text;|2-Some text;|3-Some text;",
        "saveToFolder works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CResourceNotFoundException.class)
  public void testSaveToFolder_Invalid() {
    INVALID_RESOURCE.saveToFolder(CFile.fromTmp(RandomStringUtils.randomAlphabetic(10)));
  }
}
