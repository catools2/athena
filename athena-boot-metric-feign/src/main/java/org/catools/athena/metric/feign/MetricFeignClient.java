package org.catools.athena.metric.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.model.metrics.MetricInventoryDto;
import org.catools.athena.model.metrics.MetricSummaryDto;
import org.catools.athena.model.metrics.MetricTrendPointDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value = "metricFeignClient")
public interface MetricFeignClient {

  @RequestLine("GET /metric/summary?project={project}&environment={environment}&actionName={actionName}&actionType={actionType}&actionTarget={actionTarget}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<MetricSummaryDto> getSummary(
      @Param("project") String project,
      @Param("environment") String environment,
      @Param("actionName") String actionName,
      @Param("actionType") String actionType,
      @Param("actionTarget") String actionTarget,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /metric/trend?project={project}&environment={environment}&actionName={actionName}&actionType={actionType}&actionTarget={actionTarget}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<List<MetricTrendPointDto>> getTrend(
      @Param("project") String project,
      @Param("environment") String environment,
      @Param("actionName") String actionName,
      @Param("actionType") String actionType,
      @Param("actionTarget") String actionTarget,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /metric/all?page={page}&size={size}&sort={sort}&direction={direction}&project={project}&environment={environment}&actionName={actionName}&actionType={actionType}&actionTarget={actionTarget}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<MetricInventoryDto>> getAll(
      @Param("page") int page,
      @Param("size") int size,
      @Param("sort") String sort,
      @Param("direction") String direction,
      @Param("project") String project,
      @Param("environment") String environment,
      @Param("actionName") String actionName,
      @Param("actionType") String actionType,
      @Param("actionTarget") String actionTarget);

  @RequestLine("GET /metric/{id}")
  TypedResponse<MetricDto> getById(@Param("id") Long id);

  @RequestLine("POST /metric")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(MetricDto metric);
}
