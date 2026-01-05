package org.catools.athena.rest.feign.apispec.client;

import feign.Headers;
import feign.RequestLine;
import org.catools.athena.model.apispec.ApiSpecDto;

public interface ApiSpecClient {

  @RequestLine("POST /oai/spec")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(ApiSpecDto apiSpec);

}
