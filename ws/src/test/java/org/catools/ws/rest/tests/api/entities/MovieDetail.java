package org.catools.ws.rest.tests.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MovieDetail extends Movie {
  @JsonProperty("Rated")
  private String rated;
  @JsonProperty("Released")
  private String released;
  @JsonProperty("Runtime")
  private String runtime;
  @JsonProperty("Genre")
  private String genre;
  @JsonProperty("Director")
  private String director;
  @JsonProperty("Writer")
  private String writer;
  @JsonProperty("Actors")
  private String actors;
  @JsonProperty("Plot")
  private String plot;
  @JsonProperty("Language")
  private String language;
  @JsonProperty("Country")
  private String country;
  @JsonProperty("Awards")
  private String awards;
  @JsonProperty("Ratings")
  private ArrayList<Rating> ratings;
  @JsonProperty("Metascore")
  private String metascore;
  private String imdbRating;
  private String imdbVotes;
  @JsonProperty("Type")
  private String type;
  private String totalSeasons;
  @JsonProperty("Response")
  private String response;
  @JsonProperty("DVD")
  private String dVD;
  @JsonProperty("BoxOffice")
  private String boxOffice;
  @JsonProperty("Production")
  private String production;
  @JsonProperty("Website")
  private String website;
}
