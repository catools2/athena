package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleTestRuns extends CSet<CZScaleTestRun> {
  public CZScaleTestRuns() {
  }

  public CZScaleTestRuns(CZScaleTestRun... c) {
    super(c);
  }

  public CZScaleTestRuns(Stream<CZScaleTestRun> stream) {
    super(stream);
  }

  public CZScaleTestRuns(Iterable<CZScaleTestRun> iterable) {
    super(iterable);
  }

  public CZScaleTestRun getById(String key) {
    return getFirstOrNull(v -> v.getKey().equals(key));
  }
}
