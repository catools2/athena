package org.catools.common.testng.utils;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CResource;
import org.catools.common.testng.annotatoins.RANY1;
import org.catools.common.tests.CTest;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

public class CXmlSuiteUtilsTest extends CTest {

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  @RANY1
  public void testBuildTestForIssueKeys() {
    CHashMap<String, CSet<String>> testClasses = new CHashMap<>();
    testClasses.put(
        "Auto Generated Test", new CSet<>("org.catools.common.testng.utils.CXmlSuiteUtilsTest"));
    XmlSuite auto_generated_test_suite =
        CXmlSuiteUtils.buildTestSuiteForClasses(testClasses, "Auto Generated Test Suite", null);
    String actual = auto_generated_test_suite.toXml();
    String expected =
        new CResource("testData/CTestNG/BuildTestSuiteForClassesTest.xml", CXmlSuiteUtilsTest.class)
            .getString();
    CVerify.String.equalsIgnoreWhiteSpaces(
        actual, expected, "TestNg auto generated xml files matched");
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  @RANY1
  public void testCopy() {
    CHashMap<String, CSet<String>> testClasses = new CHashMap<>();
    testClasses.put(
        "Auto Generated Test", new CSet<>("org.catools.common.testng.utils.CXmlSuiteUtilsTest"));
    XmlSuite baseSuite =
        CXmlSuiteUtils.buildTestSuiteForClasses(testClasses, "Auto Generated Test Suite", null);
    String expected = baseSuite.toXml();
    CXmlSuiteUtils.copy(baseSuite, " Retry " + 1);
    String actual = baseSuite.toXml();
    CVerify.String.equalsIgnoreWhiteSpaces(
        actual, expected, "TestNg auto generated xml files matched");
  }
}
