package org.catools.athena.analytics.registry;

import java.util.List;
import java.util.Optional;

/**
 * One named, parameterised query, generated from a Grafana panel by
 * {@code tools/extract_grafana.py}.
 *
 * @param id         stable id derived from the original panel SQL
 * @param title      the panel title it came from
 * @param views      analytics views the SQL reads, used for freshness reporting
 * @param tables     base tables the SQL reads; always live, so they carry no freshness
 * @param params     declared parameters
 * @param sql        SQL with {@code :name} binds and {@code {{name}}} operator slots
 * @param dashboards dashboards the panel appears on, for traceability
 * @param origin     'grafana' for a generated query, 'curated' for a hand-written one
 */
public record RegisteredQuery(
    String id,
    String title,
    List<String> views,
    List<String> tables,
    List<QueryParam> params,
    String sql,
    List<String> dashboards,
    String origin) {

  public RegisteredQuery {
    views = List.copyOf(views);
    tables = List.copyOf(tables);
    params = List.copyOf(params);
    dashboards = List.copyOf(dashboards);
  }

  public Optional<QueryParam> param(String name) {
    return params.stream().filter(p -> p.name().equals(name)).findFirst();
  }
}
