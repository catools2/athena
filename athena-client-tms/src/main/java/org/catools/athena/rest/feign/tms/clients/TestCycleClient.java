package org.catools.athena.rest.feign.tms.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.tms.TestCycleDto;

import java.util.Map;

interface TestCycleClient {

  @RequestLine("GET /tms/cycle/{code}/sha256")
  Map<String, String> getSHA256(
      @Param("code")
      String code);

  @RequestLine("GET /tms/cycle?keyword={keyword}")
  TestCycleDto findByCode(
      @Param("keyword")
      String keyword);

  @RequestLine("GET /tms/cycles?name={name}&versionCode={versionCode}")
  TestCycleDto findLastByPattern(
      @Param("name")
      String name,
      @Param("versionCode")
      String versionCode);

  @RequestLine("POST /tms/cycle")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(TestCycleDto cycle);

}
