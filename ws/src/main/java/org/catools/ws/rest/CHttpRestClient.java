package org.catools.ws.rest;

import org.catools.ws.enums.CHttpRequestType;

public abstract class CHttpRestClient<O> extends CHttpClient<O> {
  public CHttpRestClient(CHttpRequestType requestType, String targetURI) {
    super(requestType, targetURI);
  }

  public CHttpRestClient(CHttpRequestType requestType, String targetURI, String targetPath) {
    super(requestType, targetURI, targetPath);
  }
}
