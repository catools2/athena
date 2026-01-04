package org.catools.athena.rest.feign.jira;

import org.catools.athena.atlassian.etl.jira.JiraSyncClient;
import org.catools.athena.atlassian.etl.jira.configs.JiraConfigs;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class JiraCommandsTest {

  @InjectMocks
  private JiraCommands jiraCommands;

  @BeforeEach
  void setUp() {
    CoreConfigs.reload();
    JiraConfigs.reload();
  }

  @Test
  void sync_withAllParameters_shouldConfigureAndExecuteSync() {
    // Given
    String athenaHost = "http://test-athena:8080";
    String jiraHost = "https://test-jira.com";
    String jiraAccessToken = "test-token";
    String jiraUsername = "testuser";
    String jiraPassword = "testpass";
    String projectName = "Test Project";
    String projectCode = "TEST";
    List<String> issueTypes = List.of("Bug", "Story");
    List<String> fieldsToRead = List.of("Priority", "Status");
    Integer startAt = 0;
    Integer bufferSize = 100;
    Integer threadsCount = 5;
    Long timeoutInMinutes = 30L;

    try (MockedStatic<JiraSyncClient> mockedSyncClient = mockStatic(JiraSyncClient.class)) {
      // When
      jiraCommands.sync(
          athenaHost,
          jiraHost,
          jiraAccessToken,
          jiraUsername,
          jiraPassword,
          projectName,
          projectCode,
          issueTypes,
          fieldsToRead,
          startAt,
          bufferSize,
          threadsCount,
          timeoutInMinutes
      );

      // Then
      assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
      assertThat(JiraConfigs.getJiraHost()).isEqualTo(jiraHost);
      assertThat(JiraConfigs.getJiraAccessToken()).isEqualTo(jiraAccessToken);
      assertThat(JiraConfigs.getJiraUsername()).isEqualTo(jiraUsername);
      assertThat(JiraConfigs.getJiraPassword()).isEqualTo(jiraPassword);
      assertThat(CoreConfigs.getProjectName()).isEqualTo(projectName);
      assertThat(CoreConfigs.getProjectCode()).isEqualTo(projectCode);
      assertThat(CoreConfigs.getStartAt()).isEqualTo(startAt);
      assertThat(CoreConfigs.getBufferSize()).isEqualTo(bufferSize);
      assertThat(CoreConfigs.getThreadsCount()).isEqualTo(threadsCount);
      assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(timeoutInMinutes);
      assertThat(JiraConfigs.getFieldsToRead()).isEqualTo(fieldsToRead);
      assertThat(JiraConfigs.getIssueTypes()).isEqualTo(issueTypes);

      mockedSyncClient.verify(JiraSyncClient::syncJira);
    }
  }

  @Test
  void sync_withNullParameters_shouldUseDefaults() {
    // Given
    CoreConfigs.setAthenaHost("http://default-host:8080");
    CoreConfigs.setProjectName("Default Project");
    CoreConfigs.setProjectCode("DEF");

    try (MockedStatic<JiraSyncClient> mockedSyncClient = mockStatic(JiraSyncClient.class)) {
      // When
      jiraCommands.sync(
          null, // athenaHost
          null, // jiraHost
          null, // jiraAccessToken
          null, // jiraUsername
          null, // jiraPassword
          null, // projectName
          null, // projectCode
          null, // issueTypes
          null, // fieldsToRead
          null, // startAt
          null, // bufferSize
          null, // threadsCount
          null  // timeoutInMinutes
      );

      // Then
      assertThat(CoreConfigs.getAthenaHost()).isEqualTo("http://default-host:8080");
      assertThat(CoreConfigs.getProjectName()).isEqualTo("Default Project");
      assertThat(CoreConfigs.getProjectCode()).isEqualTo("DEF");

      mockedSyncClient.verify(JiraSyncClient::syncJira);
    }
  }

  @Test
  void sync_withPartialParameters_shouldUpdateOnlyProvidedValues() {
    // Given
    String newJiraHost = "https://new-jira.com";
    String newProjectCode = "NEW";

    try (MockedStatic<JiraSyncClient> mockedSyncClient = mockStatic(JiraSyncClient.class)) {
      // When
      jiraCommands.sync(
          null,
          newJiraHost,
          null,
          null,
          null,
          null,
          newProjectCode,
          null,
          null,
          null,
          null,
          null,
          null
      );

      // Then
      assertThat(JiraConfigs.getJiraHost()).isEqualTo(newJiraHost);
      assertThat(CoreConfigs.getProjectCode()).isEqualTo(newProjectCode);

      mockedSyncClient.verify(JiraSyncClient::syncJira);
    }
  }
}

