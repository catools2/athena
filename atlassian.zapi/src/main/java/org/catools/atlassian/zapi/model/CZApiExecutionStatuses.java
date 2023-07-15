package org.catools.atlassian.zapi.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZApiExecutionStatuses extends CSet<CZApiExecutionStatus> {
  public CZApiExecutionStatuses() {
  }

  public CZApiExecutionStatuses(CZApiExecutionStatus... c) {
    super(c);
  }

  public CZApiExecutionStatuses(Stream<CZApiExecutionStatus> stream) {
    super(stream);
  }

  public CZApiExecutionStatuses(Iterable<CZApiExecutionStatus> iterable) {
    super(iterable);
  }
}
