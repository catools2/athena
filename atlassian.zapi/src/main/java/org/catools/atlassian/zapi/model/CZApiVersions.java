package org.catools.atlassian.zapi.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZApiVersions extends CSet<CZApiVersion> {
  public CZApiVersions() {
  }

  public CZApiVersions(CZApiVersion... c) {
    super(c);
  }

  public CZApiVersions(Stream<CZApiVersion> stream) {
    super(stream);
  }

  public CZApiVersions(Iterable<CZApiVersion> iterable) {
    super(iterable);
  }

  public CZApiVersion getById(long id) {
    return getFirstOrNull(v -> v.getId() == id);
  }
}
