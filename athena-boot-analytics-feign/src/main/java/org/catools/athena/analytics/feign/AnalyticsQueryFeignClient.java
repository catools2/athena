package org.catools.athena.analytics.feign;

import feign.Headers;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.analytics.AdHocQueryRequestDto;
import org.catools.athena.model.analytics.QueryResultDto;
import org.springframework.cloud.openfeign.FeignClient;

/** The direct, read-only analytics SQL contract. */
@FeignClient(value = "analyticsQueryFeignClient", configuration = OpenFeignConfiguration.class)
public interface AnalyticsQueryFeignClient {

  /** Run one caller-built SELECT/WITH statement. */
  @RequestLine("POST /queries/execute")
  @Headers("Content-Type: application/json")
  TypedResponse<QueryResultDto> execute(AdHocQueryRequestDto request);
}
