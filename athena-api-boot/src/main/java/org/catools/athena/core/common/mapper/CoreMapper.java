package org.catools.athena.core.common.mapper;

import org.catools.athena.core.common.entity.*;
import org.catools.athena.core.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {CoreMapperService.class})
public interface CoreMapper {
  Project projectDtoToProject(ProjectDto metadata);

  ProjectDto projectToProjectDto(Project metadata);

  Environment environmentDtoToEnvironment(EnvironmentDto environment);

  @Mapping(source = "project.code", target = "project")
  EnvironmentDto environmentToEnvironmentDto(Environment environment);

  User userDtoToUser(UserDto user);

  UserDto userToUserDto(User user);

  UserAlias userAliasDtoToUserAlias(UserAliasDto alias);

  UserAliasDto userAliasToUserAliasDto(UserAlias alias);

  Version versionDtoToVersion(VersionDto version);

  @Mapping(source = "project.code", target = "project")
  VersionDto versionToVersionDto(Version version);
}
