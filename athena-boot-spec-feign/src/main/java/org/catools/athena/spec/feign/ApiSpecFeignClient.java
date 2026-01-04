package org.catools.athena.spec.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    value = "apiSpecFeignClient",
    configuration = OpenFeignConfiguration.class
)
public interface ApiSpecFeignClient {

  @RequestLine("GET /spec?project={project}&name={name}")
  TypedResponse<ApiSpecDto> search(@Param String project, @Param String name);

  @RequestLine("GET /spec/{id}")
  TypedResponse<ApiSpecDto> getById(@Param Long id);

  @RequestLine("POST /spec")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ApiSpecDto apiSpec);

}
