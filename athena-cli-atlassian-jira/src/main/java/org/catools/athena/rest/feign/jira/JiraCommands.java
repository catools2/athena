package org.catools.athena.rest.feign.jira;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.jira.JiraSyncClient;
import org.catools.athena.atlassian.etl.jira.configs.JiraConfigs;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class JiraCommands {

  @ShellMethod(value = "Sync Jira issues", key = "sync")
  public void sync(
      @ShellOption(value = {"-ah", "--athena-host"}, help = "The Athena api endpoint to send information to", defaultValue = ShellOption.NULL) String athenaHost,
      @ShellOption(value = {"-jh", "--jira-host"}, help = "The Jira api endpoint to read information from", defaultValue = ShellOption.NULL) String jiraHost,
      @ShellOption(value = {"-jat", "--jira-access-token"}, help = "The personal access token to be used for interaction with Jira api", defaultValue = ShellOption.NULL) String jiraAccessToken,
      @ShellOption(value = {"-ju", "--jira-username"}, help = "The username to be used for interaction with Jira api", defaultValue = ShellOption.NULL) String jiraUsername,
      @ShellOption(value = {"-jp", "--jira-password"}, help = "The password to be used for interaction with Jira api", defaultValue = ShellOption.NULL) String jiraPassword,
      @ShellOption(value = {"-pn", "--project-name"}, help = "The unique project name to use for project identification", defaultValue = ShellOption.NULL) String projectName,
      @ShellOption(value = {"-pc", "--project-code"}, help = "The unique project code to use for project identification", defaultValue = ShellOption.NULL) String projectCode,
      @ShellOption(value = {"-i", "--issue-type"}, help = "The issue type to sync", defaultValue = ShellOption.NULL) List<String> issueTypes,
      @ShellOption(value = {"-f", "--fields-to-sync"}, help = "The list of Jira fields to read during sync", defaultValue = ShellOption.NULL) List<String> fieldsToRead,
      @ShellOption(value = {"-s", "--start-at"}, help = "The index to start query data from Jira", defaultValue = ShellOption.NULL) Integer startAt,
      @ShellOption(value = {"-b", "--buffer-size"}, help = "The buffer size to define maximum number of return value in each Jira call", defaultValue = ShellOption.NULL) Integer bufferSize,
      @ShellOption(value = {"-t", "--threads"}, help = "The number of total threads to use for parallel processing", defaultValue = ShellOption.NULL) Integer threadsCount,
      @ShellOption(value = {"-m", "--timeout-in-minutes"}, help = "The total amount of wait for sync to be finished", defaultValue = ShellOption.NULL) Long timeoutInMinutes
  ) {
    // Load configuration
    if (StringUtils.isNoneBlank(athenaHost)) {
      CoreConfigs.setAthenaHost(athenaHost);
    }

    if (StringUtils.isNoneBlank(jiraHost)) {
      JiraConfigs.setJiraHost(jiraHost);
    }

    if (StringUtils.isNoneBlank(jiraAccessToken)) {
      JiraConfigs.setJiraAccessToken(jiraAccessToken);
    }

    if (StringUtils.isNoneBlank(jiraUsername)) {
      JiraConfigs.setJiraUsername(jiraUsername);
    }

    if (StringUtils.isNoneBlank(jiraPassword)) {
      JiraConfigs.setJiraPassword(jiraPassword);
    }

    if (StringUtils.isNoneBlank(projectName)) {
      CoreConfigs.setProjectName(projectName);
    }

    if (StringUtils.isNoneBlank(projectCode)) {
      CoreConfigs.setProjectCode(projectCode);
    }

    if (startAt != null) {
      CoreConfigs.setStartAt(startAt);
    }

    if (bufferSize != null) {
      CoreConfigs.setBufferSize(bufferSize);
    }

    if (threadsCount != null) {
      CoreConfigs.setThreadsCount(threadsCount);
    }

    if (timeoutInMinutes != null) {
      CoreConfigs.setTimeoutInMinutes(timeoutInMinutes);
    }

    if (fieldsToRead != null) {
      JiraConfigs.setFieldsToRead(fieldsToRead);
    }

    if (issueTypes != null) {
      JiraConfigs.setIssueTypes(issueTypes);
    }

    // Execute sync
    JiraSyncClient.syncJira();
  }
}

