package org.catools.athena.git.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.git.model.GitRepositoryDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "gitRepositoryFeignClient")
public interface GitRepositoryFeignClient {

  @RequestLine("GET /repo?keyword={keyword}")
  TypedResponse<GitRepositoryDto> search(@Param String keyword);

  @RequestLine("GET /repo/{id}")
  TypedResponse<GitRepositoryDto> getById(@Param Long id);

  @RequestLine("POST /repo")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(GitRepositoryDto repository);

}
