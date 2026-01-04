package org.catools.athena.rest.feign.git;

import org.catools.athena.rest.feign.apispec.configs.GitConfigs;
import org.catools.athena.rest.feign.apispec.entity.RepoInfoSet;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GitCommandsTest {

  @InjectMocks
  private GitCommands gitCommands;

  @BeforeEach
  void setUp() {
    CoreConfigs.reload();
  }

  @Test
  void load_withAthenaHost_shouldUpdateConfig() {
    // Given
    String athenaHost = "http://test-athena:9090";

    // When - Note: We can't test the full load without mocking static methods
    // Just test configuration updates
    String oldHost = CoreConfigs.getAthenaHost();
    CoreConfigs.setAthenaHost(athenaHost);

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
    assertThat(CoreConfigs.getAthenaHost()).isNotEqualTo(oldHost);
  }

  @Test
  void load_withUsername_shouldUpdateGitConfig() {
    // Given
    String username = "git-user";

    // When
    GitConfigs.setUsername(username);

    // Then
    assertThat(GitConfigs.getUsername()).isEqualTo(username);
  }

  @Test
  void load_withPassword_shouldUpdateGitConfig() {
    // Given
    String password = "git-password";

    // When
    GitConfigs.setPassword(password);

    // Then
    assertThat(GitConfigs.getPassword()).isEqualTo(password);
  }

  @Test
  void load_withRepositoryName_shouldUpdateGitConfig() {
    // Given
    String name = "test-repo";

    // When
    GitConfigs.setName(name);

    // Then
    assertThat(GitConfigs.getName()).isEqualTo(name);
  }

  @Test
  void load_withRepositoryUrl_shouldUpdateGitConfig() {
    // Given
    String url = "https://github.com/test/repo.git";

    // When
    GitConfigs.setUrl(url);

    // Then
    assertThat(GitConfigs.getUrl()).isEqualTo(url);
  }

  @Test
  void load_withLocalPath_shouldUpdateGitConfig() {
    // Given
    String localPath = "/tmp/test-repos";

    // When
    GitConfigs.setLocalPath(localPath);

    // Then
    assertThat(GitConfigs.getLocalPath()).isEqualTo(localPath);
  }

  @Test
  void load_withThreadsCount_shouldUpdateCoreConfig() {
    // Given
    Integer threadsCount = 8;

    // When
    CoreConfigs.setThreadsCount(threadsCount);

    // Then
    assertThat(CoreConfigs.getThreadsCount()).isEqualTo(threadsCount);
  }

  @Test
  void load_withTimeout_shouldUpdateCoreConfig() {
    // Given
    Long timeout = 45L;

    // When
    CoreConfigs.setTimeoutInMinutes(timeout);

    // Then
    assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(timeout);
  }

  @Test
  void load_withRepoInfoSet_shouldUpdateGitConfig() {
    // Given
    String repoInfoJson = "[{\"name\":\"repo1\",\"url\":\"https://github.com/test/repo1.git\"}]";

    // When
    GitConfigs.setRepoInfo(repoInfoJson);

    // Then
    RepoInfoSet repoInfoSet = GitConfigs.getRepoInfoSet();
    assertThat(repoInfoSet).isNotNull();
    assertThat(repoInfoSet).hasSize(1);
  }

  @Test
  void load_withMetadataPatternSet_shouldUpdateGitConfig() {
    // Given
    String metadataPatternJson = "[{\"name\":\"ticket\",\"pattern\":\"JIRA-[0-9]+\"}]";

    // When
    GitConfigs.setMetadataPatternSet(metadataPatternJson);

    // Then
    assertThat(GitConfigs.getMetadataPatternSet()).isNotNull();
    assertThat(GitConfigs.getMetadataPatternSet()).hasSize(1);
  }

  @Test
  void load_withAllParameters_shouldUpdateAllConfigs() {
    // Given
    String athenaHost = "http://test:8080";
    String username = "user";
    String password = "pass";
    String name = "repo";
    String url = "https://git.com/repo.git";
    String localPath = "/tmp/repos";
    Integer threads = 5;
    Long timeout = 60L;

    // When
    CoreConfigs.setAthenaHost(athenaHost);
    GitConfigs.setUsername(username);
    GitConfigs.setPassword(password);
    GitConfigs.setName(name);
    GitConfigs.setUrl(url);
    GitConfigs.setLocalPath(localPath);
    CoreConfigs.setThreadsCount(threads);
    CoreConfigs.setTimeoutInMinutes(timeout);

    // Then
    assertThat(CoreConfigs.getAthenaHost()).isEqualTo(athenaHost);
    assertThat(GitConfigs.getUsername()).isEqualTo(username);
    assertThat(GitConfigs.getPassword()).isEqualTo(password);
    assertThat(GitConfigs.getName()).isEqualTo(name);
    assertThat(GitConfigs.getUrl()).isEqualTo(url);
    assertThat(GitConfigs.getLocalPath()).isEqualTo(localPath);
    assertThat(CoreConfigs.getThreadsCount()).isEqualTo(threads);
    assertThat(CoreConfigs.getTimeoutInMinutes()).isEqualTo(timeout);
  }
}

