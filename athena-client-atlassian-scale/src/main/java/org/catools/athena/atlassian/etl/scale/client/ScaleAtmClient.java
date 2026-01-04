package org.catools.athena.atlassian.etl.scale.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.atlassian.etl.scale.model.ScalePlanTestRun;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestCase;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestRun;
import org.catools.athena.atlassian.etl.scale.model.ScaleUpdateTestResultRequest;

import java.util.Set;


public interface ScaleAtmClient {
  @RequestLine("GET /testrun/search?startAt={startAt}&maxResults={maxResults}&query={query}")
  Set<ScaleTestRun> searchTestRun(
      @Param("startAt")
      int startAt,
      @Param("maxResults")
      int maxResults,
      @Param("query")
      String query);

  @RequestLine("GET /testrun/search?startAt={startAt}&maxResults={maxResults}&query={query}&fields={fields}")
  Set<ScaleTestRun> searchTestRun(
      @Param("startAt")
      int startAt,
      @Param("maxResults")
      int maxResults,
      @Param("fields")
      String fields,
      @Param("query")
      String query);

  @RequestLine("GET /testcase/search?startAt={startAt}&maxResults={maxResults}&query={query}")
  Set<ScaleTestCase> searchTestCase(
      @Param("startAt")
      int startAt,
      @Param("maxResults")
      int maxResults,
      @Param("query")
      String query);

  @RequestLine("GET /testcase/search?startAt={startAt}&maxResults={maxResults}&query={query}&fields={fields}")
  Set<ScaleTestCase> searchTestCase(
      @Param("startAt")
      int startAt,
      @Param("maxResults")
      int maxResults,
      @Param("fields")
      String fields,
      @Param("query")
      String query);

  @RequestLine("GET /testcase/{key}")
  ScaleTestCase getTestCase(
      @Param("key")
      String key);

  @RequestLine("GET /testrun/{key}")
  ScaleTestRun getTestRun(
      @Param("key")
      String key);

  @RequestLine("POST /testrun")
  @Headers("Content-Type: application/json")
  Response saveTestRun(ScalePlanTestRun planTestRun);

  @RequestLine("POST /testrun/{testRun}/testcase/{testCase}/testresult")
  @Headers("Content-Type: application/json")
  void updateTestResult(
      @Param("testRun")
      String testRun,
      @Param("testCase")
      String testCase, ScaleUpdateTestResultRequest testResult);

}
