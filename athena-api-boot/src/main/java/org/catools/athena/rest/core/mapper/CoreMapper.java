package org.catools.athena.rest.core.mapper;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(uses = {CoreMapperService.class})
public abstract class CoreMapper {
    public abstract Project projectDtoToProject(ProjectDto metadata);

    public abstract ProjectDto projectToProjectDto(Project metadata);

    public abstract Environment environmentDtoToEnvironment(EnvironmentDto environment);

    @Mappings({
            @Mapping(source = "project.code", target = "project"),
    })
    public abstract EnvironmentDto environmentToEnvironmentDto(Environment environment);

    public abstract User userDtoToUser(UserDto user);

    public abstract UserDto userToUserDto(User user);

    public abstract Version versionDtoToVersion(VersionDto version);

    @Mappings({
            @Mapping(source = "project.code", target = "project"),
    })
    public abstract VersionDto versionToVersionDto(Version version);
}
