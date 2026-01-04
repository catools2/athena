package org.catools.athena.rest.feign.core.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.core.VersionDto;

interface VersionClient {

  @RequestLine("GET /core/version?project={project}&keyword={keyword}")
  VersionDto search(
      @Param("project")
      String project,
      @Param("keyword")
      String keyword);

  @RequestLine("POST /core/version")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(VersionDto version);

}
