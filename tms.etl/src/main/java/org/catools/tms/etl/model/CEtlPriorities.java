package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CEtlPriorities extends CSet<CEtlPriority> {
  public CEtlPriorities() {
  }

  public CEtlPriorities(CEtlPriority... c) {
    super(c);
  }

  public CEtlPriorities(Stream<CEtlPriority> stream) {
    super(stream);
  }

  public CEtlPriorities(Iterable<CEtlPriority> iterable) {
    super(iterable);
  }

  public CEtlPriority getByName(String name) {
    return getFirstOrNull(p -> p.getName().equalsIgnoreCase(name));
  }
}
