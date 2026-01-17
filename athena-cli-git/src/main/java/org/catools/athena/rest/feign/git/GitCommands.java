package org.catools.athena.rest.feign.git;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.git.configs.GitConfigs;
import org.catools.athena.rest.feign.git.entity.RepoInfo;
import org.catools.athena.rest.feign.git.entity.RepoInfoSet;
import org.catools.athena.rest.feign.git.helpers.AthenaGitApi;
import org.catools.athena.rest.feign.git.helpers.GitCloneClient;
import org.catools.athena.rest.feign.git.model.RepositoryInfo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;

@Slf4j
@ShellComponent
public class GitCommands {

  @ShellMethod(value = "Load Git repository data", key = "load")
  public void load(
      @ShellOption(value = {"--athena-host"}, help = "The Athena api endpoint to send information to", defaultValue = ShellOption.NULL) String athenaHost,
      @ShellOption(value = {"--username"}, help = "The username to clone repository", defaultValue = ShellOption.NULL) String username,
      @ShellOption(value = {"--password"}, help = "The password to clone repository", defaultValue = ShellOption.NULL) String password,
      @ShellOption(value = {"--name"}, help = "The repository name", defaultValue = ShellOption.NULL) String name,
      @ShellOption(value = {"--url"}, help = "The url to the repository to clone project from", defaultValue = ShellOption.NULL) String url,
      @ShellOption(value = {"--repo-info"}, help = "Set of repositories name and url in json format i.e. [{\"name\": \"...\",\"url\": \"...\"}]", defaultValue = ShellOption.NULL) String repoInfoSet,
      @ShellOption(value = {"--metadata-matcher"}, help = "Set of metadata name and pattern to read from commit message i.e. [{\"name\": \"...\",\"pattern\": \"...\"}]", defaultValue = ShellOption.NULL) String metadataPatternSet,
      @ShellOption(value = {"--local-path"}, help = "The path to the local folder where repository should be clone to", defaultValue = ShellOption.NULL) String localPath,
      @ShellOption(value = {"--threads"}, help = "The number of total threads to use for parallel processing", defaultValue = ShellOption.NULL) Integer threadsCount,
      @ShellOption(value = {"--timeout-in-minutes"}, help = "The total amount of wait for sync to be finished", defaultValue = ShellOption.NULL) Long timeoutInMinutes
  ) {
    // Load configuration
    if (StringUtils.isNoneBlank(athenaHost)) {
      CoreConfigs.setAthenaHost(athenaHost);
    }
    if (threadsCount != null) {
      CoreConfigs.setThreadsCount(threadsCount);
    }
    if (timeoutInMinutes != null) {
      CoreConfigs.setTimeoutInMinutes(timeoutInMinutes);
    }
    if (StringUtils.isNoneBlank(username)) {
      GitConfigs.setUsername(username);
    }
    if (StringUtils.isNoneBlank(password)) {
      GitConfigs.setPassword(password);
    }
    if (StringUtils.isNoneBlank(name)) {
      GitConfigs.setName(name);
    }
    if (StringUtils.isNoneBlank(url)) {
      GitConfigs.setUrl(url);
    }
    if (StringUtils.isNoneBlank(localPath)) {
      GitConfigs.setLocalPath(localPath);
    }
    if (StringUtils.isNoneBlank(repoInfoSet)) {
      GitConfigs.setRepoInfo(repoInfoSet);
    }
    if (StringUtils.isNoneBlank(metadataPatternSet)) {
      GitConfigs.setMetadataPatternSet(metadataPatternSet);
    }

    // Execute load
    if (GitConfigs.getRepoInfoSet() == null || GitConfigs.getRepoInfoSet().isEmpty()) {
      loadRepository(new RepoInfo(GitConfigs.getName(), GitConfigs.getUrl()));
    } else {
      loadRepositories(GitConfigs.getRepoInfoSet());
    }
  }

  private void loadRepositories(RepoInfoSet repoInfoSet) {
    for (RepoInfo repoInfo : repoInfoSet) {
      loadRepository(repoInfo);
    }
  }

  private void loadRepository(RepoInfo repoInfo) {
    GitRepositoryDto savedRepository = AthenaGitApi.getRepository(repoInfo.getName());
    RevFilter revFilter = savedRepository == null || savedRepository.getLastSync() == null ?
        CommitTimeRevFilter.NO_MERGES :
        CommitTimeRevFilter.after(Date.from(savedRepository.getLastSync()));

    Git git = cloneRepository(repoInfo);

    RepositoryInfo repositoryInfo = new RepositoryInfo(git, repoInfo.getName(), repoInfo.getUrl());
    repositoryInfo.uploadRepository(CoreConfigs.getThreadsCount(), CoreConfigs.getTimeoutInMinutes(), revFilter);
  }

  private Git cloneRepository(RepoInfo repoInfo) {
    String localPath = Path.of(StringUtils.defaultIfBlank(GitConfigs.getLocalPath(), "./tmp/repository/"), repoInfo.getName() + Instant.now())
        .toFile()
        .getPath();

    if (StringUtils.isNoneBlank(GitConfigs.getUsername())) {
      return GitCloneClient.clone(localPath, repoInfo.getName(), repoInfo.getUrl(), GitConfigs.getUsername(), GitConfigs.getPassword());
    }

    return GitCloneClient.clone(localPath, repoInfo.getName(), repoInfo.getUrl());
  }
}

