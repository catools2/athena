package org.catools.athena.core.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.core.rest.controller.VersionController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testcontainers.utility.Base58.randomString;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VersionControllerIT extends CoreControllerIT {

  @Autowired
  protected VersionController versionController;

  @Test
  @Order(1)
  void saveShouldSaveVersionIfAllFieldsAreProvided() {
    VersionDto versionDto = CoreBuilder.buildVersionDto(PROJECT_DTO);
    ResponseEntity<Void> responseEntity = versionController.save(versionDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    VersionDto savedEnv = versionController.getById(id).getBody();
    assertThat(savedEnv, notNullValue());
    assertThat(savedEnv.getCode(), equalTo(versionDto.getCode()));
    assertThat(savedEnv.getName(), equalTo(versionDto.getName()));
  }

  @Test
  @Order(2)
  void saveShallNotSaveSameVersionTwice() {
    ResponseEntity<Void> responseEntity = versionController.save(VERSION_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));
    assertThat(responseEntity.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getVersionShallReturnVersionIfValidCodeProvided() {
    ResponseEntity<VersionDto> response = versionController.search(VERSION_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(VERSION_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(VERSION_DTO.getName()));
    assertThat(response.getBody().getProject(), equalTo(PROJECT_DTO.getCode()));
  }

  @Test
  @Order(2)
  void getVersionShallReturnEmptyBodyIfInvalidCodeProvided() {
    ResponseEntity<VersionDto> response = versionController.search(randomString(10));
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getVersionShallReturnEmptyBodyIfProvidedCodeIsNull() {
    ResponseEntity<VersionDto> response = versionController.search(null);
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

}