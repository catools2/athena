package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.tms.PriorityDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "priorityFeignClient")
public interface PriorityFeignClient {

  @RequestLine("GET /priority?keyword={keyword}")
  TypedResponse<PriorityDto> search(@Param String keyword);

  @RequestLine("GET /priority/{id}")
  TypedResponse<PriorityDto> getById(@Param Long id);

  @RequestLine("POST /priority")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(PriorityDto priority);

}
