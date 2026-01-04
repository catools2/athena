package org.catools.athena.rest.feign.kube;

import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.kube.configs.KubeConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class KubeCommandsTest {

  @InjectMocks
  private KubeCommands kubeCommands;

  @BeforeEach
  void setUp() {
    CoreConfigs.reload();
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
    String projectName = "Test Kube Project";

    // When
    CoreConfigs.setProjectName(projectName);

    // Then
    assertThat(CoreConfigs.getProjectName()).isEqualTo(projectName);
  }

  @Test
  void load_withProjectCode_shouldUpdateConfig() {
    // Given
    String projectCode = "KUBE";

    // When
    CoreConfigs.setProjectCode(projectCode);

    // Then
    assertThat(CoreConfigs.getProjectCode()).isEqualTo(projectCode);
  }

  @Test
  void load_withThreadsCount_shouldUpdateConfig() {
    // Given
    Integer threadsCount = 15;

    // When
    CoreConfigs.setThreadsCount(threadsCount);

    // Then
    assertThat(CoreConfigs.getThreadsCount()).isEqualTo(threadsCount);
  }

  @Test
  void load_withTimeout_shouldUpdateConfig() {
    // Given
    Long timeout = 90L;

    // When
    CoreConfigs.setTimeoutInMinutes(timeout);

    // Then
    assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(timeout);
  }

  @Test
  void load_withNamespaces_shouldUpdateKubeConfig() {
    // Given
    List<String> namespaces = List.of("default", "kube-system", "production");

    // When
    KubeConfigs.setNamespaces(namespaces);

    // Then
    assertThat(KubeConfigs.getNamespaces())
        .isNotNull()
        .hasSize(3)
        .containsExactlyElementsOf(namespaces);
  }

  @Test
  void load_withConnectionType_shouldUpdateKubeConfig() {
    // Given
    String connectionType = "TOKEN";

    // When
    KubeConfigs.setConnectionType(connectionType);

    // Then
    assertThat(KubeConfigs.getConnectionType()).isEqualTo(connectionType);
  }

  @Test
  void load_withSSLValidation_shouldUpdateKubeConfig() {
    // Given
    Boolean shouldValidateSSL = false;

    // When
    KubeConfigs.setShouldValidateSSL(shouldValidateSSL);

    // Then
    assertThat(KubeConfigs.getShouldValidateSSL()).isEqualTo(shouldValidateSSL);
  }

  @Test
  void load_withConnectionUrl_shouldUpdateKubeConfig() {
    // Given
    String connectionUrl = "https://kubernetes.default.svc:443";

    // When
    KubeConfigs.setConnectionUrl(connectionUrl);

    // Then
    assertThat(KubeConfigs.getConnectionUrl()).isEqualTo(connectionUrl);
  }

  @Test
  void load_withConnectionToken_shouldUpdateKubeConfig() {
    // Given
    String connectionToken = "test-kube-token-12345";

    // When
    KubeConfigs.setConnectionToken(connectionToken);

    // Then
    assertThat(KubeConfigs.getConnectionToken()).isEqualTo(connectionToken);
  }

  @Test
  void load_withAllParameters_shouldUpdateAllConfigs() {
    // Given
    String athenaHost = "http://test:8080";
    String projectName = "Kube Test";
    String projectCode = "KT";
    Integer threads = 7;
    Long timeout = 120L;
    List<String> namespaces = List.of("ns1", "ns2");
    String connectionType = "CONFIG";

    // When
    CoreConfigs.setAthenaHost(athenaHost);
    CoreConfigs.setProjectName(projectName);
    CoreConfigs.setProjectCode(projectCode);
    CoreConfigs.setThreadsCount(threads);
    CoreConfigs.setTimeoutInMinutes(timeout);
    KubeConfigs.setNamespaces(namespaces);
    KubeConfigs.setConnectionType(connectionType);

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
    assertThat(CoreConfigs.getProjectName()).isEqualTo(projectName);
    assertThat(CoreConfigs.getProjectCode()).isEqualTo(projectCode);
    assertThat(CoreConfigs.getThreadsCount()).isEqualTo(threads);
    assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(timeout);
    assertThat(KubeConfigs.getNamespaces()).containsExactlyElementsOf(namespaces);
    assertThat(KubeConfigs.getConnectionType()).isEqualTo(connectionType);
  }
}

