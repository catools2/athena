package org.catools.athena.pipeline.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.util.Set;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pipelineExecutionStatusFeignClient")
public interface PipelineExecutionStatusFeignClient {

  @RequestLine("GET /execution_statuses")
  TypedResponse<Set<PipelineExecutionStatusDto>> getAll();

  @RequestLine("GET /execution_status?name={name}")
  TypedResponse<PipelineExecutionStatusDto> getByName(@Param("name") String name);

  @RequestLine("GET /execution_status/{id}")
  TypedResponse<PipelineExecutionStatusDto> getById(@Param("id") Long id);

  @RequestLine("POST /execution_status")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(PipelineExecutionStatusDto executionStatus);
}
