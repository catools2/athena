package org.catools.athena.spec.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.apispec.ApiSpecDriftDto;
import org.catools.athena.model.apispec.ApiSpecFreshnessDto;
import org.catools.athena.model.apispec.ApiSpecInventoryDto;
import org.catools.athena.model.apispec.ApiSpecSummaryDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "apiSpecFeignClient", configuration = OpenFeignConfiguration.class)
public interface ApiSpecFeignClient {

  @RequestLine("GET /spec/freshness?project={project}&name={name}&title={title}&version={version}&freshness={freshness}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<ApiSpecFreshnessDto> getFreshness(
      @Param("project") String project,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version,
      @Param("freshness") String freshness,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /spec/drift?page={page}&size={size}&sort={sort}&direction={direction}&project={project}&name={name}&title={title}&version={version}&freshness={freshness}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<ApiSpecDriftDto>> getDrift(
      @Param("page") int page,
      @Param("size") int size,
      @Param("sort") String sort,
      @Param("direction") String direction,
      @Param("project") String project,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version,
      @Param("freshness") String freshness);

  @RequestLine("GET /spec/summary?project={project}&name={name}&title={title}&version={version}&freshness={freshness}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<ApiSpecSummaryDto> getSummary(
      @Param("project") String project,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version,
      @Param("freshness") String freshness,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /spec/all?page={page}&size={size}&sort={sort}&direction={direction}&project={project}&name={name}&title={title}&version={version}&freshness={freshness}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<ApiSpecInventoryDto>> getAll(
      @Param("page") int page,
      @Param("size") int size,
      @Param("sort") String sort,
      @Param("direction") String direction,
      @Param("project") String project,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version,
      @Param("freshness") String freshness);

  @RequestLine("GET /spec?project={project}&name={name}")
  TypedResponse<ApiSpecDto> search(@Param("project") String project, @Param("name") String name);

  @RequestLine("GET /spec/{id}")
  TypedResponse<ApiSpecDto> getById(@Param("id") Long id);

  @RequestLine("POST /spec")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ApiSpecDto apiSpec);
}
