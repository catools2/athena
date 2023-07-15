package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CEtlItems extends CSet<CEtlItem> {
  public CEtlItems() {
  }

  public CEtlItems(CEtlItem... c) {
    super(c);
  }

  public CEtlItems(Stream<CEtlItem> stream) {
    super(stream);
  }

  public CEtlItems(Iterable<CEtlItem> iterable) {
    super(iterable);
  }
}
