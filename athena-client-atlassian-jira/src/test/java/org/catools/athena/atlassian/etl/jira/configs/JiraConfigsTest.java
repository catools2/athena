package org.catools.athena.atlassian.etl.jira.configs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

// Every method here mutates JiraConfigs' static state, and the build runs methods
// concurrently by default, so this class must not share a thread pool with itself.
@Execution(ExecutionMode.SAME_THREAD)
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
    assertThat(JiraConfigs.getIssueTypes()).contains("Epic", "Story", "Test", "Bug");
  }

  @Test
  void reload_shouldContainDefaultFieldsToRead() {
    // When
    JiraConfigs.reload();

    // Then
    assertThat(JiraConfigs.getFieldsToRead())
        .contains("Epic Name", "Epic Link", "Component", "Label");
  }

  @Test
  void reload_shouldContainVendorStandardFieldsLiftedOutOfIssueFields() {
    // These arrive through JRJC's typed accessors rather than issue.getFields(), so
    // TranslatorHelper adds them explicitly - the defaults must still name them.
    JiraConfigs.reload();

    assertThat(JiraConfigs.getFieldsToRead())
        .contains("Assignee", "Labels", "Resolution", "Fix Version/s", "Affects Version/s");
  }

  @Test
  void reload_shouldDefaultLabelHistoryToTrack() {
    // When
    JiraConfigs.reload();

    // Then
    assertThat(JiraConfigs.getLabelHistoryToTrack())
        .containsExactly("AIGenerated", "AIReviewed", "AIAssisted", "AIFixed");
  }

  @Test
  void reload_shouldDefaultMaxResponseSizeToTheAtlassianClientDefault() {
    // When
    JiraConfigs.reload();

    // Then
    assertThat(JiraConfigs.getMaxResponseSizeInBytes()).isEqualTo(104857600L);
  }

  @Test
  void reload_shouldPickUpANamedOverrideFile() {
    // Site-specific settings live in their own HOCON file selected through CONFIGS_TO_LOAD;
    // this proves that selection reaches JiraConfigs rather than silently falling back.
    String originalConfigToLoad = System.getProperty(ConfigUtils.CONFIGS_TO_LOAD);
    try {
      System.setProperty(ConfigUtils.CONFIGS_TO_LOAD, "demo-jira-test");
      ConfigUtils.reload();
      JiraConfigs.reload();

      assertThat(JiraConfigs.getJiraHost()).isEqualTo("https://jira.example.com");
      assertThat(JiraConfigs.getJiraUsername()).isEqualTo("demo-automation");
      assertThat(JiraConfigs.getDelayBetweenCallsInMilliseconds()).isEqualTo(500L);
      assertThat(JiraConfigs.getMaxResponseSizeInBytes()).isEqualTo(52428800L);
      assertThat(JiraConfigs.getLabelHistoryToTrack()).containsExactly("AIGenerated", "AIReviewed");
      assertThat(JiraConfigs.getIssueTypes())
          .containsExactly("Epic", "Story", "Test", "Bug", "Task", "Sub-task");
      assertThat(JiraConfigs.getFieldsToRead())
          .containsExactly(
              "Affected Version/s",
              "Component",
              "Epic Name",
              "Epic Link",
              "Fix Version/s",
              "IssueLink",
              "Label",
              "Parent",
              "Team");
    } finally {
      if (originalConfigToLoad == null) {
        System.clearProperty(ConfigUtils.CONFIGS_TO_LOAD);
      } else {
        System.setProperty(ConfigUtils.CONFIGS_TO_LOAD, originalConfigToLoad);
      }
      ConfigUtils.reload();
      JiraConfigs.reload();
    }
  }
}
