package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScalePlanExecutions extends CSet<CZScalePlanExecution> {
  public CZScalePlanExecutions() {
  }

  public CZScalePlanExecutions(CZScalePlanExecution... c) {
    super(c);
  }

  public CZScalePlanExecutions(Stream<CZScalePlanExecution> stream) {
    super(stream);
  }

  public CZScalePlanExecutions(Iterable<CZScalePlanExecution> iterable) {
    super(iterable);
  }
}
