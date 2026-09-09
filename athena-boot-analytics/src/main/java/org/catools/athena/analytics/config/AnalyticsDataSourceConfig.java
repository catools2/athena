package org.catools.athena.analytics.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * The service's only connection pool, and it is read-only by construction.
 *
 * <p>It binds to {@code athena_ro}, which holds nothing but SELECT (V1__create_users.sql,
 * V3__grant_permissions.sql). That matters because this service runs SQL that was authored
 * elsewhere - in Grafana - and reviewed as a dashboard rather than as application code. The role,
 * the read-only transaction default and the statement timeout are three independent reasons a
 * runaway or destructive query cannot do damage, and none of them depend on the SQL being correct.
 */
@Slf4j
@Configuration
public class AnalyticsDataSourceConfig {

  @Bean
  public DataSource analyticsDataSource(
      @Value("${athena.analytics.datasource.url}") String url,
      @Value("${athena.analytics.datasource.username:athena_ro}") String username,
      @Value("${athena.analytics.datasource.password:athena_ro}") String password,
      @Value("${athena.analytics.datasource.pool-size:8}") int poolSize,
      @Value("${athena.analytics.statement-timeout-ms:30000}") int statementTimeoutMs) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setPoolName("athena-analytics-ro");
    config.setMaximumPoolSize(poolSize);
    config.setMinimumIdle(1);
    config.setReadOnly(true);
    config.setAutoCommit(false);
    config.setConnectionTimeout(10_000);
    // Start degraded rather than CrashLoopBackOff if the database is down; dashboards
    // failing is better than the pod restarting into a reconnect storm.
    config.setInitializationFailTimeout(-1);
    config.addDataSourceProperty(
        "options",
        "-c default_transaction_read_only=on -c statement_timeout=" + statementTimeoutMs);
    log.info("Analytics read-only pool bound as '{}' with a {}ms statement timeout.",
        username, statementTimeoutMs);
    return new HikariDataSource(config);
  }

  @Bean
  public NamedParameterJdbcTemplate analyticsJdbcTemplate(DataSource analyticsDataSource) {
    return new NamedParameterJdbcTemplate(analyticsDataSource);
  }
}
