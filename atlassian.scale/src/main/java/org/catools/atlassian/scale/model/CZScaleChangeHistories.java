package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleChangeHistories extends CSet<CZScaleChangeHistory> {
  public CZScaleChangeHistories() {
  }

  public CZScaleChangeHistories(CZScaleChangeHistory... c) {
    super(c);
  }

  public CZScaleChangeHistories(Stream<CZScaleChangeHistory> stream) {
    super(stream);
  }

  public CZScaleChangeHistories(Iterable<CZScaleChangeHistory> iterable) {
    super(iterable);
  }
}
