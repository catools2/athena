package org.catools.atlassian.zapi.rest.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.catools.atlassian.zapi.configs.CZApiConfigs;
import org.catools.atlassian.zapi.model.CZApiProject;
import org.catools.atlassian.zapi.model.CZApiProjectVersions;
import org.catools.atlassian.zapi.model.CZApiVersions;
import org.catools.atlassian.zapi.rest.CZApiRestClient;
import org.catools.common.utils.CJsonUtil;

public class CZApiVersionClient extends CZApiRestClient {

  public CZApiVersionClient() {
    super();
  }

  public CZApiProjectVersions getProjectVersions(CZApiProject project) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZApiConfigs.ZApi.getZApiUri())
            .basePath("/util/versionBoard-list")
            .queryParam("projectId", project.getId());

    Response response = get(specification);

    CZApiProjectVersions versions = new CZApiProjectVersions();
    versions.setReleasedVersions(
        CJsonUtil.read(
            CJsonUtil.toString(response.body().jsonPath().get("releasedVersions")),
            CZApiVersions.class));
    versions.setUnreleasedVersions(
        CJsonUtil.read(
            CJsonUtil.toString(response.body().jsonPath().get("unreleasedVersions")),
            CZApiVersions.class));
    return versions;
  }
}
