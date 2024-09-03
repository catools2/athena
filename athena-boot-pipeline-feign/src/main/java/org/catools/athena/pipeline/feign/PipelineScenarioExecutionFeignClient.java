package org.catools.athena.pipeline.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pipelineScenarioExecutionFeignClient")
public interface PipelineScenarioExecutionFeignClient {

  @RequestLine("GET /scenario/{id}")
  TypedResponse<PipelineScenarioExecutionDto> getById(@Param Long id);

  @RequestLine("POST /scenario")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(PipelineScenarioExecutionDto scenarioExecution);

}
