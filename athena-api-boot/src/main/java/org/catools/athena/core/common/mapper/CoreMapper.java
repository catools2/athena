package org.catools.athena.core.common.mapper;

import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.UserAlias;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserAliasDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
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

  AppVersion versionDtoToVersion(VersionDto version);

  @Mapping(source = "project.code", target = "project")
  VersionDto versionToVersionDto(AppVersion appVersion);
}
