package org.catools.pipeline.listeners;

import org.catools.common.date.CDate;
import org.catools.common.testng.listeners.CITestNGListener;
import org.catools.common.testng.model.CTestResult;
import org.catools.pipeline.dao.CPipelineDao;
import org.catools.pipeline.helpers.CPipelineHelper;
import org.catools.pipeline.model.CPipeline;
import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class CPipelineListener implements CITestNGListener {
  private static CPipeline pipeline;
  private CDate testStartTime;
  private CDate beforeClassStartTime;
  private CDate beforeMethodStartTime;
  private CDate beforeClassEndTime;
  private CDate beforeMethodEndTime;

  @Override
  public int priority() {
    return 0;
  }

  @Override
  public void onExecutionStart() {
    buildPipeline();
  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    setStartTime(testResult.getMethod());
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    setEndTime(testResult);
  }

  @Override
  public synchronized void onTestSuccess(ITestResult result) {
    addResult(result);
  }

  @Override
  public synchronized void onTestFailure(ITestResult result) {
    addResult(result);
  }

  @Override
  public synchronized void onTestSkipped(ITestResult result) {
    addResult(result);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    addResult(result);
  }

  @Override
  public void onExecutionFinish() {
    CPipelineDao.updateEndDate(pipeline.getId(), CDate.now().getTimeStamp());
  }

  private static synchronized void buildPipeline() {
    if (pipeline == null) {
      pipeline = CPipelineHelper.getPipeline();
    }
  }

  private void setStartTime(ITestNGMethod method) {
    if (method.isBeforeClassConfiguration())
      beforeClassStartTime = CDate.now();
    else if (method.isBeforeMethodConfiguration())
      beforeMethodStartTime = CDate.now();
    else if (method.isTest())
      testStartTime = CDate.now();
  }

  private void setEndTime(ITestResult testResult) {
    ITestNGMethod method = testResult.getMethod();
    if (method.isBeforeClassConfiguration())
      beforeClassEndTime = CDate.now();
    else if (method.isBeforeMethodConfiguration())
      beforeMethodEndTime = CDate.now();
  }

  private void addResult(ITestResult testResult) {
    CTestResult result = new CTestResult(testResult, testStartTime, CDate.now());
    result.setBeforeClassStartTime(beforeClassStartTime);
    result.setBeforeClassEndTime(beforeClassEndTime);

    result.setBeforeMethodStartTime(beforeMethodStartTime);
    result.setBeforeMethodEndTime(beforeMethodEndTime);

    CPipelineHelper.addExecution(pipeline, result);
  }
}
