package org.catools.athena.spec.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "apiSpecFeignClient")
public interface ApiSpecFeignClient {

  @RequestLine("GET /spec?projectCode={projectCode}&name={name}")
  TypedResponse<ApiSpecDto> search(@Param String projectCode, @Param String name);

  @RequestLine("GET /spec/{id}")
  TypedResponse<ApiSpecDto> getById(@Param Long id);

  @RequestLine("POST /spec")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ApiSpecDto apiSpec);

}
