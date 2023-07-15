package org.catools.ws.rest.tests.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.catools.ws.rest.tests.api.entities.Movies;

/**
 * The search result which expected to be return from OMD endpoint on search request
 * It should not be used for testing directly as it carry implementaiton details
 */
@Data
public class SearchResultDTO {
  @JsonProperty("Search")
  private Movies search;
  private String totalResults;
  @JsonProperty("Response")
  private String response;

}
