package org.catools.athena.core.controller;

import feign.FeignException;
import feign.TypedResponse;
import org.apache.logging.log4j.util.Strings;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.feign.FeignUtils;
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
import static org.testcontainers.utility.Base58.randomString;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnvironmentControllerIT extends CoreControllerIT {

  @Test
  @Order(1)
  void saveShouldSaveEnvironmentIfAllFieldsAreProvided() {
    TypedResponse<Void> response = environmentFeignClient.save(environmentDto);
    verifyEnvironment(response, 201, environmentDto);
  }

  @Test
  @Order(2)
  void saveShallNotSaveSameEntityTwice() {
    EnvironmentDto environmentDto = CoreBuilder.buildEnvironmentDto(project.getCode()).setCode(this.environmentDto.getCode());
    try {
      environmentFeignClient.save(environmentDto);
    } catch (FeignException response) {
      assertThat(response.status(), equalTo(409));
    }
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEnvironmentIfValidCodeProvided() {
    TypedResponse<EnvironmentDto> response = environmentFeignClient.search(environmentDto.getCode());
    assertThat(response.status(), equalTo(200));
    FeignUtils.assertBodyEquals("Response is correct", response, """
        {
          "id" : 1,
          "code" : "DEV1",
          "name" : "Development environment for Administration",
          "project" : "ADM"
        }""");
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEmptyBodyIfInvalidCodeProvided() {
    TypedResponse<EnvironmentDto> response = environmentFeignClient.search(randomString(10));
    assertThat(response.status(), equalTo(204));
    assertThat(response.body(), nullValue());
  }

  @Test
  @Order(2)
  void getEnvironmentShallReturnEmptyBodyIfProvidedCodeIsEmpty() {
    TypedResponse<EnvironmentDto> response = environmentFeignClient.search(Strings.EMPTY);
    assertThat(response.status(), equalTo(204));
    assertThat(response.body(), nullValue());
  }

  @Test
  @Transactional
  void updateShouldUpdateEntityIfExists() {
    EnvironmentDto environmentDto = new EnvironmentDto(environment.getId(), environment.getCode(), RandomStringUtils.random(10), environment.getProject().getCode());
    TypedResponse<Void> response = environmentFeignClient.update(environmentDto);
    verifyEnvironment(response, 200, environmentDto);
  }

  @Test
  @Order(100)
  void updateShouldNotUpdateEntityIfExists() {
    try {
      EnvironmentDto environmentDto = new EnvironmentDto(10000L, environment.getCode(), environment.getName(), environment.getProject().getCode());
      environmentFeignClient.update(environmentDto);
    } catch (FeignException response) {
      assertThat(response.status(), equalTo(500));
    }
  }

  private void verifyEnvironment(TypedResponse<Void> response, int status, EnvironmentDto environmentDto) {
    assertThat(response.status(), equalTo(status));
    Long id = FeignUtils.getIdFromLocationHeader(response);
    EnvironmentDto savedEnv = environmentFeignClient.getById(id).body();
    assertThat(savedEnv, notNullValue());
    assertThat(savedEnv.getCode(), equalTo(environmentDto.getCode()));
    assertThat(savedEnv.getName(), equalTo(environmentDto.getName()));
  }
}