package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleScriptResults extends CSet<CZScaleScriptResult> {
  public CZScaleScriptResults() {
  }

  public CZScaleScriptResults(CZScaleScriptResult... c) {
    super(c);
  }

  public CZScaleScriptResults(Stream<CZScaleScriptResult> stream) {
    super(stream);
  }

  public CZScaleScriptResults(Iterable<CZScaleScriptResult> iterable) {
    super(iterable);
  }

  public CZScaleScriptResult getByIndex(int index) {
    return getFirstOrNull(v -> v.getIndex() == index);
  }
}
