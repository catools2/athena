package org.catools.atlassian.zapi.rest.execution;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.zapi.configs.CZApiConfigs;
import org.catools.atlassian.zapi.exception.CZApiException;
import org.catools.atlassian.zapi.model.CZApiCycle;
import org.catools.atlassian.zapi.model.CZApiExecutions;
import org.catools.atlassian.zapi.rest.CZApiRestClient;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.codehaus.jettison.json.JSONObject;

@Slf4j
public class CZApiExecutionClient extends CZApiRestClient {
  public void addTestsToCycle(CSet<String> issueKeys, CZApiCycle cycle, int partitionSize) {
    addTestsToCycle(
        cycle.getProject().getId(),
        cycle.getVersion().getId(),
        cycle.getId(),
        issueKeys,
        partitionSize);
  }

  public void addTestsToCycle(
      Long projectId, Long versionId, Long cycleId, CSet<String> issueKeys, int partitionSize) {
    for (CList<String> keys : issueKeys.partition(partitionSize)) {
      log.trace("Add executions for items: {}", keys);
      JSONObject entity;
      try {
        entity =
            new JSONObject()
                .put("issues", keys)
                .put("method", "1")
                .put("cycleId", cycleId)
                .put("projectId", projectId)
                .put("versionId", versionId);
      } catch (Throwable t) {
        throw new CZApiException("Failed to build JSONObject", t);
      }

      RequestSpecification specification =
          RestAssured.given()
              .baseUri(CZApiConfigs.ZApi.getZApiUri())
              .basePath("/execution/addTestsToCycle")
              .body(entity.toString());
      post(specification);
    }
  }

  public void updateBulkStatus(CZApiExecutions executions, CZApiExecutionStatus status) {
    updateBulkStatus(executions.mapToSet(e -> e.getId()), status);
  }

  public void updateBulkStatus(CSet<Long> executionIds, CZApiExecutionStatus status) {
    updateBulkStatus(executionIds, status, 50);
  }

  public void updateBulkStatus(
      CSet<Long> executionIds, CZApiExecutionStatus status, int partitionSize) {
    try {
      for (CList<Long> ids : executionIds.partition(partitionSize)) {
        JSONObject entity =
            new JSONObject()
                .put("executions", ids)
                .put("status", CZApiConfigs.ZApi.getStatusMap().get(status.name()));
        RequestSpecification specification =
            RestAssured.given()
                .baseUri(CZApiConfigs.ZApi.getZApiUri())
                .basePath("/execution/updateBulkStatus")
                .body(entity.toString());
        Response response = put(specification);
        log.debug(
            "response code:" + response.statusCode() + ", message:" + response.body().asString());
      }
    } catch (Throwable t) {
      throw new CZApiException("Could not update execution statuses", t);
    }
  }
}
