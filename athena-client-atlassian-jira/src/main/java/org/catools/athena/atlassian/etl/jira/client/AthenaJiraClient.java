package org.catools.athena.atlassian.etl.jira.client;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.catools.athena.atlassian.etl.jira.configs.JiraConfigs;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import java.net.URI;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.sleep;

@Slf4j
@UtilityClass
public class AthenaJiraClient {

  private JiraRestClient REST_CLIENT = getClient();

  public void processIssues(int threadsCount,
                            long timeoutInMinutes,
                            final String projectName,
                            String issueType,
                            Date lastSync,
                            Consumer<Issue> onAction) {
    String jql = String.format("project = \"%s\" AND issuetype = \"%s\"", projectName, issueType);

    if (lastSync != null) {
      jql += String.format(" AND updated >= \"%s\"", DateFormatUtils.format(lastSync, "yyyy-MM-dd HH:mm"));
    }
    processIssues(threadsCount, timeoutInMinutes, jql, onAction);
  }

  public void processIssues(int threadsCount, long timeoutInMinutes, final String query, Consumer<Issue> onAction) {
    AtomicInteger counter = new AtomicInteger(0);

    executeInParallel(threadsCount, timeoutInMinutes, () -> {
      while (true) {
        int startFrom = counter.getAndIncrement() * CoreConfigs.getBufferSize() + CoreConfigs.getStartAt();
        log.info("Process issues from {} to {} for query {}", startFrom, startFrom + CoreConfigs.getBufferSize(), query);
        Set<Issue> searchResult = processIssues(query, startFrom, CoreConfigs.getBufferSize());
        if (searchResult.isEmpty()) {
          return true;
        }
        searchResult.forEach(onAction);
      }
    });
  }

  public Set<Issue> processIssues(final String jql, int startAt, int bufferSize) {
    RetryPolicy<Object> retryPolicy = RetryPolicy.builder().handle(Throwable.class).withDelay(Duration.ofSeconds(10)).withMaxRetries(10).build();

    AtomicReference<Set<Issue>> issues = new AtomicReference<>(new HashSet<>());
    AtomicInteger counter = new AtomicInteger(1);

    Failsafe.with(retryPolicy).run(() -> {
      log.debug("Search JQL '{}' with bufferSize:{}, startAt:{}, attempt: {}", jql, bufferSize, startAt, counter.getAndIncrement());
      sleep(JiraConfigs.getDelayBetweenCallsInMilliseconds());
      REST_CLIENT.getSearchClient().searchJql(jql, bufferSize, startAt, null).claim().getIssues().forEach(issues.get()::add);
    });

    return issues.get();
  }

  private static JiraRestClient getClient() {
    JiraConfigs.setJiraHost("https://test-jira.com");
    JiraConfigs.setJiraUsername("testuser");
    JiraConfigs.setJiraPassword("testpass");
    URI serverUri = URI.create(JiraConfigs.getJiraHost());
    String jiraAccessToken = JiraConfigs.getJiraAccessToken();

    if (StringUtils.isNoneBlank(jiraAccessToken)) {
      return new AthenaJiraRestClientFactory().createWithAuthenticationHandler(serverUri,
          builder -> builder.setHeader("Authorization", "Bearer " + jiraAccessToken));
    }

    return new AthenaJiraRestClientFactory().createWithBasicHttpAuthentication(serverUri,
        JiraConfigs.getJiraUsername(),
        JiraConfigs.getJiraPassword());
  }
}
