package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleTestExecutions extends CSet<CZScaleTestExecution> {
  public CZScaleTestExecutions() {
  }

  public CZScaleTestExecutions(CZScaleTestExecution... c) {
    super(c);
  }

  public CZScaleTestExecutions(Stream<CZScaleTestExecution> stream) {
    super(stream);
  }

  public CZScaleTestExecutions(Iterable<CZScaleTestExecution> iterable) {
    super(iterable);
  }

  public CZScaleTestExecution getById(long id) {
    return getFirstOrNull(v -> v.getId() == id);
  }

  public CZScaleTestExecutions getExecution(CSet<String> issueIds) {
    return new CZScaleTestExecutions(getAll(e -> issueIds.contains(e.getTestCaseKey())));
  }
}
