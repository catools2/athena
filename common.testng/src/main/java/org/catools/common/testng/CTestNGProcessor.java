package org.catools.common.testng;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.common.testng.utils.CTestClassUtil;
import org.catools.common.testng.utils.CXmlSuiteUtils;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class CTestNGProcessor {

  public static CFile buildTestSuite(CHashMap<String, CSet<String>> testClassesMap,
                                     String suiteName,
                                     String filename,
                                     @Nullable
                                     Consumer<XmlSuite> xmlSuiteAdjuster) {
    CFile file = new CFile(filename);
    file.getParentFile().mkdirs();
    file.write(CXmlSuiteUtils.buildTestSuiteForClasses(testClassesMap, suiteName, xmlSuiteAdjuster).toXml());
    return file;
  }

  public static CFile buildTestSuiteForClasses(CSet<String> testClasses,
                                               String filename,
                                               @Nullable
                                               Function<CSet<String>, Map<String, CSet<String>>> testClassGroupMapper,
                                               Consumer<XmlSuite> xmlSuiteAdjuster) {
    CHashMap<String, CSet<String>> map = new CHashMap<>();
    if (testClassGroupMapper == null) {
      map.put("Test", testClasses);
    } else {
      map.putAll(testClassGroupMapper.apply(testClasses));
    }
    return buildTestSuite(map, "Suite", filename, xmlSuiteAdjuster);
  }

  public static void processLocalXmlSuite(String suiteName) {
    CVerify.Bool.isTrue(suiteName.toLowerCase().trim().endsWith(".xml"), "TestNG suite file name should end with xml.");
    CFile localXmlFile = new CFile(suiteName);
    CVerify.Bool.isTrue(localXmlFile.exists(), "Xml file exists. file: " + localXmlFile.getCanonicalPath());
    log.info("Running local xml file:" + localXmlFile.getCanonicalPath());
    processFile(localXmlFile);
  }

  public static void processResourceXmlSuite(String suiteName) {
    CVerify.Bool.isTrue(suiteName.toLowerCase().trim().endsWith(".xml"), "TestNG suite file name should end with xml.");
    String resourceContent = new CResource(suiteName, CTestNGConfigs.getBaseClassLoader()).getString();
    CVerify.String.isNotBlank(resourceContent, "Xml resource file exists and it is not empty. Resource Name: " + suiteName);
    CFile localXmlFile = CFile.fromTmp(suiteName);
    localXmlFile.write(resourceContent);
    CVerify.Bool.isTrue(localXmlFile.exists(), "Xml file copied to resource folder. file: " + localXmlFile);
    processFile(localXmlFile);
  }

  public static void processTestByTestIds(CSet<String> issueIds,
                                          String filename,
                                          Function<CSet<String>, Map<String, CSet<String>>> testClassGroupMapper,
                                          Consumer<XmlSuite> xmlSuiteAdjuster,
                                          boolean filterTestsWhichWillSkipInRun) {
    processTestClasses(CTestClassUtil.getClassNameForIssueKeys(issueIds, filterTestsWhichWillSkipInRun),
        filename,
        testClassGroupMapper,
        xmlSuiteAdjuster);
  }

  public static void processTestByClassNames(CSet<String> classNames,
                                             String filename,
                                             Function<CSet<String>, Map<String, CSet<String>>> testClassGroupMapper,
                                             Consumer<XmlSuite> xmlSuiteAdjuster) {
    processTestClasses(classNames, filename, testClassGroupMapper, xmlSuiteAdjuster);
  }

  public static void processTestClasses(CSet<String> testClasses,
                                        String filename,
                                        @Nullable
                                        Function<CSet<String>, Map<String, CSet<String>>> testClassGroupMapper,
                                        @Nullable
                                        Consumer<XmlSuite> xmlSuiteAdjuster) {
    processFile(buildTestSuiteForClasses(testClasses, filename, testClassGroupMapper, xmlSuiteAdjuster));
  }

  public static void processXmlSuites(Collection<XmlSuite> xmlSuites) {
    try {
      new CList<>(xmlSuites).forEach(x -> log.info("Processing Xml Suites \n" + x.toXml()));
      TestNG testNG = new TestNG();
      testNG.setXmlSuites((List<XmlSuite>) (xmlSuites));

      for (ITestNGListener listener : CTestNGConfigs.getListeners()) {
        testNG.addListener(listener);
      }

      CFile.fromOutput(CDate.now().toTimeStampForFileName() + ".xml").write(xmlSuites.toString());
      testNG.run();
      System.exit(testNG.getStatus());
    } catch (Throwable t) {
      log.error("Could Not Processing Xml Suites", t);
      System.exit(1);
    }
  }

  private static void processFile(CFile xmlFile) {
    log.info("Processing " + xmlFile);
    try {
      processXmlSuites(new Parser(xmlFile.getCanonicalPath()).parse());
    } catch (Throwable t) {
      System.exit(1);
      throw new RuntimeException(t);
    }
  }
}
