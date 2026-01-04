package org.catools.athena.rest.feign.core.client;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getClient;

@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class CoreClient {
  private static final ProjectClient PROJECT_CLIENT = getClient(ProjectClient.class, CoreConfigs.getAthenaHost());
  private static final EnvironmentClient ENVIRONMENT_CLIENT = getClient(EnvironmentClient.class, CoreConfigs.getAthenaHost());
  private static final VersionClient VERSION_CLIENT = getClient(VersionClient.class, CoreConfigs.getAthenaHost());
  private static final UserClient USER_CLIENT = getClient(UserClient.class, CoreConfigs.getAthenaHost());

  private static final QueryClient QUERY_CLIENT = getClient(QueryClient.class, CoreConfigs.getAthenaHost());

  public static ProjectDto searchOrCreateProject(ProjectDto project) {
    return Optional.ofNullable(search(project)).orElseGet(() -> {
      PROJECT_CLIENT.saveOrUpdate(project);
      return search(project);
    });
  }

  public static EnvironmentDto searchOrCreateEnvironment(EnvironmentDto environment) {
    return Optional.ofNullable(search(environment)).orElseGet(() -> {
      ENVIRONMENT_CLIENT.saveOrUpdate(environment);
      return search(environment);
    });
  }

  public static VersionDto searchOrCreateVersion(VersionDto version) {
    return Optional.ofNullable(search(version)).orElseGet(() -> {
      VERSION_CLIENT.saveOrUpdate(version);
      return search(version);
    });
  }

  public static UserDto searchOrCreateUser(UserDto user) {
    normalizeUser(user);
    return Optional.ofNullable(searchUser(user)).orElseGet(() -> {
      USER_CLIENT.saveOrUpdate(user);
      return searchUser(user);
    });
  }

  public static Optional<ProjectDto> searchProject(String keyword) {
    return Optional.ofNullable(PROJECT_CLIENT.search(keyword));
  }

  public static Optional<VersionDto> searchVersion(String projectCode, String keyword) {
    return Optional.ofNullable(VERSION_CLIENT.search(projectCode, keyword));
  }

  public static Optional<EnvironmentDto> searchEnvironment(String projectCode, String keyword) {
    return Optional.ofNullable(ENVIRONMENT_CLIENT.search(projectCode, keyword));
  }

  public static Optional<Object> queryRecord(String sql) {
    return Optional.ofNullable(QUERY_CLIENT.querySingleResult(sql));
  }

  public static Optional<Set<Object>> queryRecords(String sql) {
    return Optional.ofNullable(QUERY_CLIENT.queryCollection(sql));
  }

  private static UserDto searchUser(UserDto user) {
    UserDto userDto;
    for (UserAliasDto alias : user.getAliases()) {
      userDto = USER_CLIENT.search(alias.getAlias());
      if (userDto != null) {
        return userDto;
      }
    }
    return USER_CLIENT.search(user.getUsername());
  }

  private static void normalizeUser(UserDto user) {
    Set<UserAliasDto> aliases = new HashSet<>();
    user.setUsername(normalizeString(user.getUsername()));
    for (UserAliasDto alias : user.getAliases()) {
      String normalized = normalizeString(alias.getAlias());
      if (aliases.stream().noneMatch(a -> StringUtils.equalsIgnoreCase(a.getAlias(), normalized))) {
        aliases.add(new UserAliasDto(normalized));
      }

      String aliasWithoutWS = normalized.replace(" ", "");
      if (aliases.stream().noneMatch(a -> StringUtils.equalsIgnoreCase(a.getAlias(), aliasWithoutWS))) {
        aliases.add(new UserAliasDto(aliasWithoutWS));
      }
    }

    if (user.getUsername().contains(" ")) {
      String usernameWithoutWS = user.getUsername().replaceAll("\\s+", "");
      if (aliases.stream().noneMatch(a -> StringUtils.equalsIgnoreCase(a.getAlias(), usernameWithoutWS))) {
        aliases.add(new UserAliasDto(usernameWithoutWS));
      }
    }

    user.setAliases(aliases);
  }

  private static String normalizeString(final String input) {
    return input.toLowerCase()
        .replaceAll("^.*\\\\", "")
        .replaceAll("\\[.*?\\]", "")
        .replaceAll("@.*$", "")
        .replaceAll("[^a-z0-9_]+", " ")
        .replaceAll("\\s+", " ")
        .trim();
  }

  private static ProjectDto search(ProjectDto entity) {
    return searchProject(entity.getCode()).orElseGet(() -> searchProject(entity.getName()).orElse(null));
  }

  private static EnvironmentDto search(EnvironmentDto entity) {
    return searchEnvironment(entity.getProject(), entity.getCode()).orElseGet(() -> searchEnvironment(entity.getProject(), entity.getName()).orElse(null));
  }

  private static VersionDto search(VersionDto entity) {
    return searchVersion(entity.getProject(), entity.getCode()).orElseGet(() -> searchVersion(entity.getProject(), entity.getName()).orElse(null));
  }
}
