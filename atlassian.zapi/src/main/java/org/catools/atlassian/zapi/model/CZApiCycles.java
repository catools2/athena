package org.catools.atlassian.zapi.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZApiCycles extends CSet<CZApiCycle> {
  public CZApiCycles() {
  }

  public CZApiCycles(CZApiCycle... c) {
    super(c);
  }

  public CZApiCycles(Stream<CZApiCycle> stream) {
    super(stream);
  }

  public CZApiCycles(Iterable<CZApiCycle> iterable) {
    super(iterable);
  }

  public CZApiCycle getById(long id) {
    return getFirstOrNull(v -> v.getId() == id);
  }
}
