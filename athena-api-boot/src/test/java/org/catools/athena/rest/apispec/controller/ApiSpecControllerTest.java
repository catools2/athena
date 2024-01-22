package org.catools.athena.rest.apispec.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.builder.ApiSpecBuilder;
import org.catools.athena.rest.core.controller.CoreControllerTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  ApiSpecController apiSpecController;

  @Test
  @Order(1)
  void shallSaveOpenApiSpecificationInOriginalJsonFormat() throws IOException {
    File resource = ResourceUtils.getFile("src/test/resources/testdata/openApiSpec.json");
    JsonElement openApiSpec = JsonParser.parseString(Files.readString(resource.toPath()));
    ResponseEntity<Void> response = apiSpecController.saveOpenApiSpec(openApiSpec, OPEN_API_SPEC_NAME, PROJECT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(1)
  void shallSaveOpenApiSpecificationInDtoFormat() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    ResponseEntity<Void> response = apiSpecController.saveApiSpec(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallNotSaveOpenApiSpecificationIfSpecificationWithTheSameNameExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    ResponseEntity<Void> response = apiSpecController.saveApiSpec(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(208));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
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
    ResponseEntity<ApiSpecDto> response = apiSpecController.getApiSpecByProjectCodeAndName(PROJECT_DTO.getCode(), OPEN_API_SPEC_NAME);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(OPEN_API_SPEC_NAME));
    assertThat(response.getBody().getProject(), equalTo(PROJECT_DTO.getCode()));
  }
}