package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.util.List;
import java.util.Set;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.model.tms.TestExecutionInventoryDto;
import org.catools.athena.model.tms.TestExecutionSummaryDto;
import org.catools.athena.model.tms.TestExecutionTrendPointDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "testExecutionFeignClient")
public interface TestExecutionFeignClient {

  @RequestLine("GET /tms/summary?project={project}&version={version}&cycleCode={cycleCode}&itemCode={itemCode}&status={status}&progress={progress}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<TestExecutionSummaryDto> getSummary(
      @Param("project") String project,
      @Param("version") String version,
      @Param("cycleCode") String cycleCode,
      @Param("itemCode") String itemCode,
      @Param("status") String status,
      @Param("progress") String progress,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /tms/trend?project={project}&version={version}&cycleCode={cycleCode}&itemCode={itemCode}&status={status}&progress={progress}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<List<TestExecutionTrendPointDto>> getTrend(
      @Param("project") String project,
      @Param("version") String version,
      @Param("cycleCode") String cycleCode,
      @Param("itemCode") String itemCode,
      @Param("status") String status,
      @Param("progress") String progress,
      @Param("windowDays") Integer windowDays);

  @RequestLine("GET /tms/all?page={page}&size={size}&sort={sort}&direction={direction}&project={project}&version={version}&cycleCode={cycleCode}&itemCode={itemCode}&status={status}&progress={progress}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<TestExecutionInventoryDto>> getAllPaged(
      @Param("page") int page,
      @Param("size") int size,
      @Param("sort") String sort,
      @Param("direction") String direction,
      @Param("project") String project,
      @Param("version") String version,
      @Param("cycleCode") String cycleCode,
      @Param("itemCode") String itemCode,
    @Param("status") String status,
    @Param("progress") String progress);

  @RequestLine("GET /executions?itemCode={itemCode}&cycleCode={cycleCode}")
  TypedResponse<Set<TestExecutionDto>> getAll(
      @Param("itemCode") String itemCode, @Param("cycleCode") String cycleCode);

  @RequestLine("GET /execution/{id}")
  TypedResponse<TestExecutionDto> getById(@Param("id") Long id);

  @RequestLine("POST /execution?cycleCode={cycleCode}")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(
      @Param("cycleCode") String cycleCode, TestExecutionDto testExecution);
}
