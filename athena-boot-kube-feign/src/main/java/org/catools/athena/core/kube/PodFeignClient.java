package org.catools.athena.core.kube;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.util.List;
import java.util.Set;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.model.kube.PodInventoryDto;
import org.catools.athena.model.kube.PodSummaryDto;
import org.catools.athena.model.kube.PodTrendPointDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "podFeignClient", configuration = OpenFeignConfiguration.class)
public interface PodFeignClient {

  @RequestLine("GET /summary?project={project}&namespace={namespace}&name={name}&nodeName={nodeName}&status={status}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<PodSummaryDto> getSummary(
    @Param("project") String project,
    @Param("namespace") String namespace,
    @Param("name") String name,
    @Param("nodeName") String nodeName,
    @Param("status") String status,
    @Param("windowDays") Integer windowDays);

  @RequestLine("GET /trend?project={project}&namespace={namespace}&name={name}&nodeName={nodeName}&status={status}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<List<PodTrendPointDto>> getTrend(
    @Param("project") String project,
    @Param("namespace") String namespace,
    @Param("name") String name,
    @Param("nodeName") String nodeName,
    @Param("status") String status,
    @Param("windowDays") Integer windowDays);

  @RequestLine("GET /all?page={page}&size={size}&sort={sort}&direction={direction}&project={project}&namespace={namespace}&name={name}&nodeName={nodeName}&status={status}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<PodInventoryDto>> getAllPaged(
    @Param("page") int page,
    @Param("size") int size,
    @Param("sort") String sort,
    @Param("direction") String direction,
    @Param("project") String project,
    @Param("namespace") String namespace,
    @Param("name") String name,
    @Param("nodeName") String nodeName,
    @Param("status") String status);

  @RequestLine("GET /pods?project={project}&namespace={namespace}")
  TypedResponse<Set<PodDto>> getAll(
      @Param("project") String project, @Param("namespace") String namespace);

  @RequestLine("GET /pod?name={name}&namespace={namespace}")
  TypedResponse<PodDto> getByNameAndNamespace(
      @Param("name") String name, @Param("namespace") String namespace);

  @RequestLine("GET /pod/{id}")
  TypedResponse<PodDto> getById(@Param("id") Long id);

  @RequestLine("POST /pod")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(PodDto pod);
}
