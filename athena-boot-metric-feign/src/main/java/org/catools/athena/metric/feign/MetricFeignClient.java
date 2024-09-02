package org.catools.athena.metric.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.metrics.model.MetricDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "metricFeignClient")
public interface MetricFeignClient {

  @RequestLine("GET /metric/{id}")
  TypedResponse<MetricDto> getById(@Param Long id);

  @RequestLine("POST /metric")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(MetricDto metric);

}
