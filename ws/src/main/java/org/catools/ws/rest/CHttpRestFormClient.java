package org.catools.ws.rest;

import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CRequestParameters;

public abstract class CHttpRestFormClient<O> extends CHttpClient<O> {

  public CHttpRestFormClient(CHttpRequestType requestType, String targetURL, CRequestParameters formParameters) {
    this(requestType, targetURL, null, formParameters);
  }

  public CHttpRestFormClient(CHttpRequestType requestType, String targetURL, String targetPath, CRequestParameters formParameters) {
    super(requestType, targetURL, targetPath);
    if (formParameters != null) {
      this.getRequest().getFormParameters().putAll(formParameters);
    }
  }
}
