package org.catools.ws.rest.tests.api.clients.omd;

import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CHttpRequest;
import org.catools.ws.model.CHttpResponse;
import org.catools.ws.rest.tests.api.dto.SearchResultDTO;
import org.catools.ws.rest.tests.api.entities.Movies;
import org.catools.ws.rest.tests.api.entities.SearchParam;

import java.util.Map;

/**
 * Perform search and returns a list of all results from specified page of matches.
 */
public class OmdbSearchApiClient extends OmdbApiClient<SearchResultDTO> {
  private static final int MAX_PAGES_TO_READ = 1000;
  private final Map<SearchParam, Object> parameters;

  public OmdbSearchApiClient(final Map<SearchParam, Object> parameters) {
    super(CHttpRequestType.GET);
    this.parameters = parameters;
  }

  public Movies readAllPages() {
    Movies output = new Movies();
    int i = 0;
    // To make sure that we do not get into the dead loop
    while (i++ < MAX_PAGES_TO_READ) {
      getRequest().getParameters().put(SearchParam.PAGE.getQueryParam(), i);
      Movies searchResult = process().getSearch();
      if (searchResult == null || searchResult.isEmpty()) {
        break;
      }
      output.addAll(searchResult);
    }
    return output;
  }

  @Override
  public SearchResultDTO process() {
    CHttpRequest request = getRequest();
    parameters.forEach((k, v) -> request.addQueryParameter(k.getQueryParam(), v));
    CHttpResponse response = send();
    return hasResponse(response) ? response.getContent(SearchResultDTO.class) : new SearchResultDTO();
  }
}
