package org.catools.athena.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Logs HikariCP and JPA configuration on application startup.
 * This helps with debugging connection pool and database issues.
 */
@Slf4j
@Configuration
public class DataSourceConfigLogger {

  public DataSourceConfigLogger(Environment env) {
    logHikariConfiguration(env);
    logJpaConfiguration(env);
  }

  /**
   * Logs HikariCP connection pool configuration
   */
  private void logHikariConfiguration(Environment env) {
    log.info("====== HikariCP Configuration ======");
    log.info("Minimum Idle Connections: {}", getPropertyValue(env, "spring.datasource.hikari.minimum-idle", "Not set"));
    log.info("Maximum Pool Size: {}", getPropertyValue(env, "spring.datasource.hikari.maximum-pool-size", "Not set"));
    log.info("Connection Timeout (ms): {}", getPropertyValue(env, "spring.datasource.hikari.connection-timeout", "Not set"));
    log.info("Idle Timeout (ms): {}", getPropertyValue(env, "spring.datasource.hikari.idle-timeout", "Not set"));
    log.info("Max Lifetime (ms): {}", getPropertyValue(env, "spring.datasource.hikari.max-lifetime", "Not set"));
    log.info("Leak Detection Threshold (ms): {}", getPropertyValue(env, "spring.datasource.hikari.leak-detection-threshold", "Not set"));
    log.info("Auto Commit: {}", getPropertyValue(env, "spring.datasource.hikari.auto-commit", "Not set"));
    log.info("Pool Name: {}", getPropertyValue(env, "spring.datasource.hikari.pool-name", "Not set"));
    log.info("Schema: {}", getPropertyValue(env, "spring.datasource.hikari.schema", "Not set"));

    // DataSource connection properties
    log.info("---- DataSource Connection ----");
    log.info("Database URL: {}", getMaskedUrl(getPropertyValue(env, "spring.datasource.url", "Not set")));
    log.info("Database Username: {}", getPropertyValue(env, "spring.datasource.username", "Not set"));
    log.info("Database Driver: {}", getPropertyValue(env, "spring.datasource.driver-class-name", "Not set"));

    // Hikari data source properties
    log.info("---- Hikari Data Source Properties ----");
    log.info("Cache Prepared Statements: {}", getPropertyValue(env, "spring.datasource.hikari.data-source-properties.cachePrepStmts", "Not set"));
    log.info("Prepared Statement Cache Size: {}", getPropertyValue(env, "spring.datasource.hikari.data-source-properties.prepStmtCacheSize", "Not set"));
    log.info("Prepared Statement Cache SQL Limit: {}", getPropertyValue(env, "spring.datasource.hikari.data-source-properties.prepStmtCacheSqlLimit", "Not set"));
    log.info("Socket Timeout (ms): {}", getPropertyValue(env, "spring.datasource.hikari.data-source-properties.socketTimeout", "Not set"));
    log.info("TCP Keep Alives: {}", getPropertyValue(env, "spring.datasource.hikari.data-source-properties.tcpKeepAlives", "Not set"));

    log.info("====== End HikariCP Configuration ======");
  }

  /**
   * Logs JPA and Hibernate configuration
   */
  private void logJpaConfiguration(Environment env) {
    log.info("====== JPA/Hibernate Configuration ======");

    log.info("Show SQL: {}", getPropertyValue(env, "spring.jpa.show-sql", "false"));
    log.info("Open in View: {}", getPropertyValue(env, "spring.jpa.open-in-view", "false"));
    log.info("DDL Auto: {}", getPropertyValue(env, "spring.jpa.hibernate.ddl-auto", "Not set"));

    log.info("---- Hibernate Properties ----");
    log.info("Format SQL: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.format_sql", "Not set"));
    log.info("Enable Lazy Load No Trans: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.enable_lazy_load_no_trans", "Not set"));
    log.info("JDBC Batch Size: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.jdbc.batch_size", "Not set"));
    log.info("Order Inserts: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.order_inserts", "Not set"));
    log.info("Order Updates: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.order_updates", "Not set"));
    log.info("Batch Versioned Data: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.batch_versioned_data", "Not set"));
    log.info("Default Schema: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.default_schema", "Not set"));
    log.info("JDBC Time Zone: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.jdbc.time_zone", "Not set"));
    log.info("Dialect: {}", getPropertyValue(env, "spring.jpa.properties.hibernate.dialect", "Auto-detected"));

    log.info("====== End JPA/Hibernate Configuration ======");
  }

  /**
   * Gets a property value from the environment or returns a default value
   */
  private String getPropertyValue(Environment env, String key, String defaultValue) {
    String value = env.getProperty(key);
    return value != null ? value : defaultValue;
  }

  /**
   * Masks the database URL to hide sensitive information
   */
  private String getMaskedUrl(String url) {
    if (url == null || url.equals("Not set")) {
      return url;
    }
    // Extract just the host and database, hide any credentials
    try {
      if (url.contains("@")) {
        return url.substring(0, url.indexOf("@")) + "@***MASKED***";
      }
      return url;
    } catch (Exception e) {
      return "***UNABLE TO PARSE***";
    }
  }
}

