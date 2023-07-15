package org.catools.common.tests;

import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;

public class CTestRetryAnalyzer extends RetryAnalyzerCount {
  public CTestRetryAnalyzer() {
    super();
    setCount(5);
  }

  @Override
  public boolean retryMethod(ITestResult iTestResult) {
    return true;
  }
}
