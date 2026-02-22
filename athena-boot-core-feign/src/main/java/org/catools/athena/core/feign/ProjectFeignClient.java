package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.core.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    value = "projectFeignClient",
    url = "${feign.clients.athena.core.url}",
    configuration = OpenFeignConfiguration.class)
public interface ProjectFeignClient {

  @RequestLine(
      "GET /project/all?page={page}&size={size}&sort={sort}&direction={direction}&code={code}&name={name}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<ProjectDto>> getAllProjects(
      @Param("page") int page,
      @Param("size") int size,
      @Param("sort") String sort,
      @Param("direction") String direction,
      @Param("code") String code,
      @Param("name") String name);

  @RequestLine("GET /project?keyword={keyword}")
  @Headers("Accept: application/json")
  TypedResponse<ProjectDto> search(@Param("keyword") String keyword);

  @RequestLine("GET /project/{id}")
  @Headers("Accept: application/json")
  TypedResponse<ProjectDto> getById(@Param("id") Long id);

  @RequestLine("POST /project")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(ProjectDto project);

  @RequestLine("PUT /project")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> update(ProjectDto project);
}
