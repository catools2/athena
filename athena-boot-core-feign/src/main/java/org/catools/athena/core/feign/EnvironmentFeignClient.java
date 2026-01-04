package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.core.EnvironmentDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    value = "environmentFeignClient",
    url = "${feign.clients.athena.core.url}",
    configuration = OpenFeignConfiguration.class
)
public interface EnvironmentFeignClient {

  @RequestLine("GET /environment/all?page={page}&size={size}&sort={sort}&direction={direction}&code={code}&name={name}&project={project}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<EnvironmentDto>> getAllEnvironments(
      @Param int page,
      @Param int size,
      @Param String sort,
      @Param String direction,
      @Param(value = "code", expander = QueryExpander.class) String code,
      @Param(value = "name", expander = QueryExpander.class) String name,
      @Param(value = "project", expander = QueryExpander.class) String project
  );

  @RequestLine("GET /environment?keyword={keyword}&project={project}")
  TypedResponse<EnvironmentDto> search(@Param String project, @Param String keyword);

  @RequestLine("GET /environment/{id}")
  TypedResponse<EnvironmentDto> getById(@Param Long id);

  @RequestLine("POST /environment")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(EnvironmentDto environment);

  @RequestLine("PUT /environment")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> update(EnvironmentDto environment);

}
