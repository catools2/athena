package org.catools.athena.configs;

import lombok.experimental.UtilityClass;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class StagedTestData {
  private static final String DEVELOPMENT = "Development";
  private static final String DEVELOPMENT_SMOKE = "Development Smoke";
  private static final String STAGING = "Staging";
  private static final String PRODUCTION = "Production";
  private static final String QA = "QA";

  private final Map<Long, ProjectDto> PROJECTS = Stream.of(
      new ProjectDto().setId(1L).setCode("ADM").setName("Administration"),
      new ProjectDto().setId(2L).setCode("STG").setName("Storage"),
      new ProjectDto().setId(3L).setCode("ATO").setName("Automation"),
      new ProjectDto().setId(4L).setCode("FIN").setName("Finance"),
      new ProjectDto().setId(5L).setCode("ORG").setName("Organization")
  ).collect(Collectors.toMap(ProjectDto::getId, r -> r));

  private final Map<Long, UserDto> USERS = Stream.of(
      new UserDto(1L, "AKeshmiri", Set.of(new UserAliasDto().setAlias("ak"))),
      new UserDto(2L, "JDoe", Set.of(new UserAliasDto().setAlias("jd"), new UserAliasDto().setAlias("jone doe"))),
      new UserDto(3L, "JSmith", Set.of(new UserAliasDto().setAlias("ak"))),
      new UserDto(4L, "AHolmes", Set.of()),
      new UserDto(5L, "BRyan", Set.of())
  ).collect(Collectors.toMap(UserDto::getId, r -> r));

  private final Map<Long, EnvironmentDto> ENVIRONMENTS = Stream.of(
          getEnvironment(1L, "DEV", DEVELOPMENT, PROJECTS.get(1L)),
          getEnvironment(2L, "SMK", DEVELOPMENT_SMOKE, PROJECTS.get(1L)),
          getEnvironment(3L, QA, QA, PROJECTS.get(1L)),
          getEnvironment(4L, "STG", STAGING, PROJECTS.get(1L)),
          getEnvironment(5L, "PRD", PRODUCTION, PROJECTS.get(1L)),
          getEnvironment(6L, "DEV", DEVELOPMENT, PROJECTS.get(2L)),
          getEnvironment(7L, "SMK", DEVELOPMENT_SMOKE, PROJECTS.get(2L)),
          getEnvironment(8L, QA, QA, PROJECTS.get(2L)),
          getEnvironment(9L, "STG", STAGING, PROJECTS.get(2L)),
          getEnvironment(10L, "PRD", PRODUCTION, PROJECTS.get(2L)),
          getEnvironment(11L, "DEV", DEVELOPMENT, PROJECTS.get(3L)),
          getEnvironment(12L, "SMK", DEVELOPMENT_SMOKE, PROJECTS.get(3L)),
          getEnvironment(13L, QA, QA, PROJECTS.get(3L)),
          getEnvironment(14L, "STG", STAGING, PROJECTS.get(3L)),
          getEnvironment(15L, "PRD", PRODUCTION, PROJECTS.get(3L)),
          getEnvironment(16L, "DEV", DEVELOPMENT, PROJECTS.get(4L)),
          getEnvironment(17L, "SMK", DEVELOPMENT_SMOKE, PROJECTS.get(4L)),
          getEnvironment(18L, QA, QA, PROJECTS.get(4L)),
          getEnvironment(19L, "STG", STAGING, PROJECTS.get(4L)),
          getEnvironment(20L, "PRD", PRODUCTION, PROJECTS.get(4L)),
          getEnvironment(21L, "DEV", DEVELOPMENT, PROJECTS.get(5L)),
          getEnvironment(22L, "SMK", DEVELOPMENT_SMOKE, PROJECTS.get(5L)),
          getEnvironment(23L, QA, QA, PROJECTS.get(5L)),
          getEnvironment(24L, "STG", STAGING, PROJECTS.get(5L)),
          getEnvironment(25L, "PRD", PRODUCTION, PROJECTS.get(5L)))
      .collect(Collectors.toMap(EnvironmentDto::getId, r -> r));

  private final Map<Long, VersionDto> VERSIONS = Stream.of(
          getVersion(1L, "1.0", PROJECTS.get(1L)),
          getVersion(2L, "1.1", PROJECTS.get(1L)),
          getVersion(3L, "2-15", PROJECTS.get(1L)),
          getVersion(4L, "2021-02", PROJECTS.get(1L)),
          getVersion(5L, "20210203", PROJECTS.get(1L)),
          getVersion(6L, "v2021-03", PROJECTS.get(2L)),
          getVersion(7L, "v2021", PROJECTS.get(2L)),
          getVersion(8L, "2021", PROJECTS.get(2L)),
          getVersion(9L, "123", PROJECTS.get(2L)),
          getVersion(10L, "1.1", PROJECTS.get(2L)),
          getVersion(11L, "1.2", PROJECTS.get(3L)),
          getVersion(12L, "1.3", PROJECTS.get(3L)),
          getVersion(13L, "1.4", PROJECTS.get(3L)),
          getVersion(14L, "1.5", PROJECTS.get(3L)),
          getVersion(15L, "1.6", PROJECTS.get(3L)),
          getVersion(16L, "1.1", PROJECTS.get(4L)),
          getVersion(17L, "1.2", PROJECTS.get(4L)),
          getVersion(18L, "1.3", PROJECTS.get(4L)),
          getVersion(19L, "1.4", PROJECTS.get(4L)),
          getVersion(20L, "1.5", PROJECTS.get(4L)),
          getVersion(21L, "1.1", PROJECTS.get(5L)),
          getVersion(22L, "1.2", PROJECTS.get(5L)),
          getVersion(23L, "1.3", PROJECTS.get(5L)),
          getVersion(24L, "1.4", PROJECTS.get(5L)),
          getVersion(25L, "1.5", PROJECTS.get(5L)))
      .collect(Collectors.toMap(VersionDto::getId, r -> r));

  public ProjectDto getRandomProject() {
    return PROJECTS.values().stream().findAny().orElse(null);
  }

  public ProjectDto getProject() {
    return getProject(1);
  }

  public ProjectDto getProject(long id) {
    return PROJECTS.get(id);
  }

  public ProjectDto getProjectByCode(String code) {
    return PROJECTS.values().stream().filter(p -> code.equals(p.getCode())).findFirst().orElseThrow();
  }

  public UserDto getRandomUser() {
    return USERS.values().stream().findAny().orElse(null);
  }

  public UserDto getUser() {
    return getUser(1);
  }

  public UserDto getUser(long id) {
    return USERS.get(id);
  }

  public UserDto getUserByUsername(String username) {
    return USERS.values().stream().filter(u -> username.equals(u.getUsername()) || u.getAliases().stream().anyMatch(a -> username.equals(a.getAlias()))).findFirst().orElseThrow();
  }

  public EnvironmentDto getRandomEnvironment() {
    return ENVIRONMENTS.values().stream().findAny().orElse(null);
  }

  public EnvironmentDto getEnvironment() {
    return getEnvironment(1);
  }

  public EnvironmentDto getEnvironment(long id) {
    return ENVIRONMENTS.get(id);
  }

  public EnvironmentDto getEnvironmentByCode(String environment) {
    return ENVIRONMENTS.values().stream().filter(u -> environment.equals(u.getCode())).findFirst().orElseThrow();
  }

  public VersionDto getRandomVersion() {
    return VERSIONS.values().stream().findAny().orElse(null);
  }

  public VersionDto getVersion() {
    return getVersion(1);
  }

  public VersionDto getVersion(long id) {
    return VERSIONS.get(id);
  }

  private EnvironmentDto getEnvironment(long id, String code, String name, ProjectDto project) {
    return new EnvironmentDto().setId(id).setCode(code + project.getId()).setName(name + " environment for " + project.getName()).setProject(project.getCode());
  }

  private VersionDto getVersion(long id, String code, ProjectDto project) {
    return new VersionDto().setId(id).setCode(code + project.getId()).setName(code + " version for " + project.getName()).setProject(project.getCode());
  }

}