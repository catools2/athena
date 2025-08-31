package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.StatusDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "statusFeignClient")
public interface StatusFeignClient {

  @RequestLine("GET /status?keyword={keyword}")
  TypedResponse<StatusDto> search(@Param String keyword);

  @RequestLine("GET /status/{id}")
  TypedResponse<StatusDto> getById(@Param Long id);

  @RequestLine("POST /status")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(StatusDto status);

}
