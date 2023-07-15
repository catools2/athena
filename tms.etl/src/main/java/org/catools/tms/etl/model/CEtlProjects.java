package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;

import java.util.stream.Stream;

public class CEtlProjects extends CSet<CEtlProject> {
  public CEtlProjects() {
  }

  public CEtlProjects(CEtlProject... c) {
    super(c);
  }

  public CEtlProjects(Stream<CEtlProject> stream) {
    super(stream);
  }

  public CEtlProjects(Iterable<CEtlProject> iterable) {
    super(iterable);
  }

  public CEtlProject getByName(String name) {
    return getFirstOrNull(p -> CStringUtil.equalsIgnoreCase(p.getName(), name));
  }

  public CEtlProject getByName(CEtlProject project) {
    return getFirstOrNull(p -> p.equals(project));
  }
}
