package org.catools.ws.rest;

import io.restassured.http.ContentType;
import org.catools.ws.enums.CHttpRequestType;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class CHttpRestJsonClient<O> extends CHttpRestClient<O> {

  public CHttpRestJsonClient(CHttpRequestType requestType, String targetURL) {
    this(requestType, targetURL, null);
  }

  public CHttpRestJsonClient(CHttpRequestType requestType, String targetURL, String targetPath) {
    super(requestType, targetURL, targetPath);
    this.getRequest().setContentType(ContentType.JSON);
  }

  public JSONObject getEntityAsJson() {
    return (JSONObject) getRequest().getEntity();
  }

  public JSONArray getEntityAsJsonArray() {
    return (JSONArray) getRequest().getEntity();
  }
}
