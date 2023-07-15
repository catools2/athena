package org.catools.atlassian.zapi.parser;

import io.restassured.response.Response;
import org.catools.atlassian.zapi.exception.CZApiClientException;
import org.catools.atlassian.zapi.model.*;
import org.catools.common.utils.CStringUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class CZApiCyclesParser extends CZApiBaseParser {

  public static CZApiCycles parse(CZApiProject project, Response response) {
    CZApiCycles output = new CZApiCycles();
    org.json.JSONObject input;

    try {
      input = new JSONObject(response.body().asString());
      JSONArray names = input.names();
      for (int i = 0; i < names.length(); i++) {
        String key = names.getString(i);
        if (CStringUtil.equalsIgnoreCase(key, "recordsCount")) {
          continue;
        }

        JSONObject json = input.getJSONObject(key);
        if (project.getId() == json.optLong("projectId")) {
          output.add(parseCzApiCycle(project.setId(json.optLong("projectId")), key, json));
        }
      }
    } catch (Throwable t) {
      throw new CZApiClientException(
          "Could not parse input to JSON Array. input: " + response.body().asString(), t);
    }
    return output;
  }

  public static CZApiCycles parse(CZApiProjects projects, Response response) {
    CZApiCycles output = new CZApiCycles();
    JSONObject input;

    try {
      input = new JSONObject(response.body().asString());
      JSONArray names = input.names();
      for (int i = 0; i < names.length(); i++) {
        String key = names.getString(i);
        if (CStringUtil.equalsIgnoreCase(key, "recordsCount")) {
          continue;
        }

        JSONObject json = input.getJSONObject(key);
        output.add(parseCzApiCycle(projects.getById(json.optLong("projectId")), key, json));
      }
    } catch (Throwable t) {
      throw new CZApiClientException(
          "Could not parse input to JSON Array. input: " + response.body().asString(), t);
    }
    return output;
  }

  private static CZApiCycle parseCzApiCycle(CZApiProject project, String key, JSONObject json) {
    CZApiCycle cycle = new CZApiCycle();
    cycle.setId(Long.valueOf(key));
    cycle.setProject(project);
    cycle.setVersion(new CZApiVersion(json.optLong("versionId"), json.optString("versionName")));
    cycle.setDescription(json.optString("description"));
    cycle.setStartDate(getDate(json, "startDate"));
    cycle.setEndDate(getDate(json, "endDate"));
    cycle.setEnvironment(json.optString("environment"));
    cycle.setBuild(json.optString("build"));
    cycle.setName(json.optString("name"));
    cycle.setModifiedBy(CStringUtil.removeEnd(json.optString("modifiedBy"), "(Inactive)"));
    return cycle;
  }
}
