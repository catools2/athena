package org.catools.athena.core.controller;

import feign.TypedResponse;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.feign.PageResponse;
import org.catools.athena.model.core.EnvironmentDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Integration tests for Environment entity filtering and pagination.
 * Tests the getAll() method with various filter combinations including project relationships.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnvironmentFilteringIT extends CoreControllerIT {

  @Test
  @Order(1)
  void setupTestData() {
    // Create environments for filtering tests
    for (int i = 0; i < 5; i++) {
      EnvironmentDto environmentDto = CoreBuilder.buildEnvironmentDto(projectDto.getCode());
      environmentFeignClient.save(environmentDto);
    }
  }

  @Test
  @Order(2)
  void getAllEnvironmentsShouldReturnAllEnvironmentsWhenNoFiltersProvided() {
    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(5));
    assertThat(page.getTotalElements(), greaterThanOrEqualTo(5L));
  }

  @Test
  @Order(3)
  void getAllEnvironmentsShouldFilterByCode() {
    TypedResponse<PageResponse<EnvironmentDto>> allResponse = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);
    String targetCode = allResponse.body().getContent().get(0).getCode();

    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", targetCode, null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getCode(), equalTo(targetCode));
  }

  @Test
  @Order(4)
  void getAllEnvironmentsShouldFilterByName() {
    TypedResponse<PageResponse<EnvironmentDto>> allResponse = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);
    String targetName = allResponse.body().getContent().get(0).getName();

    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, targetName, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getName(), equalTo(targetName));
  }

  @Test
  @Order(5)
  void getAllEnvironmentsShouldFilterByProject() {
    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, projectDto.getCode());

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getProject(), equalTo(projectDto.getCode()));
  }

  @Test
  @Order(6)
  void getAllEnvironmentsShouldFilterByCodeAndName() {
    TypedResponse<PageResponse<EnvironmentDto>> allResponse = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);
    EnvironmentDto target = allResponse.body().getContent().get(0);

    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", target.getCode(), target.getName(), null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getCode(), equalTo(target.getCode()));
    assertThat(page.getContent().get(0).getName(), equalTo(target.getName()));
  }

  @Test
  @Order(7)
  void getAllEnvironmentsShouldFilterByCodeAndProject() {
    TypedResponse<PageResponse<EnvironmentDto>> allResponse = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);
    EnvironmentDto target = allResponse.body().getContent().get(0);

    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", target.getCode(), null, target.getProject());

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
  }

  @Test
  @Order(8)
  void getAllEnvironmentsShouldFilterByNameAndProject() {
    TypedResponse<PageResponse<EnvironmentDto>> allResponse = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);
    EnvironmentDto target = allResponse.body().getContent().get(0);

    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, target.getName(), target.getProject());

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
  }

  @Test
  @Order(9)
  void getAllEnvironmentsShouldFilterByAllThreeFilters() {
    TypedResponse<PageResponse<EnvironmentDto>> allResponse = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);
    EnvironmentDto target = allResponse.body().getContent().get(0);

    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", target.getCode(), target.getName(), target.getProject());

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getCode(), equalTo(target.getCode()));
    assertThat(page.getContent().get(0).getName(), equalTo(target.getName()));
    assertThat(page.getContent().get(0).getProject(), equalTo(target.getProject()));
  }

  @Test
  @Order(10)
  void getAllEnvironmentsShouldSupportPagination() {
    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 10, "code", "ASC", null, null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getPageNumber(), equalTo(0));
    assertThat(page.getSize(), equalTo(10));
  }

  @Test
  @Order(11)
  void getAllEnvironmentsShouldSortByCode() {
    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", null, null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());

    if (page.getContent().size() >= 2) {
      for (int i = 0; i < page.getContent().size() - 1; i++) {
        String current = page.getContent().get(i).getCode();
        String next = page.getContent().get(i + 1).getCode();
        assertThat(current.compareToIgnoreCase(next) <= 0, equalTo(true));
      }
    }
  }

  @Test
  @Order(12)
  void getAllEnvironmentsShouldReturnEmptyWhenFilterMatchesNothing() {
    TypedResponse<PageResponse<EnvironmentDto>> response = environmentFeignClient.getAllEnvironments(0, 20, "code", "ASC", "NonExistentCode123456", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<EnvironmentDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), equalTo(0));
    assertThat(page.getTotalElements(), equalTo(0L));
  }
}

