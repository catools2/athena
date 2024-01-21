package org.catools.athena.rest.apispec.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.apispec.builder.ApiSpecBuilder;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.controller.CoreControllerTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiSpecControllerTest extends CoreControllerTest {
  private final static String OPEN_API_SPEC_NAME = "OpenApi";
  private static ProjectDto PROJECT1_DTO;
  private static ProjectDto PROJECT2_DTO;

  @Autowired
  ApiSpecController apiSpecController;

  @BeforeAll
  public void beforeAll() {
    ProjectDto project = CoreBuilder.buildProjectDto();
    projectController.saveProject(project);
    PROJECT1_DTO = projectController.getProjectByCode(project.getCode()).getBody();
    assertThat(PROJECT1_DTO, notNullValue());

    ProjectDto project2 = CoreBuilder.buildProjectDto();
    projectController.saveProject(project2);
    PROJECT2_DTO = projectController.getProjectByCode(project2.getCode()).getBody();
    assertThat(PROJECT2_DTO, notNullValue());
  }

  @Test
  @Order(1)
  void shallSaveOpenApiSpecificationInOriginalJsonFormat() throws IOException {
    File resource = ResourceUtils.getFile("src/test/resources/testdata/openApiSpec.json");
    JsonElement openApiSpec = JsonParser.parseString(Files.readString(resource.toPath()));


    ResponseEntity<Void> response = apiSpecController.saveOpenApiSpec(openApiSpec, OPEN_API_SPEC_NAME, PROJECT1_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(1)
  void shallSaveOpenApiSpecificationInDtoFormat() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT1_DTO.getCode());


    ResponseEntity<Void> response = apiSpecController.saveApiSpec(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallNotSaveOpenApiSpecificationIfSpecificationWithTheSameNameExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT1_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);


    ResponseEntity<Void> response = apiSpecController.saveApiSpec(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(208));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  @Transactional
  void shallSaveOpenApiSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT2_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);


    ResponseEntity<Void> response = apiSpecController.saveApiSpec(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidIdProvided() {

    ResponseEntity<ApiSpecDto> response = apiSpecController.getApiSpecById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
    assertThat(response.getBody().getProject(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    ResponseEntity<ApiSpecDto> response = apiSpecController.getApiSpecByProjectCodeAndName(PROJECT1_DTO.getCode(), OPEN_API_SPEC_NAME);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(OPEN_API_SPEC_NAME));
    assertThat(response.getBody().getProject(), equalTo(PROJECT1_DTO.getCode()));
  }
}