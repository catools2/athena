package org.catools.athena.analytics.service;

import java.time.Instant;
import java.util.List;

/**
 * The rows a registry query produced, plus enough context for the UI to be honest about them.
 *
 * @param queryId   the query that ran
 * @param columns   column names and JDBC type names, in select order
 * @param rows      row values, aligned to {@code columns}
 * @param truncated true when the row cap cut the result short, so the UI can say so rather than
 *                  render a silently partial chart
 * @param views     the analytics views read
 * @param freshness when each view was last refreshed; materialized views are snapshots and a
 *                  dashboard that does not surface this is quietly showing stale numbers
 */
public record QueryResult(
    String queryId,
    List<Column> columns,
    List<List<Object>> rows,
    boolean truncated,
    List<String> views,
    List<ViewFreshness> freshness) {

  public record Column(String name, String type) {}

  public record ViewFreshness(String view, Instant refreshedAt, boolean materialized) {}
}
