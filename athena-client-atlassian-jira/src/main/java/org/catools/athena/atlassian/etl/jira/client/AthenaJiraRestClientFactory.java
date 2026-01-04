package org.catools.athena.atlassian.etl.jira.client;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class AthenaJiraRestClientFactory extends AsynchronousJiraRestClientFactory implements JiraRestClientFactory {

  @Override
  public JiraRestClient create(final URI serverUri, final AuthenticationHandler authenticationHandler) {
    final DisposableHttpClient httpClient = new AthenaHttpClientFactory().createClient(5, TimeUnit.MINUTES, serverUri, authenticationHandler);
    return new AthenaJiraRestClient(serverUri, httpClient);
  }

  @Override
  public JiraRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username, final String password) {
    return create(serverUri, new BasicHttpAuthenticationHandler(username, password));
  }

  @Override
  public JiraRestClient createWithAuthenticationHandler(final URI serverUri, final AuthenticationHandler authenticationHandler) {
    return create(serverUri, authenticationHandler);
  }

  @Override
  public JiraRestClient create(final URI serverUri, final HttpClient httpClient) {
    final DisposableHttpClient disposableHttpClient = new AsynchronousHttpClientFactory().createClient(httpClient);
    return new AthenaJiraRestClient(serverUri, disposableHttpClient);
  }
}
