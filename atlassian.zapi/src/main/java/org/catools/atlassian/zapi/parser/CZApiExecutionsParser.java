package org.catools.atlassian.zapi.parser;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.zapi.exception.CZApiClientException;
import org.catools.atlassian.zapi.model.CZApiExecution;
import org.catools.atlassian.zapi.model.CZApiExecutionDefects;
import org.catools.atlassian.zapi.model.CZApiExecutions;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CStringUtil;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class CZApiExecutionsParser extends CZApiBaseParser {

  public static CZApiExecutions parse(Response response) {
    if (CStringUtil.isBlank(response.body().asString())) {
      return new CZApiExecutions();
    }
    return parse(new JSONObject(response.body().asString()));
  }

  public static CZApiExecutions parse(JSONObject input) {
    CZApiExecutions output = new CZApiExecutions();

    try {
      JSONObject status = input.optJSONObject("status");
      JSONArray executions = input.optJSONArray("executions");

      for (Object o : executions) {
        JSONObject json = (JSONObject) o;
        CZApiExecution execution = new CZApiExecution();
        execution.setId(json.optLong("id"));
        execution.setOrderId(json.optLong("orderId"));
        if (status == null) {
          execution.setExecutionStatus(json.getJSONObject("status").getString("name"));
        } else {
          execution.setExecutionStatus(
              status.getJSONObject(json.getString("executionStatus")).getString("name"));
        }
        execution.setExecutedOn(getDate(json, "executedOn"));
        execution.setExecutedByUserName(json.optString("executedByUserName"));
        execution.setComment(json.optString("comment"));
        execution.setCycleId(json.optLong("cycleId"));
        execution.setCycleName(json.optString("cycleName"));
        execution.setVersionName(json.optString("versionName"));
        execution.setProjectKey(json.optString("projectKey"));
        execution.setProjectName(json.optString("project"));
        execution.setCreatedOn(getDate(json, "creationDate"));
        execution.setIssueId(json.optLong("issueId"));
        execution.setIssueKey(json.optString("issueKey"));
        execution.setExecutionDefectCount(json.optLong("executionDefectCount"));
        execution.setStepDefectCount(json.optLong("stepDefectCount"));
        execution.setTotalDefectCount(json.optLong("totalDefectCount"));

        if (json.has("executionDefects")) {
          execution.setExecutionDefects(
              CJsonUtil.read(json.optString("executionDefects"), CZApiExecutionDefects.class));
        }

        if (output.contains(execution)) {
          log.warn(
              "Duplicated execution identified during execution records parsing. {} ", execution);
          continue;
        }

        output.add(execution);
      }

      if (output.size() != executions.length()) {
        log.warn("{} execution records parsed to {} records", executions.length(), output.size());
      }
    } catch (Throwable t) {
      throw new CZApiClientException(
          "Could not parse input to JSON Array. input: " + input.toString(), t);
    }
    return output;
  }
}
