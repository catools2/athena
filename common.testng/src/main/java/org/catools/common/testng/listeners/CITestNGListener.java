package org.catools.common.testng.listeners;

import org.testng.*;
import org.testng.internal.IResultListener;

public interface CITestNGListener
    extends IExecutionListener,
    ISuiteListener,
    IClassListener,
    IResultListener,
    IConfigurationListener,
    IInvokedMethodListener {
  // 1- Listener Priority
  int priority();

  // 1- IExecutionListener
  @Override
  default void onExecutionStart() {
  }

  // 2- ISuiteListener
  @Override
  default void onStart(ISuite suite) {
  }

  // 3- IConfigurationListener2
  @Override
  default void beforeConfiguration(ITestResult result) {
  }

  // 4- IConfigurationListener
  @Override
  default void onConfigurationSuccess(ITestResult result) {
  }

  // 4- IConfigurationListener
  @Override
  default void onConfigurationFailure(ITestResult result) {
  }

  // 4- IConfigurationListener
  @Override
  default void onConfigurationSkip(ITestResult result) {
  }

  // 5- ITestListener
  @Override
  default void onStart(ITestContext context) {
  }

  // 6- IClassListener
  @Override
  default void onBeforeClass(ITestClass testClass) {
  }

  // 7- ITestListener
  @Override
  default void onTestStart(ITestResult result) {
  }

  // 8- IInvokedMethodListener
  @Override
  default void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
  }

  // 8- IInvokedMethodListener
  @Override
  default void beforeInvocation(
      IInvokedMethod method, ITestResult testResult, ITestContext context) {
  }

  // 9- IInvokedMethodListener
  @Override
  default void afterInvocation(IInvokedMethod method, ITestResult testResult) {
  }

  // 9- IInvokedMethodListener
  @Override
  default void afterInvocation(
      IInvokedMethod method, ITestResult testResult, ITestContext context) {
  }

  // 10- ITestListener
  @Override
  default void onTestSuccess(ITestResult result) {
  }

  // 11- ITestListener
  @Override
  default void onTestFailure(ITestResult result) {
  }

  // 12- ITestListener
  @Override
  default void onTestSkipped(ITestResult result) {
  }

  // 13- ITestListener
  @Override
  default void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  // 14- IClassListener
  @Override
  default void onAfterClass(ITestClass testClass) {
  }

  // 15- ITestListener
  @Override
  default void onFinish(ITestContext context) {
  }

  // 16- ISuiteListener
  @Override
  default void onFinish(ISuite suite) {
  }

  // 17- IExecutionListener
  @Override
  default void onExecutionFinish() {
  }
}
