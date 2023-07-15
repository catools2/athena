package org.catools.atlassian.zapi.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZApiExecutions extends CSet<CZApiExecution> {
  public CZApiExecutions() {
  }

  public CZApiExecutions(CZApiExecution... c) {
    super(c);
  }

  public CZApiExecutions(Stream<CZApiExecution> stream) {
    super(stream);
  }

  public CZApiExecutions(Iterable<CZApiExecution> iterable) {
    super(iterable);
  }

  public CZApiExecution getById(long id) {
    return getFirstOrNull(v -> v.getId() == id);
  }

  public CZApiExecutions getExecution(CSet<String> issueIds) {
    return new CZApiExecutions(getAll(e -> issueIds.contains(e.getIssueKey())));
  }
}
