package org.catools.athena.atlassian.etl.jira;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.atlassian.etl.jira.client.AthenaJiraClient;
import org.catools.athena.atlassian.etl.jira.configs.JiraConfigs;
import org.catools.athena.atlassian.etl.jira.translators.JiraTranslator;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.tms.clients.TmsClient;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@UtilityClass
public class JiraSyncClient {
  private final String SYNC_JIRA = "SYNC_JIRA";

  public void syncJira() {
    syncJira(CoreConfigs.getProjectCode(), CoreConfigs.getProjectName(), JiraConfigs.getIssueTypes());
  }


  public void syncJira(String projectCode, String projectName, final List<String> issueTypes) {
    syncJira(CoreConfigs.getThreadsCount(), CoreConfigs.getTimeoutInMinutes(), projectCode, projectName, issueTypes);
  }


  public void syncJira(final int threadsCount,
                       final long timeoutInMinutes,
                       final String projectCode,
                       final String projectName,
                       final List<String> issueTypes) {
    CoreCache.readProject(new ProjectDto(projectCode, projectName));

    Instant syncStartTime = Instant.now();

    for (final String issueType : issueTypes) {
      addItems(threadsCount, timeoutInMinutes, projectCode, issueType);
    }

    TmsClient.saveSyncInfo(projectCode, SYNC_JIRA, "PROJECT", syncStartTime);
  }

  public void addItems(int threadsCount, long timeoutInMinutes, final String projectKey, String issueType) {

    Date lastSync = TmsClient.getLastSyncInfo(projectKey, SYNC_JIRA, issueType);

    Instant syncStartTime = Instant.now();
    AthenaJiraClient.processIssues(threadsCount, timeoutInMinutes, projectKey, issueType, lastSync, issues -> {
      log.info("Process jira {} {}", issueType, issues.getKey());
      TmsClient.saveItem(JiraTranslator.translateIssue(issues, projectKey, JiraConfigs.getFieldsToRead()));
    });

    TmsClient.saveSyncInfo(projectKey, SYNC_JIRA, issueType, syncStartTime);
  }

}
