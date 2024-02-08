package org.catools.athena.core.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.EnvironmentDto;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testcontainers.utility.Base58.randomString;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnvironmentControllerTest extends CoreControllerTest {

  private static EnvironmentDto ENVIRONMENT_DTO;

  @BeforeAll
  public void beforeAll() {
    if (ENVIRONMENT_DTO == null)
      ENVIRONMENT_DTO = CoreBuilder.buildEnvironmentDto(PROJECT_DTO);
  }

  @Test
  @Order(1)
  void saveShouldSaveEnvironmentIfAllFieldsAreProvided() {
    ResponseEntity<Void> responseEntity = environmentController.save(ENVIRONMENT_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    EnvironmentDto savedEnv = environmentController.getById(id).getBody();
    assertThat(savedEnv, notNullValue());
    assertThat(savedEnv.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(savedEnv.getName(), equalTo(ENVIRONMENT_DTO.getName()));
  }

  @Test
  @Order(2)
  void saveShallNotSaveSameEnvironmentTwice() {
    ResponseEntity<Void> responseEntity = environmentController.save(ENVIRONMENT_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));
    assertThat(responseEntity.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEnvironmentIfValidCodeProvided() {
    ResponseEntity<EnvironmentDto> response = environmentController.getByCode(ENVIRONMENT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(response.getBody().getProject(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEmptyBodyIfInvalidCodeProvided() {
    ResponseEntity<EnvironmentDto> response = environmentController.getByCode(randomString(10));
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEmptyBodyIfProvidedCodeIsNull() {
    ResponseEntity<EnvironmentDto> response = environmentController.getByCode(null);
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }
}