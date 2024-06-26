package org.catools.athena.core.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.rest.controller.ProjectController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerIT extends CoreControllerIT {

  @Autowired
  protected ProjectController projectController;

  @Test
  @Order(1)
  void saveShouldSaveProjectWhenValidDataProvided() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    ResponseEntity<Void> responseEntity = projectController.saveOrUpdate(projectDto);
    verifyProject(responseEntity, projectDto);
  }

  @Test
  @Order(10)
  void saveShouldUpdateProjectIfProjectWithTheSameCodeAlreadyExists() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto().setCode(PROJECT.getCode());
    ResponseEntity<Void> responseEntity = projectController.saveOrUpdate(projectDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());
    verifyProject(responseEntity, projectDto);
  }

  @Test
  @Order(3)
  void getProjectShallReturnProjectIfValidProjectCodeProvided() {
    ResponseEntity<ProjectDto> response = projectController.search(PROJECT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(PROJECT_DTO.getName()));
  }


  private void verifyProject(ResponseEntity<Void> responseEntity, ProjectDto projectDto) {
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    ProjectDto savedProject = projectController.getById(id).getBody();
    assertThat(savedProject, notNullValue());
    assertThat(savedProject.getCode(), equalTo(projectDto.getCode()));
    assertThat(savedProject.getName(), equalTo(projectDto.getName()));
  }
}