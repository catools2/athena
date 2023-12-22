package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaEnvironmentControllerTest extends AthenaCoreControllerTest {

  private static ProjectDto PROJECT_DTO;
  private static EnvironmentDto ENVIRONMENT_DTO;

  @BeforeAll
  public void beforeAll() {
    PROJECT_DTO = athenaProjectController.saveProject(AthenaCoreBuilder.buildProjectDto()).getBody();
    assertThat(PROJECT_DTO, notNullValue());
    ENVIRONMENT_DTO = AthenaCoreBuilder.buildEnvironmentDto(PROJECT_DTO);
  }

  @Test
  @Order(1)
  void saveEnvironment() {
    ResponseEntity<EnvironmentDto> response = athenaEnvironmentController.saveEnvironment(ENVIRONMENT_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
    assertThat(response.getBody().getProjectCode(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(2)
  void getEnvironment() {
    ResponseEntity<EnvironmentDto> response = athenaEnvironmentController.getEnvironment(ENVIRONMENT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(response.getBody().getProjectCode(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(3)
  void getEnvironments() {
    ResponseEntity<List<EnvironmentDto>> response = athenaEnvironmentController.getEnvironments();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    EnvironmentDto environmentDto = response.getBody().stream().filter(p -> p.getCode().equals(ENVIRONMENT_DTO.getCode())).findFirst().orElse(null);
    assertThat(environmentDto, notNullValue());
    assertThat(environmentDto.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(environmentDto.getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(environmentDto.getProjectCode(), equalTo(PROJECT_DTO.getCode()));
  }
}