package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.tms.StatusTransitionDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Set;

@FeignClient(value = "statusTransitionFeignClient")
public interface StatusTransitionFeignClient {

  @RequestLine("GET /transitions?itemCode={itemCode}")
  TypedResponse<Set<StatusTransitionDto>> getAllByItemCode(@Param String itemCode);

  @RequestLine("GET /transition?keyword={keyword}")
  TypedResponse<StatusTransitionDto> search(@Param String keyword);

  @RequestLine("GET /transition/{id}")
  TypedResponse<StatusTransitionDto> getById(@Param Long id);

  @RequestLine("POST /transition?itemCode={itemCode}")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(@Param String itemCode, StatusTransitionDto statusTransition);

}
