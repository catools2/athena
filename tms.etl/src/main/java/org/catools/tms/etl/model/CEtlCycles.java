package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;

import java.util.Objects;
import java.util.stream.Stream;

public class CEtlCycles extends CSet<CEtlCycle> {
  public CEtlCycles() {
  }

  public CEtlCycles(CEtlCycle... c) {
    super(c);
  }

  public CEtlCycles(Stream<CEtlCycle> stream) {
    super(stream);
  }

  public CEtlCycles(Iterable<CEtlCycle> iterable) {
    super(iterable);
  }

  public CEtlCycle getByName(long projectId, long versionId, String name) {
    return getFirstOrNull(
        c ->
            CStringUtil.equalsIgnoreCase(c.getName(), name)
                && Objects.equals(c.getVersion().getProject().getId(), projectId)
                && Objects.equals(c.getVersion().getId(), versionId));
  }
}
