package org.catools.atlassian.scale.rest.cycle;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.atlassian.scale.configs.CZScaleConfigs;
import org.catools.atlassian.scale.model.*;
import org.catools.atlassian.scale.rest.CZScaleRestClient;
import org.catools.common.collections.CList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.function.Consumer;

@Slf4j
public class CZScaleTestRunClient extends CZScaleRestClient {

  public CZScaleTestRunClient() {
    super();
  }

  public CZScaleTestRuns getAllTestRuns(String projectKey, String folder, String fields) {
    return getAllTestRuns(projectKey, folder, fields, 1, 1, null);
  }

  public CZScaleTestRuns getAllTestRuns(String projectKey,
                                        String folder,
                                        String fields,
                                        int parallelInputCount,
                                        int parallelOutputCount,
                                        Consumer<CZScaleTestRun> onAction) {
    Set<CZScaleTestRun> results = readAllInParallel(
        "Get Test Runs",
        parallelInputCount,
        parallelOutputCount,
        (start, max) -> _getAllTestRuns(projectKey, folder, fields, start, max),
        onAction);
    return new CZScaleTestRuns(results);
  }

  private CZScaleTestRuns _getAllTestRuns(String projectKey, String folder, String fields, int startAt, int maxResults) {
    log.debug("All Test Runs, projectKey: {}, fields: {}, startAT: {}, maxResult: {}", projectKey, fields, startAt, maxResults);
    RequestSpecification specification =
        getRequestSpecification("/testrun/search")
            .queryParam("startAt", startAt)
            .queryParam("maxResults", maxResults)
            .queryParam("query", String.format("projectKey = \"%s\" AND folder = \"%s\"", projectKey, folder));

    if (StringUtils.isNotEmpty(fields)) {
      specification.queryParam("fields", fields);
    }

    Response response = get(specification);

    if (response.statusCode() != 200) {
      log.warn("Response::\n{}", response.body().asString());
    }

    response.then().statusCode(200);
    return response.body().as(CZScaleTestRuns.class);
  }

  public CZScaleTestRun getTestRun(String testRunKey) {
    RequestSpecification specification =
        getRequestSpecification("/testrun/" + testRunKey);

    Response response = get(specification);

    if (response.statusCode() != 200)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(200);
    return response.body().as(CZScaleTestRun.class);
  }

  public CZScaleTestResults getTestResults(String testRunKey) {
    return getTestResults(testRunKey, StringUtils.EMPTY, 1, 1, null);
  }

  public CZScaleTestResults getTestResults(String testRunKey,
                                           String fields,
                                           int parallelInputCount,
                                           int parallelOutputCount,
                                           Consumer<CZScaleTestResult> onAction) {
    Set<CZScaleTestResult> get_test_results = readAllInParallel(
        "Get Test Results",
        parallelInputCount,
        parallelOutputCount,
        (start, max) -> _getTestResults(testRunKey, fields, start, max),
        onAction);
    return new CZScaleTestResults(get_test_results);
  }

  private CZScaleTestResults _getTestResults(String testRunKey, String fields, int startAt, int maxResults) {
    RequestSpecification specification =
        getRequestSpecification("/testrun/" + testRunKey + "/testresults")
            .queryParam("startAt", startAt)
            .queryParam("maxResults", maxResults);

    if (StringUtils.isNotEmpty(fields)) {
      specification.queryParam("fields", fields);
    }

    Response response = get(specification);

    if (response.statusCode() != 200)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(200);
    return response.body().as(CZScaleTestResults.class);
  }

  public Long createTestResult(String testRunKey, CZScaleTestResult testResult) {
    return createTestResults(testRunKey, new CZScaleTestResults(testResult)).get(0);
  }

  public CList<Long> createTestResults(String testRunKey, CZScaleTestResults testResults) {
    RequestSpecification specification =
        getRequestSpecification("/testrun/" + testRunKey + "/testresults")
            .body(testResults);
    Response response = post(specification);

    if (response.statusCode() != 201)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(201);
    return CList.of(new JSONArray(response.body().asString()))
        .mapToList(o -> ((JSONObject) o).getLong("id"));
  }

  public long updateTestResult(String testRunKey, String testCaseKey, CZScaleUpdateTestResultRequest testResult) {
    RequestSpecification specification =
        getRequestSpecification("/testrun/" + testRunKey + "/testcase/" + testCaseKey + "/testresult")
            .body(testResult);

    Response response = put(specification);

    if (response.statusCode() != 200)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(200);
    return new JSONObject(response.body().asString()).getLong("id");
  }

  public void deleteTestRun(String testRunKey) {
    RequestSpecification specification =
        getRequestSpecification("/testrun/" + testRunKey);

    Response response = delete(specification);

    if (response.statusCode() != 204)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(204);
  }

  public String createTestRun(CZScalePlanTestRun planTestRun) {
    RequestSpecification specification =
        getRequestSpecification("/testrun")
            .body(planTestRun);

    Response response = post(specification);

    if (response.statusCode() != 201)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(201);
    String testRunKey = response.body().jsonPath().get("key");

    CList<String> createdTestCases = getTestRun(testRunKey).getItems().mapToList(CZScaleTestExecution::getTestCaseKey);
    for (CZScalePlanExecution item : planTestRun.getItems()) {
      if (createdTestCases.contains(item.getTestCaseKey())) continue;
      CZScaleTestResult testResultToAdd = convertPlanExecutionToTestResult(item);
      createTestResult(testRunKey, testResultToAdd);
    }

    return testRunKey;
  }

  private static RequestSpecification getRequestSpecification(String path) {
    return RestAssured.given()
        .baseUri(CZScaleConfigs.Scale.getAtmUri())
        .basePath(path);
  }

  private static CZScaleTestResult convertPlanExecutionToTestResult(CZScalePlanExecution item) {
    CZScaleTestResult testResultToAdd = new CZScaleTestResult().setTestCaseKey(item.getTestCaseKey())
        .setStatus(item.getStatus())
        .setEnvironment(item.getEnvironment())
        .setComment(item.getComment())
        .setUserKey(item.getUserKey())
        .setExecutionDate(item.getExecutionDate())
        .setCustomFields(item.getCustomFields())
        .setScriptResults(item.getScriptResults());
    return testResultToAdd;
  }
}
