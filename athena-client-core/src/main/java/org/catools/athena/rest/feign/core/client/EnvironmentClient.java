package org.catools.athena.rest.feign.core.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.core.EnvironmentDto;

interface EnvironmentClient {
  @RequestLine("GET /core/environment?project={project}&keyword={keyword}")
  EnvironmentDto search(
      @Param("project")
      String project,
      @Param("keyword")
      String keyword);

  @RequestLine("POST /core/environment")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(EnvironmentDto environment);
}
