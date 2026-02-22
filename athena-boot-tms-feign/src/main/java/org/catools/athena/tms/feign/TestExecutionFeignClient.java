package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.util.Set;
import org.catools.athena.model.tms.TestExecutionDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "testExecutionFeignClient")
public interface TestExecutionFeignClient {

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
