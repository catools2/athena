package org.catools.athena.core.controller;

import feign.TypedResponse;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.feign.PageResponse;
import org.catools.athena.model.core.ProjectDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Integration tests for Project entity filtering and pagination.
 * Tests the getAll() method with various filter combinations.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectFilteringIT extends CoreControllerIT {

  @Test
  @Order(1)
  void setupTestData() {
    // Create multiple projects for filtering tests
    for (int i = 0; i < 5; i++) {
      ProjectDto projectDto = CoreBuilder.buildProjectDto();
      projectFeignClient.save(projectDto);
    }
  }

  @Test
  @Order(2)
  void getAllProjectsShouldReturnAllProjectsWhenNoFiltersProvided() {
    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 20, "code", "ASC", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(5));
    assertThat(page.getTotalElements(), greaterThanOrEqualTo(5L));
  }

  @Test
  @Order(3)
  void getAllProjectsShouldFilterByCode() {
    TypedResponse<PageResponse<ProjectDto>> allResponse = projectFeignClient.getAllProjects(0, 20, "code", "ASC", null, null);
    String targetCode = allResponse.body().getContent().get(0).getCode();

    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 20, "code", "ASC", targetCode, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getCode(), equalTo(targetCode));
  }

  @Test
  @Order(4)
  void getAllProjectsShouldFilterByName() {
    TypedResponse<PageResponse<ProjectDto>> allResponse = projectFeignClient.getAllProjects(0, 20, "code", "ASC", null, null);
    String targetName = allResponse.body().getContent().get(0).getName();

    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 20, "code", "ASC", null, targetName);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getName(), equalTo(targetName));
  }

  @Test
  @Order(5)
  void getAllProjectsShouldFilterByBothCodeAndName() {
    TypedResponse<PageResponse<ProjectDto>> allResponse = projectFeignClient.getAllProjects(0, 20, "code", "ASC", null, null);
    ProjectDto target = allResponse.body().getContent().get(0);
    String targetCode = target.getCode();
    String targetName = target.getName();

    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 20, "code", "ASC", targetCode, targetName);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getCode(), equalTo(targetCode));
    assertThat(page.getContent().get(0).getName(), equalTo(targetName));
  }

  @Test
  @Order(6)
  void getAllProjectsShouldSupportPaginationWithDefaultPageSize() {
    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 10, "code", "ASC", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getPageNumber(), equalTo(0));
    assertThat(page.getSize(), equalTo(10));
  }

  @Test
  @Order(7)
  void getAllProjectsShouldSupportPaginationWithSecondPage() {
    TypedResponse<PageResponse<ProjectDto>> firstPage = projectFeignClient.getAllProjects(0, 3, "code", "ASC", null, null);
    assertThat(firstPage.body().getContent().size(), greaterThanOrEqualTo(1));

    if (firstPage.body().getTotalElements() > 3) {
      TypedResponse<PageResponse<ProjectDto>> secondPage = projectFeignClient.getAllProjects(1, 3, "code", "ASC", null, null);

      assertThat(secondPage.status(), equalTo(200));
      PageResponse<ProjectDto> page = secondPage.body();
      assertThat(page, notNullValue());
      assertThat(page.getPageNumber(), equalTo(1));
    }
  }

  @Test
  @Order(8)
  void getAllProjectsShouldSortByCode() {
    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 20, "code", "ASC", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(2));

    // Verify sorting is applied
    for (int i = 0; i < page.getContent().size() - 1; i++) {
      String current = page.getContent().get(i).getCode();
      String next = page.getContent().get(i + 1).getCode();
      assertThat(current.compareToIgnoreCase(next) <= 0, equalTo(true));
    }
  }

  @Test
  @Order(9)
  void getAllProjectsShouldReturnEmptyWhenFilterMatchesNoProjects() {
    TypedResponse<PageResponse<ProjectDto>> response = projectFeignClient.getAllProjects(0, 20, "code", "ASC", "NonExistentCode123456", null);

    assertThat(response.status(), equalTo(200));
    PageResponse<ProjectDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), equalTo(0));
    assertThat(page.getTotalElements(), equalTo(0L));
  }
}

