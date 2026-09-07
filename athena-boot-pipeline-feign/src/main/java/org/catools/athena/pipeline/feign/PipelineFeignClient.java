package org.catools.athena.pipeline.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.time.Instant;
import java.util.List;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineInventoryDto;
import org.catools.athena.model.pipeline.PipelineSummaryDto;
import org.catools.athena.model.pipeline.PipelineTrendPointDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pipelineFeignClient", configuration = OpenFeignConfiguration.class)
public interface PipelineFeignClient {

  @RequestLine("GET /pipeline/summary?project={project}&version={version}&environment={environment}&name={name}&number={number}&state={state}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<PipelineSummaryDto> getSummary(
      @Param("project") String project,
      @Param("version") String version,
      @Param("environment") String environment,
      @Param("name") String name,
      @Param("number") String number,
      @Param("state") String state,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /pipeline/trend?project={project}&version={version}&environment={environment}&name={name}&number={number}&state={state}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<List<PipelineTrendPointDto>> getTrend(
      @Param("project") String project,
      @Param("version") String version,
      @Param("environment") String environment,
      @Param("name") String name,
      @Param("number") String number,
      @Param("state") String state,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /pipeline/all?page={page}&size={size}&sort={sort}&direction={direction}&project={project}&version={version}&environment={environment}&name={name}&number={number}&state={state}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<PipelineInventoryDto>> getAll(
      @Param("page") int page,
      @Param("size") int size,
      @Param("sort") String sort,
      @Param("direction") String direction,
      @Param("project") String project,
      @Param("version") String version,
      @Param("environment") String environment,
      @Param("name") String name,
    @Param("number") String number,
    @Param("state") String state);

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
