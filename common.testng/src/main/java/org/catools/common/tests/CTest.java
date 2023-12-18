package org.catools.common.tests;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.catools.common.logger.CLoggerConfigs;
import org.catools.common.testng.model.CExecutionStatus;
import org.catools.common.testng.model.CTestResult;
import org.catools.common.testng.utils.CTestClassUtil;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Slf4j
public class CTest {

  private static boolean FIRST_RUN_PREPARATION_CALLED = false;

  static {
    AnsiConsole.systemInstall();
    ThreadContext.put("LogFolder", CLoggerConfigs.getLogFolderPath());
  }

  public final Logger logger = LoggerFactory.getLogger(CTest.class);
  private final CTestStateData dataState = new CTestStateData();
  private final CTestMetadata metadata = new CTestMetadata();
  private final String name = CTestClassUtil.getTestName(getClass());
  private CExecutionStatus testResult = CExecutionStatus.CREATED;

  public String getName() {
    return name;
  }

  @BeforeSuite
  public void beforeSuite(ITestContext context) {
    log.debug("BeforeSuite Started for suite {} ", getSuiteName(context));
    if (!FIRST_RUN_PREPARATION_CALLED) {
      onFirstRun();
      FIRST_RUN_PREPARATION_CALLED = true;
    }
  }

  @BeforeTest
  public void beforeTest(ITestContext context) {
    log.debug("BeforeTest Started for issue {} ", getContextName(context));
  }

  @BeforeClass
  public void beforeClass() {
    ThreadContext.put("LogFolder", CLoggerConfigs.getLogFolderPath());
    ThreadContext.put("TestName", name);
    log.debug("BeforeClass Started for class {} ", name);
  }

  @BeforeMethod
  public void beforeMethod(ITestResult result) {
    ThreadContext.put("LogFolder", CLoggerConfigs.getLogFolderPath());
    ThreadContext.put("TestName", name);
    log.debug("BeforeMethod Started for class {}, method {}", name, getMethodName(result));
  }

  @AfterMethod
  public void afterMethod(ITestResult result) {
    if (result == null) return;

    log.debug("AfterMethod Started for class {}, method {} ", name, getMethodName(result));
    this.testResult = new CTestResult(result).getStatus();

    if (result.getThrowable() != null) {
      log.error("Test Failed With Exception:\n" + result.getThrowable());
    }
  }

  @AfterClass
  public void afterClass() {
    log.debug("AfterClass Started for class {}", name);
    switch (testResult) {
      case SUCCESS, SUCCESS_PERCENTAGE_FAILURE -> onSuccess();
      case SKIP -> onSkip();
      case FAILURE -> onFailure();
      case DEFERRED -> onDeferred();
      case BLOCKED -> onBlocked();
      case AWAITING -> onAwaiting();
    }
  }

  @AfterTest
  public void afterTest(ITestContext context) {
    ThreadContext.remove("TestName");
    log.debug("AfterTest Started for issue {} ", getContextName(context));
  }

  @AfterSuite
  public void afterSuite(ITestContext context) {
    log.debug("AfterSuite Started for {} suite.", getSuiteName(context));
  }

  public CTestStateData getDataState() {
    return dataState;
  }

  public void updateDataState(String key, Object value) {
    getDataState().updateDataState(key, value);
  }

  public <T> T getDataState(String key) {
    return getDataState().getDataState(key);
  }

  public CTestMetadata getMetadata() {
    return metadata;
  }

  public void addMetadata(String key, String value) {
    getMetadata().addIfNotExists(key, value);
  }

  protected void onAwaiting() {
  }

  protected void onBlocked() {
  }

  protected void onDeferred() {
  }

  protected void onFailure() {
  }

  protected void onSkip() {
  }

  protected void onSuccess() {
  }

  protected void onFirstRun() {
  }

  private String getMethodName(ITestResult result) {
    if (result == null || result.getMethod() == null) {
      return "";
    }
    return result.getMethod().getMethodName();
  }

  private String getSuiteName(ITestContext context) {
    if (context == null || context.getSuite() == null) {
      return "";
    }
    return context.getSuite().getName();
  }

  private String getContextName(ITestContext context) {
    if (context == null || context.getCurrentXmlTest() == null) {
      return "";
    }
    return context.getCurrentXmlTest().getName();
  }

  public Logger getLogger() {
    return log;
  }
}
