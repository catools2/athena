package org.catools.athena.model.analytics;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/** Rows returned by a registered analytics query. */
public record QueryResultDto(
    String queryId,
    List<Column> columns,
    List<List<Object>> rows,
    boolean truncated,
    List<String> views,
    List<ViewFreshness> freshness) implements Serializable {

  public record Column(String name, String type) implements Serializable {}

  public record ViewFreshness(String view, Instant refreshedAt, boolean materialized)
      implements Serializable {}
}
