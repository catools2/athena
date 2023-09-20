package org.catools.reportportal.service;

import com.epam.reportportal.annotations.UniqueID;
import com.epam.reportportal.testng.TestMethodType;
import com.epam.reportportal.testng.TestNGService;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import org.catools.common.config.CTestManagementConfigs;
import org.catools.common.testng.CTestNGConfigs;
import org.catools.common.testng.model.CTestResult;
import org.catools.common.utils.CStringUtil;
import org.catools.reportportal.configs.CRPConfigs;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Date;

/**
 * 99% copied from com.epam.reportportal.testng.TestNGService We need to have it separated with
 * large Duplicated code so we could build extensions we need
 */
public class CReportPortalService extends TestNGService {

  /**
   * Extension point to customize beforeXXX creation event/request
   *
   * @param testResult TestNG's testResult context
   * @param type       Type of method
   * @return Request to ReportPortal
   */
  protected StartTestItemRQ buildStartConfigurationRq(ITestResult testResult, TestMethodType type) {
    StartTestItemRQ rq = new StartTestItemRQ();
    rq.setName(getMethodName(testResult));

    rq.setDescription(getMethodDescription(testResult));
    rq.setStartTime(new Date(testResult.getStartMillis()));
    rq.setType(type == null ? null : type.toString());
    return rq;
  }

  /**
   * Extension point to customize test step creation event/request
   *
   * @param testResult TestNG's testResult context
   * @return Request to ReportPortal
   */
  @Override
  protected StartTestItemRQ buildStartStepRq(ITestResult testResult) {
    StartTestItemRQ rq = new StartTestItemRQ();
    String testStepName;
    if (testResult.getTestName() != null) {
      testStepName = testResult.getTestName();
    } else {
      testStepName = getMethodName(testResult);
    }
    rq.setName(testStepName);

    rq.setDescription(createStepDescription(testResult));
    rq.setParameters(createStepParameters(testResult));
    rq.setUniqueId(getUniqueID(testResult));
    rq.setStartTime(new Date(testResult.getStartMillis()));
    rq.setAttributes(CRPConfigs.getAttributes());
    rq.setType(TestMethodType.getStepType(testResult.getMethod()).toString());

    rq.setRetry(retry(testResult));
    return rq;
  }

  /**
   * Extension point to customize test step description
   *
   * @param testResult TestNG's testResult context
   * @return Test/Step Description being sent to ReportPortal
   */
  protected String createStepDescription(ITestResult testResult) {
    StringBuilder stringBuffer = new StringBuilder();
    CTestResult result = new CTestResult(testResult);
    if (result.getTestIds().isEmpty() && getMethodDescription(testResult) != null) {
      stringBuffer.append(getMethodDescription(testResult) + "\n");
    }
    stringBuffer.append(getTestInfoForReport(result));
    return stringBuffer.toString();
  }

