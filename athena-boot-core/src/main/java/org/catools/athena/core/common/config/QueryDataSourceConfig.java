package org.catools.athena.core.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Dedicated connection pool for the ad-hoc query endpoint.
 *
 * <p>The endpoint runs SQL the caller supplies verbatim, so it deliberately does NOT reuse the
 * service's own {@code athena_core_user} connection - that role can write. It binds instead to
 * {@code athena_ro}, created in V1__create_users.sql and granted only SELECT in
 * V3__grant_permissions.sql, so a DELETE or DROP that slips past everything else is still refused
 * by the database. The pool is also small and marked read-only: this is a reporting side door, not
 * a second application datasource.
 *
 * <p>Nothing here is created unless {@code athena.query.enabled} is explicitly true.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "athena.query.enabled", havingValue = "true")
public class QueryDataSourceConfig {

  public static final String QUERY_DATA_SOURCE = "queryDataSource";

  @Bean(name = QUERY_DATA_SOURCE, destroyMethod = "close")
  public DataSource queryDataSource(
      @Value("${spring.datasource.url}") String url,
      @Value("${athena.query.datasource.username:athena_ro}") String username,
      @Value("${athena.query.datasource.password:athena_ro}") String password,
      @Value("${athena.query.datasource.pool-size:2}") int poolSize,
      @Value("${athena.query.statement-timeout-ms:30000}") int statementTimeoutMs) {
    log.warn(
        "athena.query.enabled is true: the ad-hoc SQL endpoint is exposed. It runs as '{}' and is"
            + " limited to what that role can read.",
        username);

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setPoolName("athena-query-ro");
    config.setMaximumPoolSize(poolSize);
    config.setMinimumIdle(0);
    config.setReadOnly(true);
    config.setAutoCommit(false);
    // Belt and braces alongside the role grant: a runaway report cannot pin a backend.
    config.addDataSourceProperty(
        "options", "-c default_transaction_read_only=on -c statement_timeout=" + statementTimeoutMs);
    return new HikariDataSource(config);
  }
}
