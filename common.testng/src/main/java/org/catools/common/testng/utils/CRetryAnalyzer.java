package org.catools.common.testng.utils;

import org.catools.common.testng.CTestNGConfigs;
import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;

public class CRetryAnalyzer extends RetryAnalyzerCount {
  public CRetryAnalyzer() {
    resetCount();
  }

  public void resetCount() {
    super.setCount(CTestNGConfigs.getTestRetryCount());
  }

  public boolean anyExecutionLeft() {
    return super.getCount() > -1;
  }

  public boolean isLastRetry() {
    return super.getCount() < 1;
  }

  @Override
  public boolean retryMethod(ITestResult iTestResult) {
    return true;
  }
}
