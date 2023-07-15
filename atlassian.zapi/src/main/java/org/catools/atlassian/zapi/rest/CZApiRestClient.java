package org.catools.atlassian.zapi.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.catools.atlassian.zapi.configs.CZApiConfigs;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.utils.CSleeper;

public class CZApiRestClient {

  protected Response get(String path) {
    return get(RestAssured.given().baseUri(CZApiConfigs.ZApi.getZApiUri()).basePath(path));
  }

  protected Response get(RequestSpecification request) {
    return verifyResponse(decorate(request).get());
  }

  protected Response post(RequestSpecification request) {
    return verifyResponse(decorate(request).post());
  }

  protected Response put(RequestSpecification request) {
    return verifyResponse(decorate(request).put());
  }

  private Response verifyResponse(Response response) {
    int statusCode = response.statusCode();
    if (statusCode < 200 || statusCode > 204) {
      CVerify.Int.betweenInclusive(
          statusCode, 200, 204, "Request processed successfully. response " + response.print());
    }
    return response;
  }

  protected RequestSpecification decorate(RequestSpecification request) {
    CSleeper.sleepTight(CZApiConfigs.ZApi.getDelayBetweenCallsInMilliseconds());
    return request
        .auth()
        .preemptive()
        .basic(CZApiConfigs.ZApi.getUserName(), CZApiConfigs.ZApi.getPassword())
        .contentType(ContentType.JSON);
  }
}
