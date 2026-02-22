package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.tms.TestCycleDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "cycleFeignClient", configuration = OpenFeignConfiguration.class)
public interface TestCycleFeignClient {

  @RequestLine("GET /cycle?keyword={keyword}")
  TypedResponse<TestCycleDto> search(@Param("keyword") String keyword);

  @RequestLine("GET /cycle/{id}")
  TypedResponse<TestCycleDto> getById(@Param("id") Long id);

  @RequestLine("GET /cycleByPattern?name={name}&project={project}&version={version}")
  TypedResponse<TestCycleDto> findLastByPattern(
      @Param("name") String name,
      @Param("project") String project,
      @Param("version") String version);

  @RequestLine("POST /cycle")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(TestCycleDto testCycle);
}
