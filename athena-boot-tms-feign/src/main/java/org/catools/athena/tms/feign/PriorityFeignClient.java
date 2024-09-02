package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.PriorityDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "priorityFeignClient")
public interface PriorityFeignClient {

  @RequestLine("GET /tms/priority?keyword={keyword}")
  TypedResponse<PriorityDto> search(@Param String keyword);

  @RequestLine("GET /tms/priority/{id}")
  TypedResponse<PriorityDto> getById(@Param Long id);

  @RequestLine("POST /tms/priority")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(PriorityDto priority);

}
