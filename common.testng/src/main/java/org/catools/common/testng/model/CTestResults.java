package org.catools.common.testng.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.testng.ITestNGMethod;

import java.util.stream.Stream;

public class CTestResults extends CList<CTestResult> {
  public CTestResults() {
  }

  public CTestResults(CTestResult... c) {
    super(c);
  }

  public CTestResults(Stream<CTestResult> stream) {
    super(stream);
  }

  public CTestResults(Iterable<CTestResult> iterable) {
    super(iterable);
  }

  @JsonIgnore
  public CTestResult getTestResultOrNull(ITestNGMethod testNGMethod) {
    return getFirstOrNull(
        test ->
            test.getClassName().equals(testNGMethod.getTestClass().getName())
                && test.getMethodName().equals(testNGMethod.getMethodName()));
  }

  @JsonIgnore
  public boolean isPassed(ITestNGMethod method) {
    CTestResult testResultByMethod =
        getFirstOrNull(
            test ->
                test.getClassName().equals(method.getTestClass().getName())
                    && test.getMethodName().equals(method.getMethodName()));
    return testResultByMethod != null && testResultByMethod.getStatus().isPassed();
  }

  @JsonIgnore
  public CTestResult getTestResultByIdOrNull(String id) {
    return getFirstOrNull(test -> test.getTestIds() != null && test.getTestIds().contains(id));
  }

  public CSet<String> getAllIssueIds() {
    CSet<String> ids = new CSet<>();
    for (CTestResult result : this) {
      ids.addAll(result.getTestIds());
    }
    return ids;
  }

  public CHashMap<CExecutionStatus, CTestResults> getStatusMap() {
    CHashMap<CExecutionStatus, CTestResults> keys = new CHashMap<>();
    for (CTestResult result : this) {
      keys.putIfAbsent(result.getStatus(), new CTestResults());
      keys.get(result.getStatus()).add(result);
    }
    return keys;
  }
}
