package org.catools.atlassian.zapi.model;

import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;

import java.util.stream.Stream;

public class CZApiProjects extends CSet<CZApiProject> {
  public CZApiProjects() {
  }

  public CZApiProjects(CZApiProject... c) {
    super(c);
  }

  public CZApiProjects(Stream<CZApiProject> stream) {
    super(stream);
  }

  public CZApiProjects(Iterable<CZApiProject> iterable) {
    super(iterable);
  }

  public CZApiProject getById(long id) {
    return getFirstOrNull(v -> v.getId() == id);
  }

  public CZApiProject getByName(String name) {
    return getFirstOrNull(v -> CStringUtil.equals(v.getName(), name));
  }
}
