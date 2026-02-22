package org.catools.athena.pipeline.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.time.Instant;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.pipeline.PipelineDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pipelineFeignClient", configuration = OpenFeignConfiguration.class)
public interface PipelineFeignClient {

  @RequestLine(
      "GET /pipeline?name={name}&number={number}&project={project}&version={version}&environment={environment}")
  TypedResponse<PipelineDto> getLastPipeline(
      @Param("name") String name,
      @Param("number") String number,
      @Param("project") String project,
      @Param("version") String version,
      @Param("environment") String environment);

  @RequestLine("GET /pipeline/{id}")
  TypedResponse<PipelineDto> getById(@Param("id") Long id);

  @RequestLine("PUT /pipeline?pipelineId={pipelineId}&date={date}")
  TypedResponse<PipelineDto> updateEndDate(
      @Param("pipelineId") Long pipelineId, @Param("date") Instant date);

  @RequestLine("POST /pipeline")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(PipelineDto pipeline);
}
