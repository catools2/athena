package org.catools.athena.pipeline.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pipelineExecutionFeignClient")
public interface PipelineExecutionFeignClient {

  @RequestLine("GET /execution/{id}")
  TypedResponse<PipelineExecutionDto> getById(@Param Long id);

  @RequestLine("POST /execution")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(PipelineExecutionDto execution);

}
