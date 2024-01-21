package org.catools.athena.rest.core.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.*;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.instancio.Instancio;

import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class CoreBuilder {
  public static UserDto buildUserDto() {
    return Instancio.of(UserDto.class)
        .ignore(field(UserDto::getId))
        .create();
  }

  public static User buildUser(UserDto userDto) {
    return new User()
        .setId(userDto.getId())
        .setName(userDto.getName());
  }

  public static ProjectDto buildProjectDto() {
    return Instancio.of(ProjectDto.class)
        .ignore(field(ProjectDto::getId))
        .generate(field(ProjectDto::getCode), gen -> gen.string().length(1, 5))
        .create();
  }

  public static Project buildProject(ProjectDto projectDto) {
    return new Project()
        .setId(projectDto.getId())
        .setCode(projectDto.getCode())
        .setName(projectDto.getName());
  }

  public static EnvironmentDto buildEnvironmentDto(ProjectDto project) {
    return Instancio.of(EnvironmentDto.class)
        .ignore(field(EnvironmentDto::getId))
        .generate(field(EnvironmentDto::getCode), gen -> gen.string().length(1, 5))
        .set(field(EnvironmentDto::getProject), project.getCode())
        .create();
  }

  public static Environment buildEnvironment(EnvironmentDto environmentDto, Project project) {
    return new Environment()
        .setId(environmentDto.getId())
        .setName(environmentDto.getName())
        .setCode(environmentDto.getCode())
        .setProject(project);
  }

  public static VersionDto buildVersionDto(ProjectDto project) {
    return Instancio.of(VersionDto.class)
        .ignore(field(VersionDto::getId))
        .generate(field(VersionDto::getName), gen -> gen.string().length(1, 5))
        .generate(field(VersionDto::getCode), gen -> gen.string().length(1, 5))
        .set(field(VersionDto::getProject), project.getCode())
        .create();
  }

  public static Version buildVersion(VersionDto versionDto, Project project) {
    return new Version()
        .setId(versionDto.getId())
        .setName(versionDto.getName())
        .setCode(versionDto.getCode())
        .setProject(project);
  }

  public static Set<MetadataDto> buildMetadataDto() {
    return Instancio.of(MetadataDto.class)
        .ignore(field(MetadataDto::getId))
        .generate(field(MetadataDto::getName), gen -> gen.string().length(1, 100))
        .generate(field(MetadataDto::getValue), gen -> gen.string().length(1, 2000))
        .stream()
        .limit(10)
        .collect(Collectors.toSet());
  }
}
