package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import java.util.Set;
import org.catools.athena.model.tms.StatusTransitionDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "statusTransitionFeignClient")
public interface StatusTransitionFeignClient {

  @RequestLine("GET /transitions?itemCode={itemCode}")
  TypedResponse<Set<StatusTransitionDto>> getAllByItemCode(@Param("itemCode") String itemCode);

  @RequestLine("GET /transition?keyword={keyword}")
  TypedResponse<StatusTransitionDto> search(@Param("keyword") String keyword);

  @RequestLine("GET /transition/{id}")
  TypedResponse<StatusTransitionDto> getById(@Param("id") Long id);

  @RequestLine("POST /transition?itemCode={itemCode}")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(
      @Param("itemCode") String itemCode, StatusTransitionDto statusTransition);
}
