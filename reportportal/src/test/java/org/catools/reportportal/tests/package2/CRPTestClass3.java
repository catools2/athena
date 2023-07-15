package org.catools.reportportal.tests.package2;

import org.catools.common.testng.CTestNGConfigs;
import org.catools.common.testng.utils.CRetryAnalyzer;
import org.catools.common.tests.CTest;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * We need this class to run them and verify manually report portal to ensure that graph display
 * right information
 */
public class CRPTestClass3 extends CTest {
  private static AtomicInteger instanceValue = new AtomicInteger();

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void testPassOnSecondTestRun() {
    assert instanceValue.getAndIncrement() > 1;
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void testPassOnSecondSuiteRun() {
    assert CTestNGConfigs.getSuiteRunCounter() == 2;
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void testPassOnThirdSuiteRun() {
    assert CTestNGConfigs.getSuiteRunCounter() == 3;
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void test1() {
    logger.info("test1");
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void test2() {
    logger.warn("test2");
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void test3() {
    logger.error("test3");
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void test4() {
    logger.debug("test4");
  }
}
