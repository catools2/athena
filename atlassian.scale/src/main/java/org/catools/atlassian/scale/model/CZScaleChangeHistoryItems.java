package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleChangeHistoryItems extends CSet<CZScaleChangeHistoryItem> {
  public CZScaleChangeHistoryItems() {
  }

  public CZScaleChangeHistoryItems(CZScaleChangeHistoryItem... c) {
    super(c);
  }

  public CZScaleChangeHistoryItems(Stream<CZScaleChangeHistoryItem> stream) {
    super(stream);
  }

  public CZScaleChangeHistoryItems(Iterable<CZScaleChangeHistoryItem> iterable) {
    super(iterable);
  }
}
