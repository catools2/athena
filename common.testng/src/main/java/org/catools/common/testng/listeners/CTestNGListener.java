package org.catools.common.testng.listeners;

import org.catools.common.collections.CList;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.functions.CMemoize;
import org.catools.common.testng.CTestNGConfigs;
import org.catools.common.testng.CTestNGResultGenerator;
import org.catools.common.testng.utils.CTestSuiteUtil;
import org.catools.common.testng.utils.CXmlSuiteUtils;
import org.catools.common.tests.exception.CSkipAwaitingTestException;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class CTestNGListener implements CITestNGListener, IMethodInterceptor, IAlterSuiteListener, IReporter {
  private static final CMemoize<Integer> suiteCounter = new CMemoize(CTestNGConfigs::incrementSuiteRun);
  private static final CList<ITestNGListener> listeners = new CList<>();
  private static final CList<ISuite> suites = new CList<>();

  public CTestNGListener() {
  }

  public static CList<ITestNGListener> getListeners() {
    return listeners;
  }

  public static void addListeners(ITestNGListener... listeners) {
    getListeners().addAll(new CList<>(listeners).getAll(l -> getListeners().notContains(l)));
  }

  @Override
  public int priority() {
    return 0;
  }

  @Override // 1- IExecutionListener
  public void onExecutionStart() {
    doIf(l -> l instanceof IExecutionListener, l -> ((IExecutionListener) l).onExecutionStart());
  }

  @Override // 2- ISuiteListener
  public void onStart(ISuite suite) {
    doIf(l -> l instanceof ISuiteListener, l -> ((ISuiteListener) l).onStart(suite));
    suiteCounter.reset();
  }

  @Override // 3- IConfigurationListener2
  public void beforeConfiguration(ITestResult result) {
    doIf(l -> l instanceof IConfigurationListener, l -> ((IConfigurationListener) l).beforeConfiguration(result));
  }

  @Override // 4- IConfigurationListener
  public void onConfigurationSuccess(ITestResult result) {
    doIf(l -> l instanceof IConfigurationListener, l -> ((IConfigurationListener) l).onConfigurationSuccess(result));
  }

  @Override // 4- IConfigurationListener
  public void onConfigurationFailure(ITestResult result) {
    doIf(l -> l instanceof IConfigurationListener, l -> ((IConfigurationListener) l).onConfigurationFailure(result));
  }

  @Override // 4- IConfigurationListener
  public void onConfigurationSkip(ITestResult result) {
    doIf(l -> l instanceof IConfigurationListener, l -> ((IConfigurationListener) l).onConfigurationSkip(result));
  }

  @Override // 5- ITestListener
  public void onStart(ITestContext context) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onStart(context));
  }

  @Override // 6- IClassListener
  public void onBeforeClass(ITestClass testClass) {
    doIf(l -> l instanceof IClassListener, l -> ((IClassListener) l).onBeforeClass(testClass));
  }

  @Override // 7- ITestListener
  public void onTestStart(ITestResult result) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onTestStart(result));
  }

  // 8- IInvokedMethodListener
  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    doIf(l -> l instanceof IInvokedMethodListener, l -> ((IInvokedMethodListener) l).beforeInvocation(method, testResult));
  }

  // 8- IInvokedMethodListener
  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    if (method.isTestMethod()) {
      if (CTestSuiteUtil.shouldSkipForThisRun(method.getTestMethod())) {
        throw new CSkipAwaitingTestException("Skipping test by annotation rules!");
      }
    }
    doIf(l -> l instanceof IInvokedMethodListener, l -> ((IInvokedMethodListener) l).beforeInvocation(method, testResult, context));
  }

  // 9- IInvokedMethodListener
  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    doIf(l -> l instanceof IInvokedMethodListener, l -> ((IInvokedMethodListener) l).afterInvocation(method, testResult));
  }

  // 9- IInvokedMethodListener
  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    doIf(l -> l instanceof IInvokedMethodListener, l -> ((IInvokedMethodListener) l).afterInvocation(method, testResult, context));
  }

  @Override // 10- ITestListener
  public void onTestSuccess(ITestResult result) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onTestSuccess(result));
  }

  @Override // 11- ITestListener
  public void onTestFailure(ITestResult result) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onTestFailure(result));
  }

  @Override // 12- ITestListener
  public void onTestSkipped(ITestResult result) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onTestSkipped(result));
  }

  @Override // 13- ITestListener
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onTestFailedButWithinSuccessPercentage(result));
  }

  @Override // 14- IClassListener
  public void onAfterClass(ITestClass testClass) {
    doIf(l -> l instanceof IClassListener, l -> ((IClassListener) l).onAfterClass(testClass));
  }

  @Override // 15- ITestListener
  public void onFinish(ITestContext context) {
    doIf(l -> l instanceof ITestListener, l -> ((ITestListener) l).onFinish(context));
  }

  @Override // 16- ISuiteListener
  public void onFinish(ISuite suite) {
    doIf(l -> l instanceof ISuiteListener, l -> ((ISuiteListener) l).onFinish(suite));
    suites.add(suite);
    suiteCounter.get();
  }

  @Override // 17- IExecutionListener
  public void onExecutionFinish() {
    doIf(l -> l instanceof IExecutionListener, l -> ((IExecutionListener) l).onExecutionFinish());
    CTestNGResultGenerator.generateReport(suites, CPathConfigs.getOutputPath());
  }

  @Override
  public List<IMethodInstance> intercept(List<IMethodInstance> list, ITestContext iTestContext) {
    CList<ITestNGListener> methodInterceptor = listeners.getAll(l -> l instanceof IMethodInterceptor);
    if (!methodInterceptor.isEmpty()) {
      return ((IMethodInterceptor) methodInterceptor.getFirst()).intercept(list, iTestContext);
    }
    return list;
  }

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    doIf(l -> l instanceof IReporter, l -> ((IReporter) l).generateReport(xmlSuites, suites, outputDirectory));
  }

  @Override
  public void alter(List<XmlSuite> list) {
    CList<XmlSuite> retryList = new CList<>();
    for (int i = 0; i < CTestNGConfigs.getSuiteRetryCount(); i++) {
      for (XmlSuite xmlSuite : list) {
        XmlSuite suite = CXmlSuiteUtils.copy(xmlSuite, " Retry " + (i + 1));
        retryList.add(suite);
      }
    }
    list.addAll(retryList);
  }

  private <T extends ITestNGListener> void doIf(Predicate<ITestNGListener> predicate, Consumer<T> action) {
    CList<ITestNGListener> list = new CList<>(listeners.getAll(predicate));

    if (list.isEmpty()) {
      return;
    }

    // We are using some listener to do Suite manipulation so we give CITestNGListener higher
    // priority
    CList<ITestNGListener> cListeners = list.getAll(l -> l instanceof CITestNGListener);
    cListeners.sort(Comparator.comparingInt(f -> ((CITestNGListener) f).priority()));
    cListeners.forEach(l -> action.accept((T) l));

    list.getAll(l -> !(l instanceof CITestNGListener)).forEach(l -> action.accept((T) l));
  }
}
