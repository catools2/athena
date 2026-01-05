package org.catools.athena.atlassian.etl.jira.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JiraConfigsTest {

  @BeforeEach
  void setUp() {
    JiraConfigs.reload();
  }

  @Test
  void setAndGetJiraHost_shouldWorkCorrectly() {
    // Given
    String expectedHost = "https://jira.example.com";

    // When
    JiraConfigs.setJiraHost(expectedHost);

    // Then
    assertThat(JiraConfigs.getJiraHost()).isEqualTo(expectedHost);
  }

  @Test
  void setAndGetJiraAccessToken_shouldWorkCorrectly() {
    // Given
    String expectedToken = "test-access-token-123";

    // When
    JiraConfigs.setJiraAccessToken(expectedToken);

    // Then
    assertThat(JiraConfigs.getJiraAccessToken()).isEqualTo(expectedToken);
  }

  @Test
  void setAndGetJiraUsername_shouldWorkCorrectly() {
    // Given
    String expectedUsername = "testuser";

    // When
    JiraConfigs.setJiraUsername(expectedUsername);

    // Then
    assertThat(JiraConfigs.getJiraUsername()).isEqualTo(expectedUsername);
  }

  @Test
  void setAndGetJiraPassword_shouldWorkCorrectly() {
    // Given
    String expectedPassword = "testpassword";

    // When
    JiraConfigs.setJiraPassword(expectedPassword);

    // Then
    assertThat(JiraConfigs.getJiraPassword()).isEqualTo(expectedPassword);
  }

  @Test
  void setAndGetDelayBetweenCallsInMilliseconds_shouldWorkCorrectly() {
    // Given
    Long expectedDelay = 2000L;

    // When
    JiraConfigs.setDelayBetweenCallsInMilliseconds(expectedDelay);

    // Then
    assertThat(JiraConfigs.getDelayBetweenCallsInMilliseconds()).isEqualTo(expectedDelay);
  }

  @Test
  void setAndGetIssueTypes_shouldWorkCorrectly() {
    // Given
    List<String> expectedIssueTypes = List.of("Bug", "Story", "Task");

    // When
    JiraConfigs.setIssueTypes(expectedIssueTypes);

    // Then
    assertThat(JiraConfigs.getIssueTypes())
        .isNotNull()
        .hasSize(3)
        .containsExactlyElementsOf(expectedIssueTypes);
  }

  @Test
  void setAndGetFieldsToRead_shouldWorkCorrectly() {
    // Given
    List<String> expectedFields = List.of("Priority", "Status", "Assignee");

    // When
    JiraConfigs.setFieldsToRead(expectedFields);

    // Then
    assertThat(JiraConfigs.getFieldsToRead())
        .isNotNull()
        .hasSize(3)
        .containsExactlyElementsOf(expectedFields);
  }

  @Test
  void reload_shouldSetDefaultValues() {
    // When
    JiraConfigs.reload();

    // Then
    assertThat(JiraConfigs.getDelayBetweenCallsInMilliseconds()).isNotNull();
    assertThat(JiraConfigs.getIssueTypes()).isNotNull().isNotEmpty();
    assertThat(JiraConfigs.getFieldsToRead()).isNotNull().isNotEmpty();
  }

  @Test
  void reload_shouldContainDefaultIssueTypes() {
    // When
    JiraConfigs.reload();

    // Then
    assertThat(JiraConfigs.getIssueTypes())
        .contains("Epic", "Story", "Test", "Bug");
  }

  @Test
  void reload_shouldContainDefaultFieldsToRead() {
    // When
    JiraConfigs.reload();

    // Then
    assertThat(JiraConfigs.getFieldsToRead())
        .contains("Epic Name", "Epic Link", "Component", "Label");
  }
}

