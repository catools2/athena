package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.core.model.EnvironmentDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "environmentFeignClient")
public interface EnvironmentFeignClient {

  @RequestLine("GET /environment?keyword={keyword}")
  TypedResponse<EnvironmentDto> search(@Param String keyword);

  @RequestLine("GET /environment/{id}")
  TypedResponse<EnvironmentDto> getById(@Param Long id);

  @RequestLine("POST /environment")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(EnvironmentDto environment);

}
