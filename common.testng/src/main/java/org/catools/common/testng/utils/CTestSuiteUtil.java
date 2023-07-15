package org.catools.common.testng.utils;

import lombok.experimental.UtilityClass;
import org.catools.common.annotations.CAwaiting;
import org.catools.common.annotations.CIgnored;
import org.catools.common.annotations.CRegression;
import org.catools.common.annotations.CSeverity;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.testng.CTestNGConfigs;
import org.catools.common.testng.listeners.CExecutionStatisticListener;
import org.testng.IMethodInstance;
import org.testng.ITestNGMethod;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.catools.common.testng.listeners.CExecutionResultListener.isPassed;

@UtilityClass
public class CTestSuiteUtil {

  public static List<IMethodInstance> filterMethodInstanceToExecute(List<IMethodInstance> list) {
    CList<IMethodInstance> output = new CList<>(list);
    if (!output.isEmpty()) {
      output.removeIf(
          method -> {
            boolean result = method.getMethod().isTest() && shouldSkipForThisRun(method.getMethod());
            if (result) {
              CExecutionStatisticListener.removeTestMethod(method.getMethod());
            }
            return result;
          });
    }
    return output;
  }

  public static boolean shouldSkipForThisRun(ITestNGMethod method) {
    // if test passed previously then there is not reason to run it again
    return isPassed(method) || shouldSkipByAnnotation(getAnnotations(method));
  }

  public static boolean shouldSkipByAnnotation(CList<Annotation> annotations) {
    return shouldSkipByAnnotationsConfigRules(annotations)
        || shouldSkipByRegressionAndSeverityRules(annotations);
  }

  private static boolean shouldSkipByRegressionAndSeverityRules(CList<Annotation> annotations) {
    int severityLevel = CTestNGConfigs.getSeverityLevel();
    int regressionDepth = CTestNGConfigs.getRegressionDepth();

    // We should not skip any test if there is no severity level or regression depth defined
    if (severityLevel == -1 && regressionDepth == -1) {
      return false;
    }

    // If we have both severity level abd regression depth then we should have both match otherwise
    // we should skip
    if (severityLevel > -1 && regressionDepth > -1) {
      return shouldSkipBySeverityLevel(annotations) || shouldSkipByRegressionLevel(annotations);
    } else if (severityLevel > -1) {
      return shouldSkipBySeverityLevel(annotations);
    } else if (regressionDepth > -1) {
      return shouldSkipByRegressionLevel(annotations);
    }

    return false;
  }

  private static boolean shouldSkipByAnnotationsConfigRules(CList<Annotation> annotations) {
    if (shouldBeSkippedByAwaitingAnnotation(annotations) || shouldBeSkippedByIgnoredAnnotation(annotations)) {
      return true;
    }

    CList<String> annotationsToIgnoreTestIfAllMatch = CTestNGConfigs.getAnnotationsToIgnoreTestIfAllMatch();
    CList<String> annotationsToIgnoreTestIfAnyMatch = CTestNGConfigs.getAnnotationsToIgnoreTestIfAnyMatch();
    CList<String> annotationsToRunTestIfAllMatch = CTestNGConfigs.getAnnotationsToRunTestIfAllMatch();
    CList<String> annotationsToRunTestIfAnyMatch = CTestNGConfigs.getAnnotationsToRunTestIfAnyMatch();

    // If no configuration set to handle label then we do not skip by label
    if (annotationsToIgnoreTestIfAllMatch.isEmpty()
        && annotationsToIgnoreTestIfAnyMatch.isEmpty()
        && annotationsToRunTestIfAllMatch.isEmpty()
        && annotationsToRunTestIfAnyMatch.isEmpty()) {
      return false;
    }

    CSet<String> annotationNames = annotations.mapToSet(a -> a.annotationType().getSimpleName());

    // If any configuration set to handle label and test does not hve a label then we skip it
    if (annotationNames.isEmpty()) {
      return true;
    }

    if (annotationsToIgnoreTestIfAllMatch.isNotEmpty()
        && annotationNames.containsAll(annotationsToIgnoreTestIfAllMatch)) {
      return true;
    }

    if (annotationsToIgnoreTestIfAnyMatch.isNotEmpty()
        && annotationNames.containsAny(annotationsToIgnoreTestIfAnyMatch)) {
      return true;
    }

    if (annotationsToRunTestIfAnyMatch.isEmpty() && annotationsToRunTestIfAllMatch.isEmpty()) {
      return false;
    }

    if (annotationsToRunTestIfAllMatch.isNotEmpty()
        && annotationNames.containsAll(annotationsToRunTestIfAllMatch)) {
      return false;
    }

    return !annotationsToRunTestIfAnyMatch.isNotEmpty()
        || !annotationNames.containsAny(annotationsToRunTestIfAnyMatch);
  }

  private static boolean shouldSkipBySeverityLevel(CList<Annotation> annotations) {
    return annotations.hasNot(a -> a instanceof CSeverity) ||
        annotations.has(a -> a instanceof CSeverity && (((CSeverity) a).level() > CTestNGConfigs.getSeverityLevel()));
  }

  private static boolean shouldSkipByRegressionLevel(CList<Annotation> annotations) {
    return annotations.hasNot(a -> a instanceof CRegression) ||
        annotations.has(a -> a instanceof CRegression && (((CRegression) a).depth() > CTestNGConfigs.getRegressionDepth()));
  }

  private static boolean shouldBeSkippedByAwaitingAnnotation(CList<Annotation> annotations) {
    return CTestNGConfigs.skipClassWithAwaitingTest() && annotations.has(an -> an instanceof CAwaiting);
  }


  private static boolean shouldBeSkippedByIgnoredAnnotation(CList<Annotation> annotations) {
    return CTestNGConfigs.skipClassWithIgnoredTest() && annotations.has(an -> an instanceof CIgnored);
  }

  private static CList<Annotation> getAnnotations(ITestNGMethod method) {
    if (!hasAnnotation(method)) {
      return new CList<>();
    }
    return new CList<>(method.getConstructorOrMethod().getMethod().getAnnotations());
  }

  private static boolean hasAnnotation(ITestNGMethod method) {
    return method != null
        && method.getConstructorOrMethod() != null
        && method.getConstructorOrMethod().getMethod() != null
        && method.getConstructorOrMethod().getMethod().getAnnotations() != null;
  }
}
