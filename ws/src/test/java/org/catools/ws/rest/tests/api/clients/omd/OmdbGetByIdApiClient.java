package org.catools.ws.rest.tests.api.clients.omd;

import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CHttpRequest;
import org.catools.ws.model.CHttpResponse;
import org.catools.ws.rest.tests.api.entities.MovieDetail;
import org.catools.ws.rest.tests.api.entities.SearchParam;

/**
 * Returns the result based on the input id e.g. tt999999.
 */
public class OmdbGetByIdApiClient extends OmdbApiClient<MovieDetail> {
  private final String id;

  public OmdbGetByIdApiClient(final String id) {
    super(CHttpRequestType.GET);
    this.id = id;
  }

  @Override
  public MovieDetail process() {
    CHttpRequest request = getRequest();
    request.addQueryParameter(SearchParam.ID.getQueryParam(), id);
    CHttpResponse response = send();
    return hasResponse(response) ? response.getContent(MovieDetail.class) : new MovieDetail();
  }
}
