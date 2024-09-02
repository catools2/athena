package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.core.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "userFeignClient")
public interface UserFeignClient {

  @RequestLine("GET /user?keyword={keyword}")
  TypedResponse<UserDto> search(@Param String keyword);

  @RequestLine("GET /user/{id}")
  TypedResponse<UserDto> getById(@Param Long id);

  @RequestLine("POST /user")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(UserDto user);

}
