package org.catools.common.testng.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;

@UtilityClass
public class CTestReportUtil {

  public static synchronized void removeDuplicateResults(CList<ISuite> suites) {
    CList<Pair<ITestContext, CSet<ITestResult>>> totalPassed = new CList<>();
    CList<Pair<ITestContext, CSet<ITestResult>>> totalFailed = new CList<>();
    CList<Pair<ITestContext, CSet<ITestResult>>> totalSkipped = new CList<>();
    CList<Pair<ITestContext, CSet<ITestResult>>> totalPassedConfig = new CList<>();
    CList<Pair<ITestContext, CSet<ITestResult>>> totalFailedConfig = new CList<>();
    CList<Pair<ITestContext, CSet<ITestResult>>> totalSkippedConfig = new CList<>();

    suites.sort(Comparator.comparing(ISuite::getName));
    refreshList(
        suites,
        totalPassed,
        totalFailed,
        totalSkipped,
        totalPassedConfig,
        totalFailedConfig,
        totalSkippedConfig);

    removeDuplicate(
        totalPassed,
        (context, result) -> context.getPassedTests().getAllResults().remove(result));
    removeDuplicate(
        totalPassed,
        (context, result) ->
            context.getFailedButWithinSuccessPercentageTests().getAllResults().remove(result));

    removeDuplicate(
        totalFailed,
        (context, result) -> context.getFailedTests().getAllResults().remove(result));
    removeDuplicate(
        totalFailed,
        (context, result) ->
            context.getFailedButWithinSuccessPercentageTests().getAllResults().remove(result));

    removeDuplicate(
        totalSkipped,
        (context, result) -> context.getSkippedTests().getAllResults().remove(result));

    removeDuplicate(
        totalPassedConfig,
        (context, result) -> context.getPassedConfigurations().getAllResults().remove(result));
    removeDuplicate(
        totalFailedConfig,
        (context, result) -> context.getFailedConfigurations().getAllResults().remove(result));
    removeDuplicate(
        totalSkippedConfig,
        (context, result) -> context.getSkippedConfigurations().getAllResults().remove(result));

    refreshList(
        suites,
        totalPassed,
        totalFailed,
        totalSkipped,
        totalPassedConfig,
        totalFailedConfig,
        totalSkippedConfig);

    // Remove passed tests from other lists
    removeMatches(
        totalPassed,
        totalFailed,
        (context, result) -> context.getFailedTests().getAllResults().remove(result));
    removeMatches(
        totalPassed,
        totalSkipped,
        (context, result) -> context.getSkippedTests().getAllResults().remove(result));

    // Remove failed tests from skipped lists
    removeMatches(
        totalFailed,
        totalSkipped,
        (context, result) -> context.getSkippedTests().getAllResults().remove(result));

    // Remove passed configuration from other lists
    removeMatches(
        totalPassedConfig,
        totalFailedConfig,
        (context, result) -> context.getFailedConfigurations().getAllResults().remove(result));
    removeMatches(
        totalPassedConfig,
        totalSkippedConfig,
        (context, result) -> context.getSkippedConfigurations().getAllResults().remove(result));

    // Remove failed configuration from skipped lists
    removeMatches(
        totalFailedConfig,
        totalSkippedConfig,
        (context, result) -> context.getSkippedConfigurations().getAllResults().remove(result));
  }

  private static void removeMatches(
      CList<Pair<ITestContext, CSet<ITestResult>>> mainList,
      CList<Pair<ITestContext, CSet<ITestResult>>> toBeClean,
      BiConsumer<ITestContext, ITestResult> remove) {
    for (Pair<ITestContext, CSet<ITestResult>> m : mainList) {
      for (Map.Entry<ITestContext, CSet<ITestResult>> t : toBeClean) {
        removeDuplicate(m.getRight(), t, remove);
      }
    }
  }

  private static void refreshList(
      CList<ISuite> suites,
      CList<Pair<ITestContext, CSet<ITestResult>>> totalPassed,
      CList<Pair<ITestContext, CSet<ITestResult>>> totalFailed,
      CList<Pair<ITestContext, CSet<ITestResult>>> totalSkipped,
      CList<Pair<ITestContext, CSet<ITestResult>>> totalPassedConfig,
      CList<Pair<ITestContext, CSet<ITestResult>>> totalFailedConfig,
      CList<Pair<ITestContext, CSet<ITestResult>>> totalSkippedConfig) {
    totalPassed.clear();
    totalFailed.clear();
    totalSkipped.clear();
    totalPassedConfig.clear();
    totalFailedConfig.clear();
    totalSkippedConfig.clear();

    for (ISuite suite : suites) {
      synchronized (suite) {
        for (ISuiteResult sr : suite.getResults().values()) {
          ITestContext testContext = sr.getTestContext();
          Pair<ITestContext, CSet<ITestResult>> passed =
              Pair.of(testContext, new CSet<>(testContext.getPassedTests().getAllResults()));
          passed
              .getValue()
              .addAll(testContext.getFailedButWithinSuccessPercentageTests().getAllResults());

          totalPassed.add(passed);
          totalFailed.add(
              Pair.of(testContext, new CSet<>(testContext.getFailedTests().getAllResults())));
          totalSkipped.add(
              Pair.of(testContext, new CSet<>(testContext.getSkippedTests().getAllResults())));
          totalPassedConfig.add(
              Pair.of(
                  testContext, new CSet<>(testContext.getPassedConfigurations().getAllResults())));
          totalFailedConfig.add(
              Pair.of(
                  testContext, new CSet<>(testContext.getFailedConfigurations().getAllResults())));
          totalSkippedConfig.add(
              Pair.of(
                  testContext, new CSet<>(testContext.getSkippedConfigurations().getAllResults())));
        }
      }
    }
  }

  private static void removeDuplicate(
      CList<Pair<ITestContext, CSet<ITestResult>>> resultSet,
      BiConsumer<ITestContext, ITestResult> remove) {
    CSet<ITestResult> localCopy = new CSet<>();
    for (Pair<ITestContext, CSet<ITestResult>> pair : resultSet) {
      for (ITestResult r : pair.getValue()) {
        ITestResult match = localCopy.getFirstOrNull(l -> isSameTestMethod(l, r));
        if (match != null) {
          remove.accept(pair.getKey(), r);
        } else {
          localCopy.add(r);
        }
      }
    }
  }

  private static void removeDuplicate(
      CSet<ITestResult> highPriorityResultSet,
      Map.Entry<ITestContext, CSet<ITestResult>> lowPriorityResultSet,
      BiConsumer<ITestContext, ITestResult> remove) {
    highPriorityResultSet.forEach(
        r -> {
          ITestResult match =
              lowPriorityResultSet.getValue().getFirstOrNull(l -> isSameTestMethod(l, r));
          if (match != null) {
            remove.accept(lowPriorityResultSet.getKey(), match);
          }
        });
  }

  private static boolean isSameTestMethod(ITestResult result1, ITestResult result2) {
    return result1.getInstanceName().equals(result2.getInstanceName())
        && result1.getMethod().getMethodName().equals(result2.getMethod().getMethodName())
        && ((result1.getParameters() == null && result2.getParameters() == null)
        || result1.getParameters().getClass().equals(result2.getParameters().getClass()));
  }
}
