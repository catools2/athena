package org.catools.athena.rest.feign.core.client;

import feign.Param;
import feign.RequestLine;

import java.util.Set;

interface QueryClient {

  @RequestLine("GET /core/query/record?query={query}")
  Object querySingleResult(
      @Param("query")
      String query);

  @RequestLine("GET /core/query/records?query={query}")
  Set<Object> queryCollection(
      @Param("query")
      String query);

}
