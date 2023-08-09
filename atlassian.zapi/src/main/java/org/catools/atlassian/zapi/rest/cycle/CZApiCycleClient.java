package org.catools.atlassian.zapi.rest.cycle;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.catools.atlassian.zapi.CZApiClient;
import org.catools.atlassian.zapi.configs.CZApiConfigs;
import org.catools.atlassian.zapi.exception.CZApiException;
import org.catools.atlassian.zapi.model.*;
import org.catools.atlassian.zapi.parser.CZApiCycleParser;
import org.catools.atlassian.zapi.parser.CZApiCyclesParser;
import org.catools.atlassian.zapi.rest.CZApiRestClient;
import org.catools.common.extensions.verify.CVerify;
import org.codehaus.jettison.json.JSONObject;

public class CZApiCycleClient extends CZApiRestClient {

  public CZApiCycleClient() {
    super();
  }

  public CZApiExecutions getExecutions(CZApiCycle cycle) {
    return CZApiClient.Search.getExecutions(
        String.format(
            "project = \"%s\" AND fixVersion = \"%s\" AND cycleName = \"%s\"",
            cycle.getProject().getName(), cycle.getVersion().getName(), cycle.getName()));
  }

  public CZApiCycles getAllCycle(CZApiProject project, CZApiVersion version) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZApiConfigs.ZApi.getZApiUri())
            .basePath("/cycle")
            .queryParam("projectId", project.getId())
            .queryParam("versionId", version.getId());
    return CZApiCyclesParser.parse(project, get(specification));
  }

  public CZApiCycle getCycleById(long cycleId) {
    CZApiProjects projects = CZApiClient.Project.getProjects();
    return CZApiCycleParser.parse(projects, get(String.format("/cycle/%s", cycleId)));
  }

  public long createCycle(
      String name,
      String build,
      String environment,
      String description,
      String startDate,
      String endDate,
      Long projectId,
      Long versionId) {
    return cloneCycle(
        null, name, build, environment, description, startDate, endDate, projectId, versionId);
  }

  public long cloneCycle(
      Long clonedCycleId,
      String name,
      String build,
      String environment,
      String description,
      String startDate,
      String endDate,
      Long projectId,
      Long versionId) {
    JSONObject entity = new JSONObject();
    try {
      entity
          .put("clonedCycleId", clonedCycleId)
          .put("name", name)
          .put("build", build)
          .put("environment", environment)
          .put("description", description)
          .put("startDate", startDate)
          .put("endDate", endDate)
          .put("projectId", projectId)
          .put("versionId", versionId);
    } catch (Exception e) {
      throw new CZApiException("Could convert", e);
    }

    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZApiConfigs.ZApi.getZApiUri())
            .basePath("/cycle")
            .body(entity.toString());
    Response response = post(specification);

    try {
      String id = response.body().jsonPath().get("id");
      CVerify.String.isNotBlank(id, "Test cycle has been created.");
      return Long.valueOf(id);
    } catch (Exception e) {
      throw new CZApiException("Failed to parse response\n" + response.getBody(), e);
    }
  }
}
