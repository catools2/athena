package org.catools.athena.rest.feign.core.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.core.ProjectDto;

interface ProjectClient {
  @RequestLine("GET /core/project?keyword={keyword}")
  ProjectDto search(
      @Param("keyword")
      String keyword);

  @RequestLine("POST /core/project")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(ProjectDto project);
}
