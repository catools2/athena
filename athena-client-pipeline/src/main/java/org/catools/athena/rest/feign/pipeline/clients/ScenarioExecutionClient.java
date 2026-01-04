package org.catools.athena.rest.feign.pipeline.clients;

import feign.Headers;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.model.pipeline.PipelineScenarioExecutionDto;

public interface ScenarioExecutionClient {
  @RequestLine("POST /pipeline/scenario")
  @Headers("Content-Type: application/json")
  Response saveScenarioExecution(PipelineScenarioExecutionDto execution);

}
