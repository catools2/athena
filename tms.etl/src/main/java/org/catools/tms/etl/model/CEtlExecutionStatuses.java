package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.Objects;
import java.util.stream.Stream;

public class CEtlExecutionStatuses extends CSet<CEtlExecutionStatus> {
  public CEtlExecutionStatuses() {
  }

  public CEtlExecutionStatuses(CEtlExecutionStatus... c) {
    super(c);
  }

  public CEtlExecutionStatuses(Stream<CEtlExecutionStatus> stream) {
    super(stream);
  }

  public CEtlExecutionStatuses(Iterable<CEtlExecutionStatus> iterable) {
    super(iterable);
  }

  public CEtlExecutionStatus getByIdOrAddIfNotExists(CEtlExecutionStatus newStatus) {
    if (newStatus == null) {
      return null;
    }
    CEtlExecutionStatus status = getFirstOrNull(u -> Objects.equals(u.getId(), newStatus.getId()));
    if (status != null) {
      return status;
    }
    add(newStatus);
    return newStatus;
  }
}
