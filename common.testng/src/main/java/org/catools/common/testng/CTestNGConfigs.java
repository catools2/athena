package org.catools.common.testng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CConfigUtil;
import org.catools.common.utils.CStringUtil;
import org.testng.ITestNGListener;
import org.testng.xml.XmlSuite;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CTestNGConfigs {
  private static final AtomicInteger SUITE_RUN_COUNTER = new AtomicInteger(1);

  public static Class<?> getBaseClassLoader() {
    try {
      return Class.forName(CHocon.asString(Configs.CATOOLS_TESTNG_BASE_TEST_CLASS_LOADER));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static Set<ITestNGListener> getListeners() {
    final Set<ITestNGListener> listeners = new HashSet<>();
    if (CHocon.has(Configs.CATOOLS_TESTNG_LISTENERS)) {
      for (String listener : CHocon.asStrings(Configs.CATOOLS_TESTNG_LISTENERS)) {
        try {
          listeners.add((ITestNGListener) Class.forName(listener).getConstructor().newInstance());
        } catch (Throwable t) {
          System.out.println(
              "Could not find CATOOLS_TESTNG_LISTENERS parameter " + listener + " in the class path.");
        }
      }
    }
    return listeners;
  }

  public static int getSeverityLevel() {
    return CHocon.asInteger(Configs.CATOOLS_TESTNG_RUN_SEVERITY_LEVEL);
  }

  public static int getRegressionDepth() {
    return CHocon.asInteger(Configs.CATOOLS_TESTNG_RUN_REGRESSION_DEPTH);
  }

  public static CList<String> getAnnotationsToIgnoreTestIfAnyMatch() {
    return CList.of(CHocon.asStrings(Configs.CATOOLS_TESTNG_IGNORE_TEST_WITH_ANY_ANNOTATION));
  }

  public static CList<String> getAnnotationsToIgnoreTestIfAllMatch() {
    return CList.of(CHocon.asStrings(Configs.CATOOLS_TESTNG_IGNORE_TEST_WITH_ALL_ANNOTATION));
  }

  public static CList<String> getAnnotationsToRunTestIfAllMatch() {
    return CList.of(CHocon.asStrings(Configs.CATOOLS_TESTNG_RUN_TEST_WITH_ALL_ANNOTATIONS));
  }

  public static CList<String> getAnnotationsToRunTestIfAnyMatch() {
    return CList.of(CHocon.asStrings(Configs.CATOOLS_TESTNG_RUN_TEST_WITH_ANY_ANNOTATIONS));
  }

  public static int getTestRetryCount() {
    return CHocon.asInteger(Configs.CATOOLS_TESTNG_TEST_RETRY_COUNT);
  }

  public static int getSuiteRetryCount() {
    return CHocon.asInteger(Configs.CATOOLS_TESTNG_SUITE_RETRY_COUNT);
  }

  public static int getSuiteRunCounter() {
    return SUITE_RUN_COUNTER.get();
  }

  public static CSet<String> getTestPackages() {
    return new CSet<>(CHocon.asStrings(Configs.CATOOLS_TESTNG_TEST_PACKAGES));
  }

  public static XmlSuite.ParallelMode getTestLevelParallel() {
    return CHocon.asEnum(Configs.CATOOLS_TESTNG_TEST_LEVEL_PARALLEL_MODE, XmlSuite.ParallelMode.class);
  }

  public static int getTestLevelThreadCount() {
    return CHocon.asInteger(Configs.CATOOLS_TESTNG_TEST_LEVEL_THREAD_COUNT);
  }

  public static XmlSuite.ParallelMode getSuiteLevelParallel() {
    return CHocon.asEnum(Configs.CATOOLS_TESTNG_SUITE_LEVEL_PARALLEL_MODE, XmlSuite.ParallelMode.class);
  }

  public static int getSuiteLevelThreadCount() {
    return CHocon.asInteger(Configs.CATOOLS_TESTNG_SUITE_LEVEL_THREAD_COUNT);
  }

  public static Integer incrementSuiteRun() {
    int suiteRun = SUITE_RUN_COUNTER.incrementAndGet();
    CConfigUtil.setRunName(isLastSuiteRun() ? CStringUtil.EMPTY : "run_" + suiteRun);
    return suiteRun;
  }

  public static boolean isFirstSuiteRun() {
    return getSuiteRunCounter() == 1;
  }

  public static boolean isLastSuiteRun() {
    return getSuiteRunCounter() >= getSuiteRetryCount() + 1;
  }

  public static boolean skipClassWithAwaitingTest() {
    return CHocon.asBoolean(Configs.CATOOLS_TESTNG_SKIP_CLASS_WITH_AWAITING_TEST);
  }

  public static boolean skipClassWithIgnoredTest() {
    return CHocon.asBoolean(Configs.CATOOLS_TESTNG_SKIP_CLASS_WITH_IGNORED_TEST);
  }

  public static String getTestNgResultName() {
    return CHocon.asString(Configs.CATOOLS_TESTNG_RESULT_XML_NAME);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_TESTNG_TEST_PACKAGES("catools.testng.test_packages"),
    CATOOLS_TESTNG_TEST_LEVEL_PARALLEL_MODE("catools.testng.test_level.parallel_mode"),
    CATOOLS_TESTNG_TEST_LEVEL_THREAD_COUNT("catools.testng.test_level.thread_count"),
    CATOOLS_TESTNG_SUITE_LEVEL_PARALLEL_MODE("catools.testng.suite_level.parallel_mode"),
    CATOOLS_TESTNG_SUITE_LEVEL_THREAD_COUNT("catools.testng.suite_level.thread_count"),
    CATOOLS_TESTNG_LISTENERS("catools.testng.listeners"),
    CATOOLS_TESTNG_TEST_RETRY_COUNT("catools.testng.test_retry_count"),
    CATOOLS_TESTNG_SUITE_RETRY_COUNT("catools.testng.suite_retry_count"),
    CATOOLS_TESTNG_BASE_TEST_CLASS_LOADER("catools.testng.base_test_class_loader"),
    CATOOLS_TESTNG_SKIP_CLASS_WITH_AWAITING_TEST("catools.testng.skip_class_with_awaiting_test"),
    CATOOLS_TESTNG_SKIP_CLASS_WITH_IGNORED_TEST("catools.testng.skip_class_with_ignored_test"),
    CATOOLS_TESTNG_RUN_SEVERITY_LEVEL("catools.testng.run_severity_level"),
    CATOOLS_TESTNG_RUN_REGRESSION_DEPTH("catools.testng.run_regression_depth"),
    CATOOLS_TESTNG_IGNORE_TEST_WITH_ANY_ANNOTATION("catools.testng.ignore_test_with_any_annotation"),
    CATOOLS_TESTNG_IGNORE_TEST_WITH_ALL_ANNOTATION("catools.testng.ignore_test_with_all_annotation"),
    CATOOLS_TESTNG_RUN_TEST_WITH_ALL_ANNOTATIONS("catools.testng.run_test_with_all_annotations"),
    CATOOLS_TESTNG_RUN_TEST_WITH_ANY_ANNOTATIONS("catools.testng.run_test_with_any_annotations"),
    CATOOLS_TESTNG_RESULT_XML_NAME("catools.testng.result_xml_name");

    private final String path;
  }
}
