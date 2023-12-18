package org.catools.common.testng;

import lombok.experimental.UtilityClass;
import org.catools.common.collections.CList;
import org.catools.common.io.CFile;
import org.catools.common.security.CSensitiveDataMaskingManager;
import org.catools.common.testng.utils.CTestReportUtil;
import org.catools.common.utils.CStringUtil;
import org.testng.*;
import org.testng.internal.Utils;
import org.testng.reporters.XMLReporter;
import org.testng.reporters.XMLReporterConfig;
import org.testng.reporters.XMLStringBuffer;
import org.testng.reporters.XMLSuiteResultWriter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@UtilityClass
public class CTestNGResultGenerator {
  private static final String XML_EXTENSION = ".xml";
  private static final String FILE_NAME = CTestNGConfigs.getTestNgResultName();
  private static final XMLReporterConfig config = new XMLReporterConfig();
  private static XMLStringBuffer rootBuffer;

  public static synchronized void generateReport(CList<ISuite> suites, String outputDirectory) {
    config.setOutputDirectory(outputDirectory);
    CTestReportUtil.removeDuplicateResults(suites);

    // Calculate passed/failed/skipped
    int passed = 0;
    int failed = 0;
    int skipped = 0;
    for (ISuite s : suites) {
      for (ISuiteResult sr : s.getResults().values()) {
        ITestContext testContext = sr.getTestContext();
        passed += testContext.getPassedTests().size();
        failed += testContext.getFailedTests().size();
        skipped += testContext.getSkippedTests().size();
      }
    }

    rootBuffer = new XMLStringBuffer();
    Properties p = new Properties();
    p.put("passed", passed);
    p.put("failed", failed);
    p.put("skipped", skipped);
    p.put("total", passed + failed + skipped);
    rootBuffer.push(XMLReporterConfig.TAG_TESTNG_RESULTS, p);
    writeReporterOutput(rootBuffer);
    for (int i = 0; i < suites.size(); i++) {
      writeSuite(suites.get(i));
    }
    rootBuffer.pop();
    new File(config.getOutputDirectory()).mkdirs();
    Utils.writeUtf8File(
        config.getOutputDirectory(),
        CStringUtil.removeEnd(FILE_NAME, XML_EXTENSION)
            + (CTestNGConfigs.isLastSuiteRun() ? "" : CTestNGConfigs.getSuiteRunCounter())
            + XML_EXTENSION,
        rootBuffer,
        null);
  }

  private static void writeReporterOutput(XMLStringBuffer xmlBuffer) {
    xmlBuffer.push(XMLReporterConfig.TAG_REPORTER_OUTPUT);
    List<String> output = Reporter.getOutput();
    for (String line : output) {
      if (line != null) {
        xmlBuffer.push(XMLReporterConfig.TAG_LINE);
        xmlBuffer.addCDATA(CSensitiveDataMaskingManager.mask(line));
        xmlBuffer.pop();
      }
    }
    xmlBuffer.pop();
  }

  private static void writeSuite(ISuite suite) {
    switch (config.getFileFragmentationLevel()) {
      case XMLReporterConfig.FF_LEVEL_NONE -> writeSuiteToBuffer(rootBuffer, suite);
      case XMLReporterConfig.FF_LEVEL_SUITE, XMLReporterConfig.FF_LEVEL_SUITE_RESULT -> {
        File suiteFile = referenceSuite(rootBuffer, suite);
        writeSuiteToFile(suiteFile, suite);
      }
    }
  }

  private static void writeSuiteToFile(File suiteFile, ISuite suite) {
    XMLStringBuffer xmlBuffer = new XMLStringBuffer();
    writeSuiteToBuffer(xmlBuffer, suite);
    CFile parentDir = new CFile(suiteFile.getParentFile());
    if (parentDir.exists() || suiteFile.getParentFile().mkdirs()) {
      Utils.writeFile(parentDir.getCanonicalPath(), FILE_NAME, xmlBuffer.toXML());
    }
  }

  private static File referenceSuite(XMLStringBuffer xmlBuffer, ISuite suite) {
    String relativePath = suite.getName() + File.separatorChar + FILE_NAME;
    File suiteFile = new File(config.getOutputDirectory(), relativePath);
    Properties attrs = new Properties();
    attrs.setProperty(XMLReporterConfig.ATTR_URL, relativePath);
    xmlBuffer.addEmptyElement(XMLReporterConfig.TAG_SUITE, attrs);
    return suiteFile;
  }

