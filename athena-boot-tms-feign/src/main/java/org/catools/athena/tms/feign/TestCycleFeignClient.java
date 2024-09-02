package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.TestCycleDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "cycleFeignClient")
public interface TestCycleFeignClient {

  @RequestLine("GET /tms/cycle?keyword={keyword}")
  TypedResponse<TestCycleDto> search(@Param String keyword);

  @RequestLine("GET /tms/cycle/{id}")
  TypedResponse<TestCycleDto> getById(@Param Long id);

  @RequestLine("GET /tms/cycleByPattern?name={name}&versionCode={versionCode}")
  TypedResponse<TestCycleDto> findLastByPattern(@Param String name, @Param String versionCode);

  @RequestLine("POST /tms/cycle")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(TestCycleDto testCycle);

}
