package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.VersionDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Set;

import static org.catools.athena.utils.ConstraintViolationUtil.assertThrowsConstraintViolation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testcontainers.utility.Base58.randomString;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VersionControllerTest extends CoreControllerTest {

  @Test
  @Order(1)
  void saveVersionShouldSaveVersionIfAllFieldsAreProvided() {
    VersionDto versionDto = CoreBuilder.buildVersionDto(PROJECT_DTO);
    ResponseEntity<Void> responseEntity = versionController.saveVersion(versionDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    VersionDto savedEnv = versionController.getVersionById(id).getBody();
    assertThat(savedEnv, notNullValue());
    assertThat(savedEnv.getCode(), equalTo(versionDto.getCode()));
    assertThat(savedEnv.getName(), equalTo(versionDto.getName()));
  }

  @Test
  @Order(1)
  void saveVersionShouldNotSaveVersionIfProjectCodeIsNull() {
    VersionDto versionDto = CoreBuilder.buildVersionDto(PROJECT_DTO);
    versionDto.setProject(null);
    assertThrowsConstraintViolation(() -> versionController.saveVersion(versionDto),
        "project",
        "The version project must be provided.");

  }

  @Test
  @Order(1)
  void saveVersionShouldNotSaveVersionIfVersionCodeIsNull() {
    VersionDto versionDto = CoreBuilder.buildVersionDto(PROJECT_DTO);
    versionDto.setCode(null);
    assertThrowsConstraintViolation(() -> versionController.saveVersion(versionDto),
        "code",
        "The version code must be provided.");
  }

  @Test
  @Order(1)
  void saveVersionShouldNotSaveVersionIfVersionNameIsNull() {
    VersionDto versionDto = CoreBuilder.buildVersionDto(PROJECT_DTO);
    versionDto.setName(null);
    assertThrowsConstraintViolation(() -> versionController.saveVersion(versionDto),
        "name",
        "The version name must be provided.");
  }

  @Test
  @Order(2)
  void saveVersionShallNotSaveSameVersionTwice() {
    ResponseEntity<Void> responseEntity = versionController.saveVersion(VERSION_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));
    assertThat(responseEntity.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getVersionShallReturnVersionIfValidCodeProvided() {
    ResponseEntity<VersionDto> response = versionController.getVersionByCode(VERSION_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(VERSION_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(VERSION_DTO.getName()));
    assertThat(response.getBody().getProject(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(2)
  void getVersionShallReturnEmptyBodyIfInvalidCodeProvided() {
    ResponseEntity<VersionDto> response = versionController.getVersionByCode(randomString(10));
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getVersionShallReturnEmptyBodyIfProvidedCodeIsNull() {
    ResponseEntity<VersionDto> response = versionController.getVersionByCode(null);
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(3)
  void getVersionsShallReturnVersionsIfValidProjectCodeProvided() {
    ResponseEntity<Set<VersionDto>> response = versionController.getVersions(PROJECT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    VersionDto versionDto = response.getBody().stream().filter(p -> p.getCode().equals(VERSION_DTO.getCode())).findFirst().orElse(null);
    assertThat(versionDto, notNullValue());
    assertThat(versionDto.getCode(), equalTo(VERSION_DTO.getCode()));
    assertThat(versionDto.getName(), equalTo(VERSION_DTO.getName()));
    assertThat(versionDto.getProject(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(3)
  void getVersionsShallReturnEmptyBodyIfNotProjectFound() {
    ResponseEntity<Set<VersionDto>> response = versionController.getVersions(randomString(5));
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(3)
  void getVersionsShallReturnEmptyBodyIfProjectCodeIsNull() {
    ResponseEntity<Set<VersionDto>> response = versionController.getVersions(null);
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }
}