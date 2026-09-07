package org.catools.athena.atlassian.etl.jira.client;

import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.sleep;

import com.atlassian.httpclient.api.ResponseTooLargeException;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.BasicUser;
import com.atlassian.jira.rest.client.api.domain.Issue;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import java.net.URI;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.catools.athena.atlassian.etl.jira.configs.JiraConfigs;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

@Slf4j
@UtilityClass
public class AthenaJiraClient {

  private JiraRestClient REST_CLIENT = getClient();

  // Optional, not BasicUser: a name that Jira no longer knows has to be cacheable too, and
  // ConcurrentHashMap cannot store a null value. Without this, every historical reference to a
  // deleted account would re-query Jira and re-fail.
  private final ConcurrentMap<String, Optional<BasicUser>> USERS = new ConcurrentHashMap<>();

  public BasicUser getUser(final String username) {
    if (StringUtils.isBlank(username)) {
      return null;
    }

    return USERS.computeIfAbsent(username, AthenaJiraClient::readUser).orElse(null);
  }

  private Optional<BasicUser> readUser(final String username) {
    sleep(JiraConfigs.getDelayBetweenCallsInMilliseconds());
    try {
      return Optional.ofNullable(REST_CLIENT.getUserClient().getUser(username).claim());
    } catch (RestClientException e) {
      // Accounts get deleted, but the changelog entries they authored live forever, so a full
      // history walk is guaranteed to ask about people who no longer exist. That is ordinary data,
      // not a failure: let the caller fall back to the display name the issue payload already
      // carries. Only 404 is absorbed -- an auth failure or outage must still stop the sync rather
      // than quietly degrade every user on the run.
      if (e.getStatusCode().isPresent() && e.getStatusCode().get() == 404) {
        log.warn("Jira user '{}' no longer exists; using the name recorded on the issue.", username);
        return Optional.empty();
      }
      throw e;
    }
  }

  public void processIssues(
      int threadsCount,
      long timeoutInMinutes,
      final String projectName,
      String issueType,
      Date lastSync,
      Consumer<Issue> onAction) {
    String jql = String.format("project = \"%s\" AND issuetype = \"%s\"", projectName, issueType);

    if (lastSync != null) {
      jql +=
          String.format(
              " AND updated >= \"%s\"", DateFormatUtils.format(lastSync, "yyyy-MM-dd HH:mm"));
    }
    processIssues(threadsCount, timeoutInMinutes, jql, onAction);
  }

  public void processIssues(
      int threadsCount, long timeoutInMinutes, final String query, Consumer<Issue> onAction) {
    AtomicInteger counter = new AtomicInteger(0);

    executeInParallel(
        threadsCount,
        timeoutInMinutes,
        () -> {
          while (true) {
            int startFrom =
                counter.getAndIncrement() * CoreConfigs.getBufferSize() + CoreConfigs.getStartAt();
            log.info(
                "Process issues from {} to {} for query {}",
                startFrom,
                startFrom + CoreConfigs.getBufferSize(),
                query);
            Set<Issue> searchResult = processIssues(query, startFrom, CoreConfigs.getBufferSize());
            if (searchResult.isEmpty()) {
              return true;
            }
            searchResult.forEach(onAction);
          }
        });
  }

  public Set<Issue> processIssues(final String jql, int startAt, int bufferSize) {
    try {
      return readIssuePage(jql, startAt, bufferSize);
    } catch (ResponseTooLargeException e) {
      // Page size, not the query, is what blew the limit: issues with long changelogs (epics
      // especially) can push 100 issues past the cap in JiraConfigs. Halve and recurse -- the
      // caller's paging is unaffected because the two halves still cover exactly
      // [startAt, startAt + bufferSize). Retrying the identical request, as the policy below
      // would otherwise do ten times, just re-downloads the same oversized body.
      if (bufferSize <= 1) {
        throw new IllegalStateException(
            String.format(
                "A single Jira issue at startAt=%d exceeds the %d byte response limit for query"
                    + " '%s'. Raise athena.jira.max_response_size_in_bytes to ingest it.",
                startAt, JiraConfigs.getMaxResponseSizeInBytes(), jql),
            e);
      }

      int firstHalf = bufferSize / 2;
      log.warn(
          "Response for startAt={} bufferSize={} exceeded the size limit; splitting into {} + {}.",
          startAt,
          bufferSize,
          firstHalf,
          bufferSize - firstHalf);

      Set<Issue> issues = new HashSet<>(processIssues(jql, startAt, firstHalf));
      issues.addAll(processIssues(jql, startAt + firstHalf, bufferSize - firstHalf));
      return issues;
    }
  }

  private Set<Issue> readIssuePage(final String jql, int startAt, int bufferSize) {
    RetryPolicy<Object> retryPolicy =
        RetryPolicy.builder()
            .handle(Throwable.class)
            // Deterministic: the same request returns the same oversized body every time.
            .abortOn(ResponseTooLargeException.class)
            .withDelay(Duration.ofSeconds(10))
            .withMaxRetries(10)
            .build();

    AtomicReference<Set<Issue>> issues = new AtomicReference<>(new HashSet<>());
    AtomicInteger counter = new AtomicInteger(1);

    Failsafe.with(retryPolicy)
        .run(
            () -> {
              log.debug(
                  "Search JQL '{}' with bufferSize:{}, startAt:{}, attempt: {}",
                  jql,
                  bufferSize,
                  startAt,
                  counter.getAndIncrement());
              sleep(JiraConfigs.getDelayBetweenCallsInMilliseconds());
              REST_CLIENT
                  .getSearchClient()
                  .searchJql(jql, bufferSize, startAt, null)
                  .claim()
                  .getIssues()
                  .forEach(issues.get()::add);
            });

    return issues.get();
  }

  private static JiraRestClient getClient() {
    URI serverUri = URI.create(JiraConfigs.getJiraHost());
    String jiraAccessToken = JiraConfigs.getJiraAccessToken();

    if (StringUtils.isNoneBlank(jiraAccessToken)) {
      return new AthenaJiraRestClientFactory()
          .createWithAuthenticationHandler(
              serverUri,
              builder -> builder.setHeader("Authorization", "Bearer " + jiraAccessToken));
    }

    return new AthenaJiraRestClientFactory()
        .createWithBasicHttpAuthentication(
            serverUri, JiraConfigs.getJiraUsername(), JiraConfigs.getJiraPassword());
  }
}
