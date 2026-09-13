package org.catools.athena.analytics.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import feign.FeignException;
import feign.TypedResponse;
import java.util.Map;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.analytics.feign.AnalyticsQueryFeignClient;
import org.catools.athena.model.analytics.AdHocQueryRequestDto;
import org.catools.athena.model.analytics.QueryResultDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** The direct read-only SQL HTTP contract over the published Feign client. */
class AnalyticsQueryControllerIT extends AthenaSpringBootIT {

  private AnalyticsQueryFeignClient analyticsQueryFeignClient;

  @BeforeAll
  void beforeAllPackages() {
    analyticsQueryFeignClient = testFeignBuilder.getClient(AnalyticsQueryFeignClient.class);
  }

  @Test
  void shallExecuteReadOnlySql() {
    TypedResponse<QueryResultDto> response = analyticsQueryFeignClient.execute(
        new AdHocQueryRequestDto("SELECT 1 AS one", Map.of()));

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().rows().getFirst().getFirst(), equalTo(1));
  }

  @Test
  void shallRejectWriteSql() {
    try {
      analyticsQueryFeignClient.execute(
          new AdHocQueryRequestDto("DELETE FROM athena.view_refresh_log", Map.of()));
      throw new AssertionError("Expected a 400 for write SQL");
    } catch (FeignException response) {
      assertThat(response.status(), equalTo(400));
    }
  }
}
