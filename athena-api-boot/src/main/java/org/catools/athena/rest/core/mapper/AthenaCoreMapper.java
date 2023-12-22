package org.catools.athena.rest.core.mapper;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(uses = {AthenaCoreMapperService.class})
public abstract class AthenaCoreMapper {
  public abstract Project projectDtoToProject(ProjectDto metadata);

  public abstract ProjectDto projectToProjectDto(Project metadata);

  @Mappings({
      @Mapping(source = "projectCode", target = "project"),
  })
  public abstract Environment environmentDtoToEnvironment(EnvironmentDto environment);

  @Mappings({
      @Mapping(source = "project.code", target = "projectCode"),
  })
  public abstract EnvironmentDto environmentToEnvironmentDto(Environment environment);

  public abstract User userDtoToUser(UserDto metadata);

  public abstract UserDto userToUserDto(User user);
}
