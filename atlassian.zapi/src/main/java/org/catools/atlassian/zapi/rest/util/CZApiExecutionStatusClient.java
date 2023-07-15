package org.catools.atlassian.zapi.rest.util;

import io.restassured.response.Response;
import org.catools.atlassian.zapi.model.CZApiExecutionStatuses;
import org.catools.atlassian.zapi.rest.CZApiRestClient;
import org.catools.common.utils.CJsonUtil;

public class CZApiExecutionStatusClient extends CZApiRestClient {

  public CZApiExecutionStatusClient() {
    super();
  }

  public CZApiExecutionStatuses getExecutionStatus() {
    Response response = get("/util/testExecutionStatus");
    return CJsonUtil.read(response.getBody().print(), CZApiExecutionStatuses.class);
  }
}
