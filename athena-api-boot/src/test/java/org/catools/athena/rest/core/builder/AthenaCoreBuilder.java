package org.catools.athena.rest.core.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.instancio.Instancio;

import static org.instancio.Select.field;

@UtilityClass
public class AthenaCoreBuilder {
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
        .set(field(EnvironmentDto::getProjectCode), project.getCode())
        .create();
  }

  public static Environment buildEnvironment(EnvironmentDto environmentDto, Project project) {
    return new Environment()
        .setId(environmentDto.getId())
        .setName(environmentDto.getName())
        .setCode(environmentDto.getCode())
        .setProject(project);
  }
}
