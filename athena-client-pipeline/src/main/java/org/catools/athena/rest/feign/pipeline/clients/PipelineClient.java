package org.catools.athena.rest.feign.pipeline.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.model.pipeline.PipelineDto;

import java.time.Instant;

public interface PipelineClient {
  @RequestLine("GET /pipeline/pipeline?name={name}&number={number}&project={project}&version={version}&environment={environment}")
  PipelineDto getPipeline(
      @Param("name")
      String pipelineName,
      @Param("number")
      String pipelineNumber,
      @Param("project")
      String project,
      @Param("version")
      String version,
      @Param("environment")
      String environment);

  @RequestLine("PATCH /pipeline/pipeline?pipelineId={pipelineId}&date={date}")
  PipelineDto updatePipelineEndDate(
      @Param("pipelineId")
      Long pipelineId,
      @Param("date")
      Instant instant);

  @RequestLine("POST /pipeline/pipeline")
  @Headers("Content-Type: application/json")
  Response savePipeline(PipelineDto project);
}