  public static String getTestInfoForReport(CTestResult result) {
    StringBuilder stringBuffer = new StringBuilder();

    stringBuffer.append("Package: " + result.getPackageName() + "\n");

    if (result.getTestIds().isNotEmpty()) {
      if (CStringUtil.isBlank(CTestManagementConfigs.getUrlToTest())) {
        stringBuffer.append("Tests: " + result.getTestIds().join(", ") + "\n");
      } else {
        stringBuffer.append("Tests: " + result.getTestIds().mapToSet(i -> String.format("[%s](%s)", i, CTestManagementConfigs.getUrlToTest(i))).join(", ") + "\n");
      }
    }

    if (result.getDefectIds().isNotEmpty()) {
      if (CStringUtil.isBlank(CTestManagementConfigs.getUrlToDefect())) {
        stringBuffer.append("Defects: " + result.getDefectIds().join(", ") + "\n");
      } else {
        stringBuffer.append("Defects: " + result.getDefectIds().mapToSet(i -> String.format("[%s](%s)", i, CTestManagementConfigs.getUrlToDefect(i))).join(", ") + "\n");
      }
    }

    if (result.getOpenDefectIds().isNotEmpty()) {
      if (CStringUtil.isBlank(CTestManagementConfigs.getUrlToDefect())) {
        stringBuffer.append("Open Defects: " + result.getOpenDefectIds().join(", ") + "\n");
      } else {
        stringBuffer.append("Open Defects: " + result.getOpenDefectIds().mapToSet(i -> String.format("[%s](%s)", i, CTestManagementConfigs.getUrlToDefect(i))).join(", ") + "\n");
      }
    }

    if (CStringUtil.isNoneBlank(result.getAwaiting())) {
      stringBuffer.append("Awaiting: " + result.getAwaiting() + "\n");
    }

    if (CStringUtil.isNoneBlank(result.getVersion())) {
      stringBuffer.append("Version: " + result.getVersion() + "\n");
    }

    if (result.getSeverityLevel() != null) {
      stringBuffer.append("Severity Level: " + result.getSeverityLevel() + "\n");
    }

    if (result.getRegressionDepth() != null) {
      stringBuffer.append("Regression Depth: " + result.getRegressionDepth() + "\n");
    }

    return stringBuffer.toString();
  }

  /**
   * Check is current method passed according the number of failed tests and configurations
   *
   * @param testContext TestNG's test content
   * @return TRUE if passed, FALSE otherwise
   */
  protected boolean isTestPassed(ITestContext testContext) {
    return testContext.getFailedTests().size() == 0
        && testContext.getFailedConfigurations().size() == 0
        && testContext.getSkippedConfigurations().size() == 0
        && testContext.getSkippedTests().size() == 0;
  }

  /**
   * Returns test item ID from annotation if it provided.
   *
   * @param testResult Where to find
   * @return test item ID or null
   */
  private String getUniqueID(ITestResult testResult) {
    UniqueID itemUniqueID = getAnnotation(UniqueID.class, testResult);
    if (itemUniqueID != null) {
      return itemUniqueID.value();
    }
    return Base64.getEncoder().encodeToString(testResult.getMethod().getQualifiedName().getBytes());
  }

  /**
   * Returns method annotation by specified annotation class from from TestNG Method or null if the
   * method does not contain such annotation.
   *
   * @param annotation Annotation class to find
   * @param testResult Where to find
   * @return {@link Annotation} or null if doesn't exists
   */
  private <T extends Annotation> T getAnnotation(Class<T> annotation, ITestResult testResult) {
    ITestNGMethod testNGMethod = testResult.getMethod();
    if (null != testNGMethod) {
      ConstructorOrMethod constructorOrMethod = testNGMethod.getConstructorOrMethod();
      if (null != constructorOrMethod) {
        Method method = constructorOrMethod.getMethod();
        if (null != method) {
          return method.getAnnotation(annotation);
        }
      }
    }
    return null;
  }

  private boolean retry(ITestResult result) {
    return (result.getMethod().getRetryAnalyzer(result) != null && CTestNGConfigs.getTestRetryCount() > 0) || CTestNGConfigs.getSuiteRetryCount() > 0;
  }

  private String getMethodDescription(ITestResult testResult) {
    return testResult.getMethod().getDescription();
  }

  // Originally this method returned getName() but I had to change it to getQualifiedName().
  // It seems that if you have a method with same name in other point then it was in RP
  // disrespecting the Package and Class Name
  private String getMethodName(ITestResult testResult) {
    String name = "";
    if (CRPConfigs.addPackageNameToMethodDescription()) {
      name += testResult.getTestClass().getRealClass().getPackageName();
    }

    if (CRPConfigs.addClassNameToMethodDescription()) {
      if (CStringUtil.isNotBlank(name)) {
        name += ".";
      }
      name += testResult.getTestClass().getRealClass().getSimpleName();
    }
    return String.format("%s::%s", name, testResult.getMethod().getMethodName());
  }
}
