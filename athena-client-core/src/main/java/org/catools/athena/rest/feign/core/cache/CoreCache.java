package org.catools.athena.rest.feign.core.cache;

import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.rest.feign.common.cache.CacheStorage;
import org.catools.athena.rest.feign.core.client.CoreClient;

public class CoreCache {
  private static final CacheStorage<String, UserDto> USERS = new CacheStorage<>("User", UserDto::getUsername, CoreClient::searchOrCreateUser);
  private static final CacheStorage<String, ProjectDto> PROJECTS = new CacheStorage<>("Project",
      ProjectDto::getCode,
      CoreClient::searchOrCreateProject);
  private static final CacheStorage<String, VersionDto> VERSIONS = new CacheStorage<>("Version",
      VersionDto::getCode,
      CoreClient::searchOrCreateVersion);
  private static final CacheStorage<String, EnvironmentDto> ENVIRONMENTS = new CacheStorage<>("Environment",
      EnvironmentDto::getCode,
      CoreClient::searchOrCreateEnvironment);

  public static synchronized UserDto readUser(UserDto user) {
    return USERS.read(user);
  }

  public static synchronized ProjectDto readProject(ProjectDto project) {
    return PROJECTS.read(project);
  }

  public static synchronized VersionDto readVersion(VersionDto version) {
    return VERSIONS.read(version);
  }

  public static synchronized EnvironmentDto readEnvironment(EnvironmentDto environment) {
    return ENVIRONMENTS.read(environment);
  }
}
