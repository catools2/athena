package org.catools.athena.git.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.git.CommitDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "commitFeignClient")
public interface CommitFeignClient {

  @RequestLine("GET /commit?hash={hash}")
  TypedResponse<CommitDto> search(@Param String hash);

  @RequestLine("GET /commit/{id}")
  TypedResponse<CommitDto> getById(@Param Long id);

  @RequestLine("POST /commit")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(CommitDto commit);

}
