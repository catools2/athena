package org.catools.athena.core.feign;

import feign.Param;

/**
 * Custom Feign parameter expander for handling null values in query parameters.
 * Allows null filter parameters to be excluded from the query string.
 */
public class QueryExpander implements Param.Expander {

  @Override
  public String expand(Object value) {
    if (value == null) {
      return "";
    }
    return value.toString();
  }
}

