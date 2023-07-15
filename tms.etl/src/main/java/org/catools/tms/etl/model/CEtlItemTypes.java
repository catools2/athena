package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CEtlItemTypes extends CSet<CEtlItemType> {
  public CEtlItemTypes() {
  }

  public CEtlItemTypes(CEtlItemType... c) {
    super(c);
  }

  public CEtlItemTypes(Stream<CEtlItemType> stream) {
    super(stream);
  }

  public CEtlItemTypes(Iterable<CEtlItemType> iterable) {
    super(iterable);
  }

  public CEtlItemType getByName(String name) {
    return getFirstOrNull(p -> p.getName().equalsIgnoreCase(name));
  }
}
