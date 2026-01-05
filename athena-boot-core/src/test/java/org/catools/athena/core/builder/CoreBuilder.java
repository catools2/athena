package org.catools.athena.core.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.instancio.Instancio;

import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class CoreBuilder {
  public static UserDto buildUserDto() {
    return Instancio.of(UserDto.class)
        .ignore(field(UserDto::getId))
        .ignore(field(UserAliasDto::getId))
        .create();
  }

  public static UserDto buildUserDto(User user) {
    return new UserDto()
        .setId(user.getId())
        .setUsername(user.getUsername())
        .setAliases(user.getAliases().stream().map(a -> new UserAliasDto(a.getId(), a.getAlias())).collect(Collectors.toSet()));
  }

  public static User buildUser(UserDto userDto) {
    final User user = new User(userDto.getId(), userDto.getUsername());

    for (UserAliasDto alias : userDto.getAliases()) {
      user.addAlias(alias.getId(), alias.getAlias());
    }

    return user;
  }

  public static ProjectDto buildProjectDto() {
    return Instancio.of(ProjectDto.class)
        .ignore(field(ProjectDto::getId))
        .generate(field(ProjectDto::getCode), gen -> gen.string().length(5, 10))
        .create();
  }

  public static ProjectDto buildProjectDto(Project project) {
    return new ProjectDto(project.getCode(), project.getName()).setId(project.getId());
  }

  public static Project buildProject(ProjectDto projectDto) {
    return new Project()
        .setId(projectDto.getId())
        .setCode(projectDto.getCode())
        .setName(projectDto.getName());
  }

  public static EnvironmentDto buildEnvironmentDto(String projectCode) {
    return Instancio.of(EnvironmentDto.class)
        .ignore(field(EnvironmentDto::getId))
        .generate(field(EnvironmentDto::getCode), gen -> gen.string().length(5, 10))
        .set(field(EnvironmentDto::getProject), projectCode)
        .create();
  }

  public static EnvironmentDto buildEnvironmentDto(Environment environment) {
    return new EnvironmentDto(
        environment.getId(),
        environment.getCode(),
        environment.getName(),
        environment.getProject().getCode()
    );
  }

  public static Environment buildEnvironment(EnvironmentDto environmentDto, Project project) {
    return new Environment()
        .setId(environmentDto.getId())
        .setName(environmentDto.getName())
        .setCode(environmentDto.getCode())
        .setProject(project);
  }

  public static VersionDto buildVersionDto(String projectCode) {
    return Instancio.of(VersionDto.class)
        .ignore(field(VersionDto::getId))
        .generate(field(VersionDto::getName), gen -> gen.string().length(5, 10))
        .generate(field(VersionDto::getCode), gen -> gen.string().length(5, 10))
        .set(field(VersionDto::getProject), projectCode)
        .create();
  }

  public static AppVersion buildVersion(VersionDto versionDto, Project project) {
    return new AppVersion()
        .setId(versionDto.getId())
        .setName(versionDto.getName())
        .setCode(versionDto.getCode())
        .setProject(project);
  }

  public static VersionDto buildVersionDto(AppVersion version) {
    return new VersionDto()
        .setId(version.getId())
        .setName(version.getName())
        .setCode(version.getCode())
        .setProject(version.getProject().getCode());
  }

  public static Set<MetadataDto> buildMetadataDto() {
    return Instancio.of(MetadataDto.class)
        .ignore(field(MetadataDto::getId))
        .generate(field(MetadataDto::getName), gen -> gen.string().length(1, 100))
        .generate(field(MetadataDto::getValue), gen -> gen.string().length(1, 2000))
        .stream()
        .limit(4)
        .collect(Collectors.toSet());
  }
}
