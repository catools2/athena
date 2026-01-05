package org.catools.athena.rest.feign.tms.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.tms.StatusDto;

interface StatusClient {

  @RequestLine("GET /tms/status?keyword={keyword}")
  StatusDto search(
      @Param("keyword")
      String keyword);

  @RequestLine("POST /tms/status")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(StatusDto status);

}
