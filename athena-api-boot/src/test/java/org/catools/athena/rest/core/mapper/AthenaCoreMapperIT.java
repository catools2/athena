package org.catools.athena.rest.core.mapper;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.service.AthenaCoreService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class AthenaCoreMapperIT extends AthenaBaseTest {
  private static ProjectDto PROJECT_DTO;
  private static Project PROJECT;
  private static EnvironmentDto ENVIRONMENT_DTO;
  private static Environment ENVIRONMENT;

  @Autowired
  AthenaCoreMapper coreMapper;

  @Autowired
  AthenaCoreService athenaCoreService;

  @BeforeAll
  public void beforeAll() {
    UserDto userDto = AthenaCoreBuilder.buildUserDto();
    userDto.setId(athenaCoreService.saveUser(userDto).getId());

    PROJECT_DTO = AthenaCoreBuilder.buildProjectDto();
    PROJECT_DTO.setId(athenaCoreService.saveProject(PROJECT_DTO).getId());
    PROJECT = AthenaCoreBuilder.buildProject(PROJECT_DTO);

    ENVIRONMENT_DTO = AthenaCoreBuilder.buildEnvironmentDto(PROJECT_DTO);
    ENVIRONMENT_DTO.setId(athenaCoreService.saveEnvironment(ENVIRONMENT_DTO).getId());
    ENVIRONMENT = AthenaCoreBuilder.buildEnvironment(ENVIRONMENT_DTO, PROJECT);
  }

  @Test
  public void projectDtoToProject() {
    final Project project = coreMapper.projectDtoToProject(PROJECT_DTO);
    assertThat(project.getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(project.getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  public void projectToProjectDto() {
    final ProjectDto projectDto = coreMapper.projectToProjectDto(PROJECT);
    assertThat(PROJECT.getId(), equalTo(projectDto.getId()));
    assertThat(PROJECT.getCode(), equalTo(projectDto.getCode()));
    assertThat(PROJECT.getName(), equalTo(projectDto.getName()));
  }

  @Test
  public void environmentDtoToEnvironment() {
    final Environment environment = coreMapper.environmentDtoToEnvironment(ENVIRONMENT_DTO);
    assertThat(environment.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(environment.getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(environment.getProject().getCode(), equalTo(ENVIRONMENT_DTO.getProjectCode()));
  }

  @Test
  public void environmentToEnvironmentDto() {
    final EnvironmentDto environmentDto = coreMapper.environmentToEnvironmentDto(ENVIRONMENT);
    assertThat(ENVIRONMENT.getId(), equalTo(environmentDto.getId()));
    assertThat(ENVIRONMENT.getCode(), equalTo(environmentDto.getCode()));
    assertThat(ENVIRONMENT.getName(), equalTo(environmentDto.getName()));
    assertThat(ENVIRONMENT.getProject().getCode(), equalTo(environmentDto.getProjectCode()));
  }

  @Test
  public void userDtoToUser() {
    final UserDto userDto = new UserDto();
    userDto.setName("UserName");

    // when
    final User user = coreMapper.userDtoToUser(userDto);

    // then
    assertThat(userDto.getName(), equalTo(user.getName()));
  }

  @Test
  public void userToUserDto() {
    final User user = new User();
    user.setId(1L);
    user.setName("UserName");

    // when
    final UserDto actualDto = coreMapper.userToUserDto(user);

    // then
    assertThat(actualDto.getName(), equalTo(user.getName()));
  }
}