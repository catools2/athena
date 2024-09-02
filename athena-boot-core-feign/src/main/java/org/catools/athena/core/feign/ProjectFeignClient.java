package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.core.model.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "projectFeignClient")
public interface ProjectFeignClient {

  @RequestLine("GET /project?keyword={keyword}")
  TypedResponse<ProjectDto> search(@Param String keyword);

  @RequestLine("GET /project/{id}")
  TypedResponse<ProjectDto> getById(@Param Long id);

  @RequestLine("POST /project")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ProjectDto project);

}
