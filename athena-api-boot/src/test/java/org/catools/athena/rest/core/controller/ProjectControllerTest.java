package org.catools.athena.rest.core.controller;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerTest extends BaseCoreControllerTest {

  private static ProjectDto PROJECT_DTO;

  @BeforeAll
  public void beforeAll() {
    PROJECT_DTO = CoreBuilder.buildProjectDto();
  }

  @Test
  @Order(1)
  void saveProjectShouldSaveProjectWhenValidDataProvided() {
    ResponseEntity<Void> responseEntity = projectController.saveProject(PROJECT_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    ProjectDto savedProject = projectController.getProjectById(id).getBody();
    assertThat(savedProject, notNullValue());
    assertThat(savedProject.getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(savedProject.getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  @Order(1)
  void saveProjectShouldNotSaveProjectIfProjectCodeIsNull() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    projectDto.setCode(null);
    assertThrowsConstraintViolation(() -> projectController.saveProject(projectDto),
        "code",
        "The project code must be provided.");
  }

  @Test
  @Order(1)
  void saveProjectShouldNotSaveProjectIfProjectNameIsNull() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    projectDto.setName(null);
    assertThrowsConstraintViolation(() -> projectController.saveProject(projectDto),
        "name",
        "The project name must be provided.");
  }

  @Test
  @Order(2)
  void saveProjectShouldNotSaveProjectIfProjectAlreadyExists() {
    ResponseEntity<Void> responseEntity = projectController.saveProject(PROJECT_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));
    assertThat(responseEntity.getBody(), nullValue());
  }

  @Test
  @Order(3)
  void getProjectShallReturnProjectIfValidProjectCodeProvided() {
    ResponseEntity<ProjectDto> response = projectController.getProjectByCode(PROJECT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  @Order(4)
  void getProjectsShallReturnProjects() {
    ResponseEntity<Set<ProjectDto>> response = projectController.getProjects();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    ProjectDto projectDto = response.getBody().stream().filter(p -> p.getCode().equals(PROJECT_DTO.getCode())).findFirst().orElse(null);
    assertThat(projectDto, notNullValue());
    assertThat(projectDto.getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(projectDto.getName(), equalTo(PROJECT_DTO.getName()));
  }
}