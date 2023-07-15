package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleTestCases extends CSet<CZScaleTestCase> {
  public CZScaleTestCases() {
  }

  public CZScaleTestCases(CZScaleTestCase... c) {
    super(c);
  }

  public CZScaleTestCases(Stream<CZScaleTestCase> stream) {
    super(stream);
  }

  public CZScaleTestCases(Iterable<CZScaleTestCase> iterable) {
    super(iterable);
  }
}
