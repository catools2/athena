package org.catools.athena.rest.feign.tms.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.tms.PriorityDto;

interface PriorityClient {

  @RequestLine("GET /tms/priority?keyword={keyword}")
  PriorityDto search(
      @Param("keyword")
      String keyword);

  @RequestLine("POST /tms/priority")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(PriorityDto priority);

}
