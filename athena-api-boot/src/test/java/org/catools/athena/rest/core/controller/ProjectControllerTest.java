package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerTest extends CoreControllerTest {

  @Test
  @Order(1)
  void saveShouldSaveProjectWhenValidDataProvided() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    ResponseEntity<Void> responseEntity = projectController.save(projectDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    ProjectDto savedProject = projectController.getProjectById(id).getBody();
    assertThat(savedProject, notNullValue());
    assertThat(savedProject.getCode(), equalTo(projectDto.getCode()));
    assertThat(savedProject.getName(), equalTo(projectDto.getName()));
  }

  @Test
  @Order(2)
  void saveShouldNotSaveProjectIfProjectAlreadyExists() {
    ResponseEntity<Void> responseEntity = projectController.save(PROJECT_DTO);
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