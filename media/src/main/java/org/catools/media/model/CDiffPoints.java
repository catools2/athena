package org.catools.media.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CDiffPoints extends CSet<CDiffPoint> {
  public CDiffPoints() {
  }

  public CDiffPoints(CDiffPoint... c) {
    super(c);
  }

  public CDiffPoints(Stream<CDiffPoint> stream) {
    super(stream);
  }

  public CDiffPoints(Iterable<CDiffPoint> iterable) {
    super(iterable);
  }
}
