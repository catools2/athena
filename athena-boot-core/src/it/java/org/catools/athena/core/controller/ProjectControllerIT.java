package org.catools.athena.core.controller;

import feign.FeignException;
import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.feign.FeignAssertHelper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerIT extends CoreControllerIT {

  @Test
  @Order(1)
  void saveShouldSaveProjectWhenValidDataProvided() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    TypedResponse<Void> response = projectFeignClient.save(projectDto);
    verifyProject(response, 201, projectDto);
  }

  @Test
  @Order(10)
  void saveShallNotSaveSameEntityTwice() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto().setCode(project.getCode()).setName(project.getName());
    TypedResponse<Void> response = projectFeignClient.save(projectDto);
    assertThat(response.status(), equalTo(208));
    Long id = FeignUtils.getIdFromLocationHeader(response);
    assertThat(id, equalTo(project.getId()));
    assertThat(response.body(), nullValue());
  }

  @Test
  @Order(3)
  void getProjectShallReturnProjectIfValidProjectCodeProvided() {
    TypedResponse<ProjectDto> response = projectFeignClient.search(projectDto.getCode());
    assertThat(response.status(), equalTo(200));
    FeignAssertHelper.assertBodyEquals("Response is correct", response, """
        {
          "id" : 1,
          "code" : "ADM",
          "name" : "Administration"
        }""");
  }

  @Test
  @Transactional
  void updateShouldUpdateEntityIfExists() {
    ProjectDto projectDto = new ProjectDto(project.getId(), project.getCode(), RandomStringUtils.random(10));
    TypedResponse<Void> response = projectFeignClient.update(projectDto);
    verifyProject(response, 200, projectDto);
  }

  @Test
  @Order(100)
  void updateShouldNotUpdateEntityIfExists() {
    try {
      ProjectDto projectDto = new ProjectDto(10000L, project.getCode(), project.getName());
      projectFeignClient.update(projectDto);
    } catch (FeignException response) {
      assertThat(response.status(), equalTo(500));
    }
  }

  private void verifyProject(TypedResponse<Void> response, int status, ProjectDto projectDto) {
    assertThat(response.status(), equalTo(status));
    Long id = FeignUtils.getIdFromLocationHeader(response);
    ProjectDto savedProject = projectFeignClient.getById(id).body();
    assertThat(savedProject, notNullValue());
    assertThat(savedProject.getCode(), equalTo(projectDto.getCode()));
    assertThat(savedProject.getName(), equalTo(projectDto.getName()));
  }
}