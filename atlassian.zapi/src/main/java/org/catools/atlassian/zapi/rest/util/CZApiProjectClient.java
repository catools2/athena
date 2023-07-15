package org.catools.atlassian.zapi.rest.util;

import io.restassured.response.Response;
import org.catools.atlassian.zapi.model.CZApiProjects;
import org.catools.atlassian.zapi.rest.CZApiRestClient;
import org.catools.common.utils.CJsonUtil;

public class CZApiProjectClient extends CZApiRestClient {

  public CZApiProjectClient() {
    super();
  }

  public CZApiProjects getProjects() {
    Response response = get("/util/project-list");
    return CJsonUtil.read(
        CJsonUtil.toString(response.body().jsonPath().get("options")), CZApiProjects.class);
  }
}
