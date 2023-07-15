package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleTestResults extends CSet<CZScaleTestResult> {
  public CZScaleTestResults() {
  }

  public CZScaleTestResults(CZScaleTestResult... c) {
    super(c);
  }

  public CZScaleTestResults(Stream<CZScaleTestResult> stream) {
    super(stream);
  }

  public CZScaleTestResults(Iterable<CZScaleTestResult> iterable) {
    super(iterable);
  }
}
