package org.catools.atlassian.jira.client;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousSearchRestClient;
import com.atlassian.jira.rest.client.internal.json.SearchResultJsonParser;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import io.atlassian.util.concurrent.Promise;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.annotation.Nullable;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.atlassian.jira.rest.client.api.IssueRestClient.Expandos.*;

// I had to extend some core classes to avid waiting for PR to merge, some PR on client project
// waiting over a year.
public class CAsynchronousSearchRestClient extends AsynchronousSearchRestClient {
  private static final Function<IssueRestClient.Expandos, String> EXPANDO_TO_PARAM =
      from -> from.name().toLowerCase();

  private static final String START_AT_ATTRIBUTE = "startAt";
  private static final String MAX_RESULTS_ATTRIBUTE = "maxResults";
  private static final int MAX_JQL_LENGTH_FOR_HTTP_GET = 500;
  private static final String JQL_ATTRIBUTE = "jql";
  private static final String SEARCH_URI_PREFIX = "search";
  private static final String EXPAND_ATTRIBUTE = "expand";
  private static final String FIELDS_ATTRIBUTE = "fields";

  private final SearchResultJsonParser searchResultJsonParser = new SearchResultJsonParser();

  private final URI searchUri;

  public CAsynchronousSearchRestClient(final URI baseUri, final HttpClient asyncHttpClient) {
    super(baseUri, asyncHttpClient);
    this.searchUri = UriBuilder.fromUri(baseUri).path(SEARCH_URI_PREFIX).build();
  }

  @Override
  public Promise<SearchResult> searchJql(@Nullable String jql) {
    return searchJql(jql, null, null, null);
  }

  @Override
  public Promise<SearchResult> searchJql(
      @Nullable String jql,
      @Nullable Integer maxResults,
      @Nullable Integer startAt,
      @Nullable Set<String> fields) {
    final Iterable<String> expandosValues =
        Stream.of(SCHEMA, NAMES, CHANGELOG, TRANSITIONS).map(EXPANDO_TO_PARAM).collect(Collectors.toList());
    final String notNullJql = StringUtils.defaultString(jql);
    if (notNullJql.length() > MAX_JQL_LENGTH_FOR_HTTP_GET) {
      return postJql(maxResults, startAt, expandosValues, notNullJql, fields);
    } else {
      return getJql(maxResults, startAt, expandosValues, notNullJql, fields);
    }
  }

  private Promise<SearchResult> getJql(
      @Nullable Integer maxResults,
      @Nullable Integer startAt,
      Iterable<String> expandosValues,
      String jql,
      @Nullable Set<String> fields) {
    final UriBuilder uriBuilder =
        UriBuilder.fromUri(searchUri)
            .queryParam(JQL_ATTRIBUTE, jql)
            .queryParam(EXPAND_ATTRIBUTE, Joiner.on(",").join(expandosValues));

    if (fields != null) {
      uriBuilder.queryParam(FIELDS_ATTRIBUTE, Joiner.on(",").join(fields));
    }
    addOptionalParam(uriBuilder, MAX_RESULTS_ATTRIBUTE, maxResults);
    addOptionalParam(uriBuilder, START_AT_ATTRIBUTE, startAt);

    return getAndParse(uriBuilder.build(), searchResultJsonParser);
  }

  private void addOptionalParam(
      final UriBuilder uriBuilder, final String key, final Object... values) {
    if (values != null && values.length > 0 && values[0] != null) {
      uriBuilder.queryParam(key, values);
    }
  }

  private Promise<SearchResult> postJql(
      @Nullable Integer maxResults,
      @Nullable Integer startAt,
      Iterable<String> expandosValues,
      String jql,
      @Nullable Set<String> fields) {
    final JSONObject postEntity = new JSONObject();

    try {
      postEntity
          .put(JQL_ATTRIBUTE, jql)
          .put(EXPAND_ATTRIBUTE, ImmutableList.copyOf(expandosValues))
          .putOpt(START_AT_ATTRIBUTE, startAt)
          .putOpt(MAX_RESULTS_ATTRIBUTE, maxResults);

      if (fields != null) {
        postEntity.put(FIELDS_ATTRIBUTE, fields); // putOpt doesn't work with collections
      }
    } catch (JSONException e) {
      throw new RestClientException(e);
    }
    return postAndParse(searchUri, postEntity, searchResultJsonParser);
  }
}
