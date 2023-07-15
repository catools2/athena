package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleTestScriptSteps extends CSet<CZScaleTestScriptStep> {
  public CZScaleTestScriptSteps() {
  }

  public CZScaleTestScriptSteps(CZScaleTestScriptStep... c) {
    super(c);
  }

  public CZScaleTestScriptSteps(Stream<CZScaleTestScriptStep> stream) {
    super(stream);
  }

  public CZScaleTestScriptSteps(Iterable<CZScaleTestScriptStep> iterable) {
    super(iterable);
  }
}
