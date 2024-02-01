package org.catools.athena.rest.core.mapper;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.service.EnvironmentService;
import org.catools.athena.rest.core.service.ProjectService;
import org.catools.athena.rest.core.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


class CoreMapperTest extends AthenaBaseTest {
  private static ProjectDto PROJECT_DTO;
  private static Project PROJECT;
  private static EnvironmentDto ENVIRONMENT_DTO;
  private static Environment ENVIRONMENT;

  @Autowired
  CoreMapper coreMapper;

  @Autowired
  UserService athenaCoreService;

  @Autowired
  ProjectService projectService;

  @Autowired
  EnvironmentService environmentService;

  @BeforeAll
  public void beforeAll() {
    UserDto userDto = CoreBuilder.buildUserDto();
    userDto.setId(athenaCoreService.save(userDto).getId());

    PROJECT_DTO = CoreBuilder.buildProjectDto();
    PROJECT_DTO.setId(projectService.save(PROJECT_DTO).getId());
    PROJECT = CoreBuilder.buildProject(PROJECT_DTO);

    ENVIRONMENT_DTO = CoreBuilder.buildEnvironmentDto(PROJECT_DTO);
    ENVIRONMENT_DTO.setId(environmentService.save(ENVIRONMENT_DTO).getId());
    ENVIRONMENT = CoreBuilder.buildEnvironment(ENVIRONMENT_DTO, PROJECT);
  }

  @Test
  void projectDtoToProject() {
    final Project project = coreMapper.projectDtoToProject(PROJECT_DTO);
    assertThat(project.getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(project.getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  void projectToProjectDto() {
    final ProjectDto projectDto = coreMapper.projectToProjectDto(PROJECT);
    assertThat(PROJECT.getId(), equalTo(projectDto.getId()));
    assertThat(PROJECT.getCode(), equalTo(projectDto.getCode()));
    assertThat(PROJECT.getName(), equalTo(projectDto.getName()));
  }

  @Test
  void environmentDtoToEnvironment() {
    final Environment environment = coreMapper.environmentDtoToEnvironment(ENVIRONMENT_DTO);
    assertThat(environment.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(environment.getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(environment.getProject().getCode(), equalTo(ENVIRONMENT_DTO.getProject()));
  }

  @Test
  void environmentToEnvironmentDto() {
    final EnvironmentDto environmentDto = coreMapper.environmentToEnvironmentDto(ENVIRONMENT);
    assertThat(ENVIRONMENT.getId(), equalTo(environmentDto.getId()));
    assertThat(ENVIRONMENT.getCode(), equalTo(environmentDto.getCode()));
    assertThat(ENVIRONMENT.getName(), equalTo(environmentDto.getName()));
    assertThat(ENVIRONMENT.getProject().getCode(), equalTo(environmentDto.getProject()));
  }

  @Test
  void userDtoToUser() {
    final UserDto userDto = new UserDto();
    userDto.setUsername("UserName");

    // when
    final User user = coreMapper.userDtoToUser(userDto);

    // then
    assertThat(userDto.getUsername(), equalTo(user.getUsername()));
  }

  @Test
  void userToUserDto() {
    final User user = new User();
    user.setId(1L);
    user.setUsername("UserName");

    // when
    final UserDto actualDto = coreMapper.userToUserDto(user);

    // then
    assertThat(actualDto.getUsername(), equalTo(user.getUsername()));
  }
}