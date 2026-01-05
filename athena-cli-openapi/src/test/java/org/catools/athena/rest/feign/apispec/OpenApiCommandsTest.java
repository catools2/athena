package org.catools.athena.rest.feign.apispec;

import org.catools.athena.rest.feign.apispec.configs.OpenApiConfigs;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OpenApiCommandsTest {

  @InjectMocks
  private OpenApiCommands openApiCommands;

  @BeforeEach
  void setUp() {
    CoreConfigs.reload();
    OpenApiConfigs.reload();
  }

  @Test
  void load_withAthenaHost_shouldUpdateConfig() {
    // Given
    String athenaHost = "http://test-athena:8080";

    // When
    CoreConfigs.setAthenaHost(athenaHost);

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
  }

  @Test
  void load_withProjectName_shouldUpdateConfig() {
    // Given
    String projectName = "Test OpenAPI Project";

    // When
    CoreConfigs.setProjectName(projectName);

    // Then
    assertThat(CoreConfigs.getProjectName()).isEqualTo(projectName);
  }

  @Test
  void load_withProjectCode_shouldUpdateConfig() {
    // Given
    String projectCode = "OPENAPI";

    // When
    CoreConfigs.setProjectCode(projectCode);

    // Then
    assertThat(CoreConfigs.getProjectCode()).isEqualTo(projectCode);
  }

  @Test
  void load_withSpecNames_shouldUpdateOpenApiConfig() {
    // Given
    List<String> specNames = List.of("spec1", "spec2", "spec3");

    // When
    OpenApiConfigs.setSpecNames(specNames);

    // Then
    assertThat(OpenApiConfigs.getSpecNames())
        .isNotNull()
        .hasSize(3)
        .containsExactlyElementsOf(specNames);
  }

  @Test
  void load_withSpecUrls_shouldUpdateOpenApiConfig() {
    // Given
    List<String> specUrls = List.of(
        "https://example.com/api/v1/openapi.json",
        "https://example.com/api/v2/openapi.json"
    );

    // When
    OpenApiConfigs.setSpecUrls(specUrls);

    // Then
    assertThat(OpenApiConfigs.getSpecUrls())
        .isNotNull()
        .hasSize(2)
        .containsExactlyElementsOf(specUrls);
  }

  @Test
  void load_withSpecInfo_shouldUpdateOpenApiConfig() {
    // Given
    String specInfoJson = "[{\"name\":\"API v1\",\"url\":\"https://example.com/api/v1/openapi.json\"}]";

    // When
    OpenApiConfigs.setSpecInfo(specInfoJson);

    // Then
    assertThat(OpenApiConfigs.getSpecInfoSet())
        .isNotNull()
        .hasSize(1);
  }

  @Test
  void load_withAllParameters_shouldUpdateAllConfigs() {
    // Given
    String athenaHost = "http://test:8080";
    String projectName = "OpenAPI Test";
    String projectCode = "OAT";
    List<String> specNames = List.of("spec1", "spec2");
    List<String> specUrls = List.of("https://example.com/spec1.json", "https://example.com/spec2.json");

    // When
    CoreConfigs.setAthenaHost(athenaHost);
    CoreConfigs.setProjectName(projectName);
    CoreConfigs.setProjectCode(projectCode);
    OpenApiConfigs.setSpecNames(specNames);
    OpenApiConfigs.setSpecUrls(specUrls);

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
    assertThat(CoreConfigs.getProjectName()).isEqualTo(projectName);
    assertThat(CoreConfigs.getProjectCode()).isEqualTo(projectCode);
    assertThat(OpenApiConfigs.getSpecNames()).containsExactlyElementsOf(specNames);
    assertThat(OpenApiConfigs.getSpecUrls()).containsExactlyElementsOf(specUrls);
  }
}