  private static void writeSuiteToBuffer(XMLStringBuffer xmlBuffer, ISuite suite) {
    xmlBuffer.push(XMLReporterConfig.TAG_SUITE, getSuiteAttributes(suite));
    writeSuiteGroups(xmlBuffer, suite);

    Map<String, ISuiteResult> results = suite.getResults();
    XMLSuiteResultWriter suiteResultWriter = new XMLSuiteResultWriter(config, new XMLReporter());
    for (Map.Entry<String, ISuiteResult> result : results.entrySet()) {
      suiteResultWriter.writeSuiteResult(xmlBuffer, result.getValue());
    }

    xmlBuffer.pop();
  }

  private static void writeSuiteGroups(XMLStringBuffer xmlBuffer, ISuite suite) {
    xmlBuffer.push(XMLReporterConfig.TAG_GROUPS);
    Map<String, Collection<ITestNGMethod>> methodsByGroups = suite.getMethodsByGroups();
    for (Map.Entry<String, Collection<ITestNGMethod>> entry : methodsByGroups.entrySet()) {
      Properties groupAttrs = new Properties();
      groupAttrs.setProperty(XMLReporterConfig.ATTR_NAME, entry.getKey());
      xmlBuffer.push(XMLReporterConfig.TAG_GROUP, groupAttrs);
      Set<ITestNGMethod> groupMethods = getUniqueMethodSet(entry.getValue());
      for (ITestNGMethod groupMethod : groupMethods) {
        Properties methodAttrs = new Properties();
        methodAttrs.setProperty(XMLReporterConfig.ATTR_NAME, groupMethod.getMethodName());
        methodAttrs.setProperty(XMLReporterConfig.ATTR_METHOD_SIG, groupMethod.toString());
        methodAttrs.setProperty(XMLReporterConfig.ATTR_CLASS, groupMethod.getRealClass().getName());
        xmlBuffer.addEmptyElement(XMLReporterConfig.TAG_METHOD, methodAttrs);
      }
      xmlBuffer.pop();
    }
    xmlBuffer.pop();
  }

  private static Properties getSuiteAttributes(ISuite suite) {
    Properties props = new Properties();
    props.setProperty(XMLReporterConfig.ATTR_NAME, suite.getName());

    // Calculate the duration
    Map<String, ISuiteResult> results = suite.getResults();
    Date minStartDate = new Date();
    Date maxEndDate = null;
    for (Map.Entry<String, ISuiteResult> result : results.entrySet()) {
      ITestContext testContext = result.getValue().getTestContext();
      Date startDate = testContext.getStartDate();
      Date endDate = testContext.getEndDate();
      if (minStartDate.after(startDate)) {
        minStartDate = startDate;
      }
      if (maxEndDate == null || maxEndDate.before(endDate)) {
        maxEndDate = endDate != null ? endDate : startDate;
      }
    }

    // The suite could be completely empty
    if (maxEndDate == null) {
      maxEndDate = minStartDate;
    }
    addDurationAttributes(props, minStartDate, maxEndDate);
    return props;
  }

  private static void addDurationAttributes(Properties attributes, Date minStartDate, Date maxEndDate) {
    Objects.requireNonNull(minStartDate);
    Objects.requireNonNull(maxEndDate);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    TimeZone utc = TimeZone.getTimeZone("UTC");
    format.setTimeZone(utc);
    String startTime = format.format(minStartDate);
    String endTime = format.format(maxEndDate);
    long duration = maxEndDate.getTime() - minStartDate.getTime();

    attributes.setProperty(XMLReporterConfig.ATTR_STARTED_AT, startTime);
    attributes.setProperty(XMLReporterConfig.ATTR_FINISHED_AT, endTime);
    attributes.setProperty(XMLReporterConfig.ATTR_DURATION_MS, Long.toString(duration));
  }

  private static Set<ITestNGMethod> getUniqueMethodSet(Collection<ITestNGMethod> methods) {
    return new LinkedHashSet<>(methods);
  }
}
