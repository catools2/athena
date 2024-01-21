package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Set;

import static org.catools.athena.utils.ConstraintViolationUtil.assertThrowsConstraintViolation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testcontainers.utility.Base58.randomString;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnvironmentControllerTest extends CoreControllerTest {

  private static ProjectDto PROJECT_DTO;
  private static EnvironmentDto ENVIRONMENT_DTO;

  @BeforeAll
  public void beforeAll() {
    ProjectDto project = CoreBuilder.buildProjectDto();
    projectController.saveProject(project);
    PROJECT_DTO = projectController.getProjectByCode(project.getCode()).getBody();
    assertThat(PROJECT_DTO, notNullValue());
    ENVIRONMENT_DTO = CoreBuilder.buildEnvironmentDto(PROJECT_DTO);
  }

  @Test
  @Order(1)
  void saveEnvironmentShouldSaveEnvironmentIfAllFieldsAreProvided() {
    ResponseEntity<Void> responseEntity = environmentController.saveEnvironment(ENVIRONMENT_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    EnvironmentDto savedEnv = environmentController.getEnvironmentById(id).getBody();
    assertThat(savedEnv, notNullValue());
    assertThat(savedEnv.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(savedEnv.getName(), equalTo(ENVIRONMENT_DTO.getName()));
  }

  @Test
  @Order(1)
  void saveEnvironmentShouldNotSaveEnvironmentIfProjectCodeIsNull() {
    EnvironmentDto versionDto = CoreBuilder.buildEnvironmentDto(PROJECT_DTO);
    versionDto.setProject(null);
    assertThrowsConstraintViolation(() -> environmentController.saveEnvironment(versionDto),
        "project",
        "The environment project must be provided.");

  }

  @Test
  @Order(1)
  void saveEnvironmentShouldNotSaveEnvironmentIfEnvironmentCodeIsNull() {
    EnvironmentDto versionDto = CoreBuilder.buildEnvironmentDto(PROJECT_DTO);
    versionDto.setCode(null);
    assertThrowsConstraintViolation(() -> environmentController.saveEnvironment(versionDto),
        "code",
        "The environment code must be provided.");
  }

  @Test
  @Order(1)
  void saveEnvironmentShouldNotSaveEnvironmentIfEnvironmentNameIsNull() {
    EnvironmentDto versionDto = CoreBuilder.buildEnvironmentDto(PROJECT_DTO);
    versionDto.setName(null);
    assertThrowsConstraintViolation(() -> environmentController.saveEnvironment(versionDto),
        "name",
        "The environment name must be provided.");
  }

  @Test
  @Order(2)
  void saveEnvironmentShallNotSaveSameEnvironmentTwice() {
    ResponseEntity<Void> responseEntity = environmentController.saveEnvironment(ENVIRONMENT_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));
    assertThat(responseEntity.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEnvironmentIfValidCodeProvided() {
    ResponseEntity<EnvironmentDto> response = environmentController.getEnvironmentByCode(ENVIRONMENT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(response.getBody().getProject(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEmptyBodyIfInvalidCodeProvided() {
    ResponseEntity<EnvironmentDto> response = environmentController.getEnvironmentByCode(randomString(10));
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEmptyBodyIfProvidedCodeIsNull() {
    ResponseEntity<EnvironmentDto> response = environmentController.getEnvironmentByCode(null);
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(3)
  void getEnvironmentsShallReturnEnvironmentsIfValidProjectCodeProvided() {
    ResponseEntity<Set<EnvironmentDto>> response = environmentController.getEnvironments();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    EnvironmentDto versionDto = response.getBody().stream().filter(p -> p.getCode().equals(ENVIRONMENT_DTO.getCode())).findFirst().orElse(null);
    assertThat(versionDto, notNullValue());
    assertThat(versionDto.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(versionDto.getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(versionDto.getProject(), equalTo(PROJECT_DTO.getCode()));
  }
}