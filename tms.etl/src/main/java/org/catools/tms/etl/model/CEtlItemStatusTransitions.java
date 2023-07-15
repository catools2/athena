package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CEtlItemStatusTransitions extends CSet<CEtlItemStatusTransition> {
  public CEtlItemStatusTransitions() {
  }

  public CEtlItemStatusTransitions(CEtlItemStatusTransition... c) {
    super(c);
  }

  public CEtlItemStatusTransitions(Stream<CEtlItemStatusTransition> stream) {
    super(stream);
  }

  public CEtlItemStatusTransitions(Iterable<CEtlItemStatusTransition> iterable) {
    super(iterable);
  }
}
