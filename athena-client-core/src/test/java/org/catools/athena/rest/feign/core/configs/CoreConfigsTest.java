package org.catools.athena.rest.feign.core.configs;

import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.VersionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoreConfigsTest {

  @BeforeEach
  void setUp() {
    CoreConfigs.reload();
  }

  @Test
  void setAndGetAthenaHost_shouldWorkCorrectly() {
    // Given
    String expectedHost = "http://test.example.com:9090";

    // When
    CoreConfigs.setAthenaHost(expectedHost);

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isEqualTo(expectedHost);
  }

  @Test
  void setAndGetProjectName_shouldWorkCorrectly() {
    // Given
    String expectedName = "Test Project";

    // When
    CoreConfigs.setProjectName(expectedName);

    // Then
    assertThat(CoreConfigs.getProjectName()).isEqualTo(expectedName);
  }

  @Test
  void setAndGetProjectCode_shouldWorkCorrectly() {
    // Given
    String expectedCode = "TEST";

    // When
    CoreConfigs.setProjectCode(expectedCode);

    // Then
    assertThat(CoreConfigs.getProjectCode()).isEqualTo(expectedCode);
  }

  @Test
  void setAndGetEnvironmentName_shouldWorkCorrectly() {
    // Given
    String expectedName = "Production";

    // When
    CoreConfigs.setEnvironmentName(expectedName);

    // Then
    assertThat(CoreConfigs.getEnvironmentName()).isEqualTo(expectedName);
  }

  @Test
  void setAndGetEnvironmentCode_shouldWorkCorrectly() {
    // Given
    String expectedCode = "PROD";

    // When
    CoreConfigs.setEnvironmentCode(expectedCode);

    // Then
    assertThat(CoreConfigs.getEnvironmentCode()).isEqualTo(expectedCode);
  }

  @Test
  void setAndGetVersionName_shouldWorkCorrectly() {
    // Given
    String expectedName = "1.0.0";

    // When
    CoreConfigs.setVersionName(expectedName);

    // Then
    assertThat(CoreConfigs.getVersionName()).isEqualTo(expectedName);
  }

  @Test
  void setAndGetVersionCode_shouldWorkCorrectly() {
    // Given
    String expectedCode = "v1";

    // When
    CoreConfigs.setVersionCode(expectedCode);

    // Then
    assertThat(CoreConfigs.getVersionCode()).isEqualTo(expectedCode);
  }

  @Test
  void setAndGetStartAt_shouldWorkCorrectly() {
    // Given
    Integer expectedStartAt = 100;

    // When
    CoreConfigs.setStartAt(expectedStartAt);

    // Then
    assertThat(CoreConfigs.getStartAt()).isEqualTo(expectedStartAt);
  }

  @Test
  void setAndGetBufferSize_shouldWorkCorrectly() {
    // Given
    Integer expectedBufferSize = 75;

    // When
    CoreConfigs.setBufferSize(expectedBufferSize);

    // Then
    assertThat(CoreConfigs.getBufferSize()).isEqualTo(expectedBufferSize);
  }

  @Test
  void setAndGetThreadsCount_shouldWorkCorrectly() {
    // Given
    Integer expectedThreadsCount = 20;

    // When
    CoreConfigs.setThreadsCount(expectedThreadsCount);

    // Then
    assertThat(CoreConfigs.getThreadsCount()).isEqualTo(expectedThreadsCount);
  }

  @Test
  void setAndGetTimeoutInMinutes_shouldWorkCorrectly() {
    // Given
    Long expectedTimeout = 180L;

    // When
    CoreConfigs.setTimeoutInMinutes(expectedTimeout);

    // Then
    assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(expectedTimeout);
  }

  @Test
  void getProject_shouldReturnProjectDto() {
    // Given
    CoreConfigs.setProjectCode("TEST");
    CoreConfigs.setProjectName("Test Project");

    // When
    ProjectDto project = CoreConfigs.getProject();

    // Then
    assertThat(project).isNotNull();
    assertThat(project.getCode()).isEqualTo("TEST");
    assertThat(project.getName()).isEqualTo("Test Project");
  }

  @Test
  void getEnvironment_shouldReturnEnvironmentDto() {
    // Given
    CoreConfigs.setEnvironmentCode("DEV");
    CoreConfigs.setEnvironmentName("Development");
    CoreConfigs.setProjectCode("TEST");

    // When
    EnvironmentDto environment = CoreConfigs.getEnvironment();

    // Then
    assertThat(environment).isNotNull();
    assertThat(environment.getCode()).isEqualTo("DEV");
    assertThat(environment.getName()).isEqualTo("Development");
  }

  @Test
  void getVersion_shouldReturnVersionDto() {
    // Given
    CoreConfigs.setVersionCode("v2");
    CoreConfigs.setVersionName("2.0.0");
    CoreConfigs.setProjectCode("TEST");

    // When
    VersionDto version = CoreConfigs.getVersion();

    // Then
    assertThat(version).isNotNull();
    assertThat(version.getCode()).isEqualTo("v2");
    assertThat(version.getName()).isEqualTo("2.0.0");
  }

  @Test
  void reload_shouldSetDefaultValues() {
    // When
    CoreConfigs.reload();

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isNotNull();
    assertThat(CoreConfigs.getStartAt()).isNotNull().isEqualTo(0);
    assertThat(CoreConfigs.getBufferSize()).isNotNull().isEqualTo(50);
    assertThat(CoreConfigs.getThreadsCount()).isNotNull().isEqualTo(10);
    assertThat(CoreConfigs.getTimeoutInMinutes()).isNotNull().isEqualTo(120L);
  }
}

