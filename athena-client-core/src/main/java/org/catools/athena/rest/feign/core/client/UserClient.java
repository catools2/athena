package org.catools.athena.rest.feign.core.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.core.UserDto;

interface UserClient {
  @RequestLine("GET /core/user?keyword={keyword}")
  UserDto search(
      @Param("keyword")
      String keyword);

  @RequestLine("POST /core/user")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(UserDto user);
}
