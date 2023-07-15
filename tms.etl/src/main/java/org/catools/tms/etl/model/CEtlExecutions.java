package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CEtlExecutions extends CSet<CEtlExecution> {
  public CEtlExecutions() {
  }

  public CEtlExecutions(CEtlExecution... c) {
    super(c);
  }

  public CEtlExecutions(Stream<CEtlExecution> stream) {
    super(stream);
  }

  public CEtlExecutions(Iterable<CEtlExecution> iterable) {
    super(iterable);
  }
}
