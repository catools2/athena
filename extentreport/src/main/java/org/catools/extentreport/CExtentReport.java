package org.catools.extentreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.catools.common.collections.CList;
import org.catools.common.config.CTestManagementConfigs;
import org.catools.common.exception.CFileOperationException;
import org.catools.common.testng.model.CTestResult;
import org.catools.common.utils.CConfigUtil;
import org.catools.common.utils.CStringUtil;
import org.testng.ITestResult;

import java.io.File;

public class CExtentReport extends ExtentReports {
  private ExtentSparkReporter extentHtmlReporter;

  public CExtentReport(String reportDir) {
    this(
        reportDir,
        CExtentReportConfigs.getExtentReportName() + " " + CConfigUtil.getRunName(),
        CExtentReportConfigs.getExtentReportFileName() + CConfigUtil.getRunName());
  }

  public CExtentReport(String reportDir, String reportName, String reportFileName) {
    super();
    extentHtmlReporter = buildExtentHtmlReporter(reportDir, reportName, reportFileName);
    attachReporter(extentHtmlReporter);
  }

  private static String getTestMethodName(CTestResult exec) {
    CList<String> list = new CList<>();
    if (!exec.getTestIds().isEmpty()) {
      list.add("Test(s): ");
      list.add(
          exec.getTestIds()
              .mapToSet(
                  id ->
                      CStringUtil.format(
                          "<a style=\"color:Blue\" href=\"%s\">%s</a>",
                          CTestManagementConfigs.getUrlToTest(id), id))
              .join(", "));
    }

    if (!exec.getOpenDefectIds().isEmpty()) {
      if (list.isNotEmpty()) {
        list.add(" | ");
      }
      list.add(
          exec.getOpenDefectIds()
              .mapToSet(
                  id ->
                      CStringUtil.format(
                          "<a class=\"fa fa-bug\" style=\"color:Red\" href=\"%s\">%s</a>",
                          CTestManagementConfigs.getUrlToDefect(id), id))
              .join(", "));
    }

    if (CStringUtil.isNotBlank(exec.getAwaiting())) {
      if (list.isNotEmpty()) {
        list.add(" | ");
      }
      list.add(
          "<i class=\"fa fa-hourglass-half\" style=\"color:Chocolate\">"
              + exec.getAwaiting()
              + "</i>");
    }

    return list.isEmpty() ? exec.getMethodName() : list.join("");
  }

  private static String getTestMethodDescription(CTestResult exec) {
    return CStringUtil.defaultIfBlank(exec.getDescription(), exec.getTestFullName());
  }

  public synchronized ExtentTest createTest(ITestResult result) {
    CTestResult cTestResult = new CTestResult(result);
    return super.createTest(getTestMethodName(cTestResult), getTestMethodDescription(cTestResult));
  }

  protected ExtentSparkReporter buildExtentHtmlReporter(
      String reportDir, String reportName, String reportFileName) {
    File file = new File(reportDir, reportFileName.replaceAll(".html", "") + ".html");
    if (file.exists()) {
      if (!file.delete()) {
        throw new CFileOperationException(file, "filed to delete file!");
      }
    }

    ExtentSparkReporter htmlReporter = new ExtentSparkReporter(file.getPath());
    htmlReporter.config().setTheme(Theme.STANDARD);
    htmlReporter.config().setDocumentTitle(reportName);
    htmlReporter.config().setEncoding("utf-8");
    htmlReporter.config().setReportName(reportName);
    return htmlReporter;
  }
}
