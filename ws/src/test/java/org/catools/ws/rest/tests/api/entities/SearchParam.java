package org.catools.ws.rest.tests.api.entities;

import lombok.Getter;

/**
 * Query parameters for OMD endpoint.
 *
 * @see <a href="https://www.omdbapi.com/#parameters">OMDb API Parameters</a> for more detail
 */
public enum SearchParam {
  ID("i"),
  TITLE("t"),
  YEAR("y"),
  SEARCH("s"),
  PAGE("page");

  @Getter
  private String queryParam;

  SearchParam(String queryParam) {
    this.queryParam = queryParam;
  }
}
