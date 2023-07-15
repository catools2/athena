package org.catools.atlassian.zapi.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZApiExecutionDefects extends CSet<CZApiExecutionDefect> {
  public CZApiExecutionDefects() {
  }

  public CZApiExecutionDefects(CZApiExecutionDefect... c) {
    super(c);
  }

  public CZApiExecutionDefects(Stream<CZApiExecutionDefect> stream) {
    super(stream);
  }

  public CZApiExecutionDefects(Iterable<CZApiExecutionDefect> iterable) {
    super(iterable);
  }
}
