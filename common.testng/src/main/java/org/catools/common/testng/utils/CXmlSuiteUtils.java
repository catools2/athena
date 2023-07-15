package org.catools.common.testng.utils;

import lombok.experimental.UtilityClass;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.testng.CTestNGConfigs;
import org.catools.common.utils.CObjectUtil;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@UtilityClass
public class CXmlSuiteUtils {
  public static XmlTest buildTestForIssueKeys(CSet<String> issueIds, String testName, boolean filterTestsWhichWillSkipInRun) {
    CSet<String> classNameForIssueKeys = CTestClassUtil.getClassNameForIssueKeys(issueIds, filterTestsWhichWillSkipInRun);
    return buildTestForClasses(classNameForIssueKeys, testName);
  }

  public static XmlSuite buildTestSuiteForClasses(
      CHashMap<String, CSet<String>> testClasses,
      String suiteName,
      @Nullable Consumer<XmlSuite> xmlSuiteAdjuster) {
    CList<XmlTest> tests =
        testClasses
            .asSet()
            .mapToList(
                e -> buildTestForClasses(e.getValue(), e.getKey()));
    return buildTestSuiteForClasses(tests, suiteName, xmlSuiteAdjuster);
  }

  public static XmlTest buildTestForClasses(CSet<String> testClasses, String testName) {
    XmlTest xmlTest = new XmlTest();
    xmlTest.setJUnit(false);
    xmlTest.setName(testName);

    // Sort list
    CList<String> listForSorting = testClasses.toList();
    listForSorting.sort(String::compareTo);
    xmlTest.setXmlClasses(listForSorting.mapToList(XmlClass::new));
    return xmlTest;
  }

  public static XmlSuite buildTestSuiteForClasses(
      CList<XmlTest> tests, String suiteName, @Nullable Consumer<XmlSuite> xmlSuiteAdjuster) {
    XmlSuite xmlSuite = new XmlSuite();
    xmlSuite.setName(suiteName);
    xmlSuite.setJUnit(false);
    xmlSuite.setAllowReturnValues(true);
    if (!XmlSuite.ParallelMode.NONE.equals(CTestNGConfigs.getSuiteLevelParallel())) {
      xmlSuite.setParallel(CTestNGConfigs.getSuiteLevelParallel());
    }

    if (CTestNGConfigs.getSuiteLevelThreadCount() > 0) {
      xmlSuite.setThreadCount(CTestNGConfigs.getSuiteLevelThreadCount());
    }

    tests.forEach(
        test -> {
          if (!XmlSuite.ParallelMode.NONE.equals(CTestNGConfigs.getTestLevelParallel())) {
            test.setParallel(CTestNGConfigs.getTestLevelParallel());
          }

          if (CTestNGConfigs.getTestLevelThreadCount() > 0) {
            test.setThreadCount(CTestNGConfigs.getTestLevelThreadCount());
          }

          test.setSuite(xmlSuite);
          xmlSuite.addTest(test);
        });

    if (xmlSuiteAdjuster != null) {
      xmlSuiteAdjuster.accept(xmlSuite);
    }
    return xmlSuite;
  }

  public static XmlSuite copy(XmlSuite xmlSuite, String suiteNamePostfix) {
    XmlSuite suite = CObjectUtil.clone(xmlSuite);
    suite.setName(suite.getName() + suiteNamePostfix);
    return suite;
  }
}
