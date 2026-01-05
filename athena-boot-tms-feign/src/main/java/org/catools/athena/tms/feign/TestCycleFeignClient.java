package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.tms.TestCycleDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    value = "cycleFeignClient",
    configuration = OpenFeignConfiguration.class
)
public interface TestCycleFeignClient {

  @RequestLine("GET /cycle?keyword={keyword}")
  TypedResponse<TestCycleDto> search(@Param String keyword);

  @RequestLine("GET /cycle/{id}")
  TypedResponse<TestCycleDto> getById(@Param Long id);

  @RequestLine("GET /cycleByPattern?name={name}&project={project}&version={version}")
  TypedResponse<TestCycleDto> findLastByPattern(@Param String name, @Param String project, @Param String version);

  @RequestLine("POST /cycle")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(TestCycleDto testCycle);

}
