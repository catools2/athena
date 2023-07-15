package org.catools.extentreport;

import com.aventstack.extentreports.ExtentTest;
import org.catools.common.collections.CSet;
import org.catools.common.config.CTestManagementConfigs;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.testng.listeners.CITestNGListener;
import org.catools.common.testng.model.CTestResult;
import org.catools.common.utils.CFileUtil;
import org.testng.ISuite;
import org.testng.ITestResult;

import static org.catools.common.testng.utils.CTestClassUtil.noRetryLeft;

public class CExtentReportListener implements CITestNGListener {
  private static final ThreadLocal<ExtentTest> overallTest = new ThreadLocal<>();
  private static final ThreadLocal<ExtentTest> suiteTest = new ThreadLocal<>();

  // We do not want to start tests that already started
  private static final CSet<String> startedTests = new CSet<>();

  private static CExtentReport overAllExtentReport;
  private static CExtentReport suiteExtentReport;

  private final String projectName = CTestManagementConfigs.getProjectName();
  private final String versionName = CTestManagementConfigs.getVersionName();

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(overallTest::remove));
    Runtime.getRuntime().addShutdownHook(new Thread(suiteTest::remove));
  }

  @Override
  public int priority() {
    return 1;
  }

  @Override
  public void onExecutionStart() {
    if (CExtentReportConfigs.isEnable()) {
      overAllExtentReport =
          new CExtentReport(
              CFileUtil.getCanonicalPath(CPathConfigs.getOutputRoot()),
              "Overall Extent Report",
              "OverallExtentReport");
    }
  }

  @Override
  public void onStart(ISuite suite) {
    if (CExtentReportConfigs.isEnable()) {
      suiteExtentReport = new CExtentReport(CPathConfigs.getOutputPath());
      startedTests.clear();
    }
  }

  @Override
  public synchronized void onTestStart(ITestResult result) {
    String key =
        String.format(
            "%s->%s->%s",
            projectName,
            versionName,
            new CTestResult(projectName, versionName, result).getTestFullName());
    if (suiteExtentReport != null && !startedTests.contains(key)) {
      startedTests.add(key);
      suiteTest.set(suiteExtentReport.createTest(projectName, versionName, result));
      overallTest.set(overAllExtentReport.createTest(projectName, versionName, result));
    }
  }

  @Override
  public synchronized void onTestSuccess(ITestResult result) {
    if (suiteExtentReport != null && suiteTest.get() != null) {
      suiteTest.get().pass("Test passed");
      overallTest.get().pass("Test passed");
    }
  }

  @Override
  public synchronized void onTestFailure(ITestResult result) {
    if (suiteExtentReport != null && suiteTest.get() != null) {
      if (noRetryLeft(result, false)) {
        suiteTest.get().fail(result.getThrowable());
      }

      if (noRetryLeft(result, true)) {
        overallTest.get().fail(result.getThrowable());
      }
    }
  }

  @Override
  public synchronized void onTestSkipped(ITestResult result) {
    if (suiteExtentReport != null && suiteTest.get() != null) {
      if (noRetryLeft(result, false)) {
        suiteTest.get().skip(result.getThrowable());
      }

      if (noRetryLeft(result, true)) {
        overallTest.get().skip(result.getThrowable());
      }
    }
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    if (suiteExtentReport != null && suiteTest.get() != null) {
      if (noRetryLeft(result, false)) {
        suiteTest.get().pass("Passed with failed percentage.");
      }

      if (noRetryLeft(result, true)) {
        overallTest.get().pass("Passed with failed percentage.");
      }
    }
  }

  @Override
  public void onFinish(ISuite suite) {
    if (suiteExtentReport != null) {
      suiteExtentReport.flush();
    }
  }

  @Override
  public void onExecutionFinish() {
    if (overAllExtentReport != null) {
      overAllExtentReport.flush();
    }
  }
}
