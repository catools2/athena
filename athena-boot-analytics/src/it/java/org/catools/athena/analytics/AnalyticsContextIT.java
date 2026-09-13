package org.catools.athena.analytics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import javax.sql.DataSource;
import org.catools.athena.AthenaSpringBootIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * The context starts, and the pieces the service depends on are wired.
 *
 * <p>An integration test rather than a unit test because the pool takes Hikari's fail-fast
 * default: a context cannot load without a reachable database. That is intended - a service that
 * cannot read the warehouse has nothing to serve - and it means this can only be asserted against
 * a real one.
 *
 * <p>Note what is <em>not</em> asserted here. The service's own read-only pool - {@code
 * read-only=true} plus {@code -c default_transaction_read_only=on} - is configured in
 * {@code application.properties}, but {@code AthenaTestConfig} contributes its own
 * {@code DataSource} under the {@code testContainers} profile and {@code DataSourceAutoConfiguration}
 * backs off when one already exists. So the pool running here is the shared test one, and
 * asserting read-only against it would be asserting something about athena-common-test rather than
 * about this service. Those guards are worth verifying somewhere; this is not the place they can be.
 */
class AnalyticsContextIT extends AthenaSpringBootIT {

  @Autowired private DataSource[] dataSources;

  @Autowired private NamedParameterJdbcTemplate jdbcTemplate;

  @Test
  void opensExactlyOneConnectionPool() {
    // A second pool appearing is how the read-only guarantee would quietly stop being true in
    // production, where this service supplies its own.
    assertThat(dataSources, arrayWithSize(1));
  }

  @Test
  void resolvesTheAutoConfiguredJdbcTemplate() {
    // Supplied by JdbcTemplateAutoConfiguration, not by a hand-written @Bean.
    assertThat(jdbcTemplate, notNullValue());
    assertThat(
        jdbcTemplate.getJdbcTemplate().queryForObject("select 1", Integer.class), equalTo(1));
  }

}
