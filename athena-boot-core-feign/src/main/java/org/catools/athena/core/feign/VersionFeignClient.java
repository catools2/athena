package org.catools.athena.core.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.core.VersionDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    value = "versionFeignClient",
    url = "${feign.clients.athena.core.url}",
    configuration = OpenFeignConfiguration.class
)
public interface VersionFeignClient {

  @RequestLine("GET /version/all?page={page}&size={size}&sort={sort}&direction={direction}&code={code}&name={name}&project={project}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<VersionDto>> getAllVersions(
      @Param int page,
      @Param int size,
      @Param String sort,
      @Param String direction,
      @Param(value = "code", expander = QueryExpander.class) String code,
      @Param(value = "name", expander = QueryExpander.class) String name,
      @Param(value = "project", expander = QueryExpander.class) String project
  );

  @RequestLine("GET /version?keyword={keyword}&project={project}")
  TypedResponse<VersionDto> search(@Param String project, @Param String keyword);

  @RequestLine("GET /version/{id}")
  TypedResponse<VersionDto> getById(@Param Long id);

  @RequestLine("POST /version")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> save(VersionDto version);

  @RequestLine("PUT /version")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> update(VersionDto version);

}
