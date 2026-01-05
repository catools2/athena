package org.catools.athena.pipeline.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.pipeline.PipelineDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.time.Instant;

@FeignClient(
    value = "pipelineFeignClient",
    configuration = OpenFeignConfiguration.class
)
public interface PipelineFeignClient {

  @RequestLine("GET /pipeline?name={name}&number={number}&project={project}&version={version}&environment={environment}")
  TypedResponse<PipelineDto> getLastPipeline(@Param String name, @Param String number, @Param String project, @Param String version, @Param String environment);

  @RequestLine("GET /pipeline/{id}")
  TypedResponse<PipelineDto> getById(@Param Long id);

  @RequestLine("PUT /pipeline?pipelineId={pipelineId}&date={date}")
  TypedResponse<PipelineDto> updateEndDate(@Param Long pipelineId, @Param Instant date);

  @RequestLine("POST /pipeline")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(PipelineDto pipeline);

}
