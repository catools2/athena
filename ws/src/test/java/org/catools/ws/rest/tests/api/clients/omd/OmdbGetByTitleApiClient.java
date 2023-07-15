package org.catools.ws.rest.tests.api.clients.omd;

import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CHttpRequest;
import org.catools.ws.model.CHttpResponse;
import org.catools.ws.rest.tests.api.entities.MovieDetail;
import org.catools.ws.rest.tests.api.entities.SearchParam;

/**
 * Returns the result based on the input title name and verify that the response status code is 200.
 */
public class OmdbGetByTitleApiClient extends OmdbApiClient<MovieDetail> {
  private final String title;

  public OmdbGetByTitleApiClient(final String title) {
    super(CHttpRequestType.GET);
    this.title = title;
  }

  @Override
  public MovieDetail process() {
    CHttpRequest request = getRequest();
    request.addQueryParameter(SearchParam.TITLE.getQueryParam(), title);
    CHttpResponse response = send();
    return hasResponse(response) ? response.getContent(MovieDetail.class) : new MovieDetail();
  }
}
