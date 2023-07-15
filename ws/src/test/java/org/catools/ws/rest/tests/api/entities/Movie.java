package org.catools.ws.rest.tests.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Movie {
  @JsonProperty("Title")
  private String title;
  @JsonProperty("Year")
  private String year;
  private String imdbID;
  @JsonProperty("Type")
  private String type;
  @JsonProperty("Poster")
  private String poster;
}
