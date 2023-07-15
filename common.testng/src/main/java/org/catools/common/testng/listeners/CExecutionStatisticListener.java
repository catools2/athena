package org.catools.common.testng.listeners;

import org.catools.common.collections.CHashMap;
import org.catools.common.testng.model.CExecutionStatus;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

public class CExecutionStatisticListener implements ISuiteListener, IResultListener {
  private static final CHashMap<String, CExecutionStatus> methodSignatures = new CHashMap<>();
  private static int total;
  private static int passed;
  private static int failed;
  private static int skipped;
  private static int running;
  private static int waiting;

  public static int getTotal() {
    return total;
  }

  public static int getTotalPassed() {
    return passed;
  }

  public static int getTotalFailed() {
    return failed;
  }

  public static int getTotalSkipped() {
    return skipped;
  }

  public static int getTotalRunning() {
    return running;
  }

  public static int getTotalWaiting() {
    return waiting;
  }

  @Override
  public void onStart(ISuite suite) {
    methodSignatures.clear();
    suite.getAllMethods().forEach(m -> updateTestResult(m, CExecutionStatus.CREATED));
  }

  @Override
  public void onTestStart(ITestResult result) {
    updateTestResult(result.getMethod(), CExecutionStatus.WIP);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    updateTestResult(result.getMethod(), CExecutionStatus.SUCCESS);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    updateTestResult(result.getMethod(), CExecutionStatus.FAILURE);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    updateTestResult(result.getMethod(), CExecutionStatus.SKIP);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    updateTestResult(result.getMethod(), CExecutionStatus.SUCCESS);
  }

  private static void updateVariables() {
    total = methodSignatures.size();
    passed = methodSignatures.getAllKeys(CExecutionStatus::isPassed).size();
    failed = methodSignatures.getAllKeys(CExecutionStatus::isFailed).size();
    skipped = methodSignatures.getAllKeys(CExecutionStatus::isSkipped).size();
    running = methodSignatures.getAllKeys(CExecutionStatus::isRunning).size();
    waiting = total - passed - failed - skipped - running;
  }

  private static synchronized void updateTestResult(ITestNGMethod method, CExecutionStatus status) {
    methodSignatures.put(method.getTestClass().getName() + method.getMethodName(), status);
    updateVariables();
  }

  public static synchronized void removeTestMethod(ITestNGMethod method) {
    methodSignatures.remove(method.getTestClass().getName() + method.getMethodName());
    updateVariables();
  }
}
