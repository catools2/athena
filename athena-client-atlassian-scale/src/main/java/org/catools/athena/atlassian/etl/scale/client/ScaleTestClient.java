package org.catools.athena.atlassian.etl.scale.client;

import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.atlassian.etl.scale.model.ScaleChangeHistory;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestResult;

import java.util.HashSet;


public interface ScaleTestClient {
  @RequestLine("GET /testcase/{id}/history")
  HashSet<ScaleChangeHistory> getTestCaseHistory(
      @Param("id")
      int id);

  @RequestLine("GET /testcase/{key}/allVersions?fields={fields}")
  Response getTestCaseAllVersions(
      @Param("key")
      String key,
      @Param("fields")
      String fields);

  @RequestLine("GET /testrun/{key}/testresults?startAt={startAt}&maxResults={maxResults}")
  HashSet<ScaleTestResult> getTestResults(
      @Param("startAt")
      int startAt,
      @Param("maxResults")
      int maxResults);

}
