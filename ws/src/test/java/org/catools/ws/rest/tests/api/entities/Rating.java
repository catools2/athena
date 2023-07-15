package org.catools.ws.rest.tests.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
  @JsonProperty("Source")
  private String source;
  @JsonProperty("Value")
  private String value;
}
