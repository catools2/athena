package org.catools.athena.core.common.service;

import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.common.config.QueryDataSourceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Runs caller-supplied SQL against the read-only pool from {@link QueryDataSourceConfig}.
 *
 * <p>The statement text cannot be parameterised - the whole point of the endpoint is that the
 * caller brings the query - so containment is structural rather than syntactic: an {@code
 * athena_ro} role that holds only SELECT, a connection pinned read-only, a per-statement timeout,
 * and a row cap. Nothing is bound to this service unless {@code athena.query.enabled} is true.
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "athena.query.enabled", havingValue = "true")
public class QueryServiceImpl implements QueryService {

  private final DataSource queryDataSource;
  private final int maxRows;
  private final int queryTimeoutSeconds;

  public QueryServiceImpl(
      @Qualifier(QueryDataSourceConfig.QUERY_DATA_SOURCE) DataSource queryDataSource,
      @Value("${athena.query.max-rows:10000}") int maxRows,
      @Value("${athena.query.statement-timeout-ms:30000}") int statementTimeoutMs) {
    this.queryDataSource = queryDataSource;
    this.maxRows = maxRows;
    // Ceil to at least a second: setQueryTimeout(0) would mean "no limit".
    this.queryTimeoutSeconds = Math.max(1, statementTimeoutMs / 1000);
  }

  @Override
  public Optional<Object> querySingleResult(final String query) {
    log.debug("querySingleResult(query={})", query);
    Set<Object> results = execute(query, 1);
    return results.stream().findFirst();
  }

  @Override
  public Optional<Set<Object>> queryCollection(final String query) {
    log.debug("queryCollection(query={})", query);
    Set<Object> results = execute(query, maxRows);
    return results.isEmpty() ? Optional.empty() : Optional.of(results);
  }

  /** Reads the first column of up to {@code rowLimit} rows. */
  private Set<Object> execute(final String query, final int rowLimit) {
    // LinkedHashSet, not HashSet: the caller's ORDER BY is meaningless if the response reorders it.
    Set<Object> results = new LinkedHashSet<>();

    try (Connection connection = queryDataSource.getConnection()) {
      connection.setReadOnly(true);
      try (Statement statement = connection.createStatement()) {
        statement.setQueryTimeout(queryTimeoutSeconds);
        statement.setMaxRows(rowLimit);
        try (@SuppressWarnings("sql") ResultSet rs = statement.executeQuery(query)) {
          while (rs.next()) {
            results.add(rs.getObject(1));
          }
        }
      }
      connection.rollback();
    } catch (SQLException e) {
      // The message can quote the caller's own SQL back, so it is safe to return; the stack trace
      // stays in the log.
      log.error("Error executing query: {}", query, e);
      throw new IllegalArgumentException("Failed to execute query: " + e.getMessage(), e);
    }

    return results;
  }
}
