package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.core.UserDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    value = "userFeignClient",
    url = "${feign.clients.athena.core.url}",
    configuration = OpenFeignConfiguration.class
)
public interface UserFeignClient {

  @RequestLine("GET /user/all?page={page}&size={size}&sort={sort}&direction={direction}&username={username}&alias={alias}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<UserDto>> getAllUsers(
      @Param int page,
      @Param int size,
      @Param String sort,
      @Param String direction,
      @Param(value = "username", expander = QueryExpander.class) String username,
      @Param(value = "alias", expander = QueryExpander.class) String alias
  );

  @RequestLine("GET /user?keyword={keyword}")
  TypedResponse<UserDto> search(@Param String keyword);

  @RequestLine("GET /user/{id}")
  TypedResponse<UserDto> getById(@Param Long id);

  @RequestLine("POST /user")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(UserDto user);

  @RequestLine("PUT /user")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> update(UserDto user);

}
