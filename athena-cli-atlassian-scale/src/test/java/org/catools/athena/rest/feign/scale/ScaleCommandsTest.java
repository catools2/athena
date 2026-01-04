package org.catools.athena.rest.feign.scale;

import org.catools.athena.atlassian.etl.scale.ScaleSyncClient;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
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
class ScaleCommandsTest {

  @InjectMocks
  private ScaleCommands scaleCommands;

  @BeforeEach
  void setUp() {
    CoreConfigs.reload();
    ScaleConfigs.reload();
  }

  @Test
  void sync_withAllParameters_shouldConfigureAndExecuteSync() {
    // Given
    String athenaHost = "http://test-athena:8080";
    String scaleHost = "https://test-scale.com";
    String scaleAccessToken = "test-token";
    String scaleUsername = "testuser";
    String scalePassword = "testpass";
    String projectName = "Test Project";
    String projectCode = "TEST";
    Boolean syncTests = true;
    Boolean syncRuns = false;
    Integer startAt = 0;
    Integer bufferSize = 100;
    Integer threadsCount = 5;
    Long timeoutInMinutes = 30L;
    List<String> testRunFolders = List.of("folder1", "folder2");
    List<String> testCaseFolders = List.of("cases1", "cases2");

    try (MockedStatic<ScaleSyncClient> mockedSyncClient = mockStatic(ScaleSyncClient.class)) {
      // When
      scaleCommands.sync(
          athenaHost,
          scaleHost,
          scaleAccessToken,
          scaleUsername,
          scalePassword,
          projectName,
          projectCode,
          syncTests,
          syncRuns,
          startAt,
          bufferSize,
          threadsCount,
          timeoutInMinutes,
          testRunFolders,
          testCaseFolders
      );

      // Then
      assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
      assertThat(ScaleConfigs.getScaleHost()).isEqualTo(scaleHost);
      assertThat(ScaleConfigs.getScaleAccessToken()).isEqualTo(scaleAccessToken);
      assertThat(ScaleConfigs.getScaleUsername()).isEqualTo(scaleUsername);
      assertThat(ScaleConfigs.getScalePassword()).isEqualTo(scalePassword);
      assertThat(CoreConfigs.getProjectName()).isEqualTo(projectName);
      assertThat(CoreConfigs.getProjectCode()).isEqualTo(projectCode);
      assertThat(ScaleConfigs.isSyncTests()).isEqualTo(syncTests);
      assertThat(ScaleConfigs.isSyncRuns()).isEqualTo(syncRuns);
      assertThat(CoreConfigs.getStartAt()).isEqualTo(startAt);
      assertThat(CoreConfigs.getBufferSize()).isEqualTo(bufferSize);
      assertThat(CoreConfigs.getThreadsCount()).isEqualTo(threadsCount);
      assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(timeoutInMinutes);
      assertThat(ScaleConfigs.getTestRunFoldersToSync()).isEqualTo(testRunFolders);
      assertThat(ScaleConfigs.getTestCasesFoldersToSync()).isEqualTo(testCaseFolders);

      mockedSyncClient.verify(ScaleSyncClient::syncTestCases);
    }
  }

  @Test
  void sync_withBothSyncFlags_shouldExecuteBothSyncs() {
    // Given
    Boolean syncTests = true;
    Boolean syncRuns = true;

    try (MockedStatic<ScaleSyncClient> mockedSyncClient = mockStatic(ScaleSyncClient.class)) {
      // When
      scaleCommands.sync(
          null, null, null, null, null, null, null,
          syncTests, syncRuns,
          null, null, null, null, null, null
      );

      // Then
      mockedSyncClient.verify(ScaleSyncClient::syncTestCases);
      mockedSyncClient.verify(ScaleSyncClient::syncTestRuns);
    }
  }

  @Test
  void sync_withOnlySyncTests_shouldExecuteOnlyTestCasesSync() {
    // Given
    Boolean syncTests = true;
    Boolean syncRuns = false;

    try (MockedStatic<ScaleSyncClient> mockedSyncClient = mockStatic(ScaleSyncClient.class)) {
      // When
      scaleCommands.sync(
          null, null, null, null, null, null, null,
          syncTests, syncRuns,
          null, null, null, null, null, null
      );

      // Then
      mockedSyncClient.verify(ScaleSyncClient::syncTestCases);
      mockedSyncClient.verifyNoMoreInteractions();
    }
  }

  @Test
  void sync_withOnlySyncRuns_shouldExecuteOnlyTestRunsSync() {
    // Given
    Boolean syncTests = false;
    Boolean syncRuns = true;

    try (MockedStatic<ScaleSyncClient> mockedSyncClient = mockStatic(ScaleSyncClient.class)) {
      // When
      scaleCommands.sync(
          null, null, null, null, null, null, null,
          syncTests, syncRuns,
          null, null, null, null, null, null
      );

      // Then
      mockedSyncClient.verify(ScaleSyncClient::syncTestRuns);
      mockedSyncClient.verifyNoMoreInteractions();
    }
  }

  @Test
  void sync_withNullParameters_shouldUseDefaults() {
    // Given
    CoreConfigs.setProjectCode("DEF");
    ScaleConfigs.setSyncTests(false);
    ScaleConfigs.setSyncRuns(false);

    try (MockedStatic<ScaleSyncClient> mockedSyncClient = mockStatic(ScaleSyncClient.class)) {
      // When
      scaleCommands.sync(
          null, null, null, null, null, null, null,
          null, null,
          null, null, null, null, null, null
      );

      // Then
      assertThat(CoreConfigs.getProjectCode()).isEqualTo("DEF");
      // No sync should happen when both flags are false
      mockedSyncClient.verifyNoInteractions();
    }
  }
}

