package org.catools.common.testng.listeners;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.testng.utils.CTestSuiteUtil;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.List;

@Slf4j
public class CIMethodInterceptor implements IMethodInterceptor {

  @Override
  public List<IMethodInstance> intercept(List<IMethodInstance> list, ITestContext iTestContext) {
    if (!list.isEmpty()) {
      int sizeBeforeFilter = list.size();
      log.debug("Cleanup test method inventory.");
      list = CTestSuiteUtil.filterMethodInstanceToExecute(list);
      log.info(
          "{} out of {} tests will be executed after applying filter.",
          list.size(),
          sizeBeforeFilter);
    }
    return list;
  }
}
