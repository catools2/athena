package org.catools.athena.analytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * When each analytics view last changed.
 *
 * <p>Eighteen of the forty-one objects are materialized views - snapshots that only move when
 * something refreshes them. A dashboard that does not say so will happily plot week-old numbers as
 * if they were current, which is the failure mode this exists to prevent.
 *
 * <p>The timestamps come from {@code athena.view_refresh_log}, written by
 * {@code athena.refresh_analytics_views()}. That is deliberate: PostgreSQL does not record a
 * refresh, and the usual proxy ({@code pg_stat_all_tables.last_analyze}) only moves when something
 * analyzes the view and cannot distinguish a refresh that succeeded from one that failed. Plain
 * views are always live and report no timestamp rather than a misleading one.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ViewFreshnessService {

  private static final String LOG_QUERY = """
      SELECT m.matviewname          AS view_name,
             l.refreshed_at         AS refreshed_at,
             coalesce(l.status, '') AS status
      FROM pg_matviews m
      LEFT JOIN athena.view_refresh_log l ON l.view_name = m.matviewname
      WHERE m.schemaname = 'athena'
      """;

  private final NamedParameterJdbcTemplate jdbc;

  public List<QueryResult.ViewFreshness> of(List<String> views) {
    Map<String, Entry> known = snapshot();
    return views.stream()
        .map(view -> {
          Entry entry = known.get(view);
          if (entry == null) {
            // Not a materialized view, so it reads through to live data.
            return new QueryResult.ViewFreshness(view, null, false);
          }
          return new QueryResult.ViewFreshness(view, entry.refreshedAt(), true);
        })
        .toList();
  }

  private record Entry(Instant refreshedAt, String status) {}

  /**
   * Read every materialized view's state in one round trip. A dashboard renders many panels at
   * once and each carries its view list, so a per-view lookup would multiply cheap queries by the
   * panel count for no benefit.
   */
  private Map<String, Entry> snapshot() {
    Map<String, Entry> byView = new HashMap<>();
    try {
      jdbc.query(LOG_QUERY, new MapSqlParameterSource(), rs -> {
        Instant refreshedAt = rs.getTimestamp("refreshed_at") == null
            ? null
            : rs.getTimestamp("refreshed_at").toInstant();
        String status = rs.getString("status");
        if (!"ok".equals(status) && refreshedAt != null) {
          // A failed refresh left the previous snapshot in place; the timestamp would claim
          // freshness the data does not have.
          refreshedAt = null;
        }
        byView.put(rs.getString("view_name"), new Entry(refreshedAt, status));
      });
    } catch (RuntimeException e) {
      // A missing log table or a database blip must not fail the query the caller actually asked
      // for; the UI shows "unknown" instead.
      log.debug("Could not read analytics view freshness", e);
    }
    return byView;
  }
}
