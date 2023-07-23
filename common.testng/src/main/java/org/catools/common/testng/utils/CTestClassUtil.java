package org.catools.common.testng.utils;

import com.google.common.reflect.ClassPath;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.annotations.CTestIds;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.testng.CTestNGConfigs;
import org.testng.IMethodInstance;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.internal.annotations.DisabledRetryAnalyzer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

@Slf4j
@UtilityClass
public class CTestClassUtil {
  private static final CList<TestClassInfo> keyClasses = new CList<>();

  public static CList<String> getIssueKeys(List<IMethodInstance> list, boolean filterTestsWhichWillSkipInRun) {
    CList<String> issueKeys = new CList<>();
    CList<TestClassInfo> classNameMap = CTestClassUtil.getClassNameMap(filterTestsWhichWillSkipInRun);
    list = CTestSuiteUtil.filterMethodInstanceToExecute(list);

    for (IMethodInstance method : list) {
      for (TestClassInfo testClassInfo : classNameMap) {
        if (testClassInfo.getClassName().equals(method.getMethod().getTestClass().getName())) {
          issueKeys.add(testClassInfo.getTestId());
        }
      }
    }
    return issueKeys;
  }

  public static CSet<String> getClassNameForIssueKeys(CSet<String> issueIds, boolean filterTestsWhichWillSkipInRun) {
    return getClassNameMap(filterTestsWhichWillSkipInRun)
        .getAll(k -> issueIds.contains(k.getTestId()))
        .mapToSet(TestClassInfo::getClassName);
  }

  public static CList<TestClassInfo> getClassNameMap(boolean filterTestsWhichWillSkipInRun) {
    if (keyClasses.isEmpty()) {
      final ClassLoader loader = Thread.currentThread().getContextClassLoader();
      try {
        ClassPath classpath = ClassPath.from(loader);
        for (String testPackage : CTestNGConfigs.getTestPackages()) {
          for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(testPackage)) {
            new CList<>(classInfo.load().getMethods())
                .forEach(
                    m -> {
                      CList<Annotation> annotations = CList.of(m.getAnnotations());
                      if (annotations.has(a -> a instanceof Test)) {
                        boolean shouldSkipByAnnotation = CTestSuiteUtil.shouldSkipByAnnotation(annotations);
                        Annotation testIds = annotations.getFirstOrNull(a -> a instanceof CTestIds);
                        if (testIds != null) {
                          List.of(((CTestIds) testIds).ids())
                              .forEach(
                                  s -> {
                                    s = s.trim();
                                    keyClasses.add(new TestClassInfo(shouldSkipByAnnotation, s, classInfo.getName()));
                                  });
                        } else {
                          keyClasses.add(new TestClassInfo(shouldSkipByAnnotation, null, classInfo.getName()));
                        }
                      }
                    });
          }
        }
      } catch (IOException e) {
        log.error("Fail to build class name map", e);
      }
      log.info(keyClasses.size() + " tests class found.");
    }
    return filterTestsWhichWillSkipInRun ? keyClasses.getAll(t -> !t.isShouldSkipByAnnotation()) : keyClasses;
  }

  public static String getTestName(Class testClazz) {
    return testClazz.getName().replaceAll("\\W", "_");
  }

  public static boolean noRetryLeft(ITestResult result, boolean considerSuiteRetry) {
    if (considerSuiteRetry && !CTestNGConfigs.isLastSuiteRun()) {
      return false;
    }

    if (result.getMethod() == null
        || result.getMethod().getRetryAnalyzer(result) == null
        || result.getMethod().getRetryAnalyzer(result) instanceof DisabledRetryAnalyzer) {
      return true;
    }

    if (result.getMethod().getRetryAnalyzer(result) instanceof CRetryAnalyzer) {
      return ((CRetryAnalyzer) result.getMethod().getRetryAnalyzer(result)).isLastRetry();
    }

    log.warn(
        "You should use CRetryAnalyzer for retry analyzer annotation. method {}",
        result.getMethod().getMethodName());
    return false;
  }

  @Data
  public static class TestClassInfo {
    private final boolean shouldSkipByAnnotation;
    private final String testId;
    private final String className;
  }
}
