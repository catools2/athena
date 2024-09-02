package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.core.model.VersionDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "versionFeignClient")
public interface VersionFeignClient {

  @RequestLine("GET /version?keyword={keyword}")
  TypedResponse<VersionDto> search(@Param String keyword);

  @RequestLine("GET /version/{id}")
  TypedResponse<VersionDto> getById(@Param Long id);

  @RequestLine("POST /version")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(VersionDto version);

}
