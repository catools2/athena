package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.TestExecutionDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Set;

@FeignClient(value = "testExecutionFeignClient")
public interface TestExecutionFeignClient {

  @RequestLine("GET /tms/executions?itemCode={itemCode}&cycleCode={cycleCode}")
  TypedResponse<Set<TestExecutionDto>> getAll(@Param String itemCode, @Param String cycleCode);

  @RequestLine("GET /tms/execution/{id}")
  TypedResponse<TestExecutionDto> getById(@Param Long id);

  @RequestLine("POST /tms/execution?cycleCode={cycleCode}")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(@Param String cycleCode, TestExecutionDto testExecution);

}
