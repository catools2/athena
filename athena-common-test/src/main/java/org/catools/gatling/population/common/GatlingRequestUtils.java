package org.catools.gatling.population.common;

import io.gatling.javaapi.http.HttpRequestActionBuilder;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

import static io.gatling.javaapi.http.HttpDsl.status;

@UtilityClass
public class GatlingRequestUtils {

  public static HttpRequestActionBuilder decorateJsonPostRequest(int statusCode, HttpRequestActionBuilder actionBuilder) {
    return actionBuilder
        .headers(Map.of("Content-Type", "application/json", "Accept", "*/*"))
        .check(status().is(statusCode));
  }

  public static HttpRequestActionBuilder decorateGetRequest(List<Integer> statusCodes, HttpRequestActionBuilder actionBuilder) {
    return decorateGetRequest(statusCodes, false, actionBuilder);
  }

  public static HttpRequestActionBuilder decorateGetRequest(List<Integer> statusCodes, Boolean useCache, HttpRequestActionBuilder actionBuilder) {
    return decorateGetRequest(statusCodes, "application/json", "*/*", useCache, actionBuilder);
  }

  public static HttpRequestActionBuilder decorateGetRequest(List<Integer> statusCodes, String contentType, String accept, HttpRequestActionBuilder actionBuilder) {
    return decorateGetRequest(statusCodes, contentType, accept, false, actionBuilder);
  }

  public static HttpRequestActionBuilder decorateGetRequest(List<Integer> statusCodes, String contentType, String accept, Boolean useCache, HttpRequestActionBuilder actionBuilder) {
    return actionBuilder
        .queryParam("nocache", useCache)
        .headers(Map.of("Content-Type", contentType, "Accept", accept))
        .check(status().in(statusCodes));
  }
}