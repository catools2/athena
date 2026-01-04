package org.catools.athena.rest.feign.pipeline.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;

public interface ExecutionStatusClient {

  @RequestLine("GET /pipeline/execution_status?name={name}")
  PipelineExecutionStatusDto getExecutionStatus(
      @Param("name")
      String name);

  @RequestLine("POST /pipeline/execution_status")
  @Headers("Content-Type: application/json")
  Response saveExecutionStatus(PipelineExecutionStatusDto pipelineExecutionStatusDto);
}
