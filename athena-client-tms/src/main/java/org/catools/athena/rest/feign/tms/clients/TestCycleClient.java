package org.catools.athena.rest.feign.tms.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.Map;
import org.catools.athena.model.tms.TestCycleDto;

interface TestCycleClient {

  @RequestLine("GET /tms/cycle/{code}/sha256")
  Map<String, String> getSHA256(@Param("code") String code);

  @RequestLine("GET /tms/cycle?keyword={keyword}")
  TestCycleDto findByCode(@Param("keyword") String keyword);

  @RequestLine("GET /tms/cycleByPattern?name={name}&project={project}&version={version}")
  TestCycleDto findLastByPattern(
      @Param("name") String name,
      @Param("project") String projectCode,
      @Param("version") String versionCode);

  @RequestLine("POST /tms/cycle")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(TestCycleDto cycle);
}
