package org.catools.athena.apispec.controller;

import org.catools.athena.apispec.builder.ApiSpecBuilder;
import org.catools.athena.apispec.common.entity.ApiPath;
import org.catools.athena.apispec.common.entity.ApiSpec;
import org.catools.athena.apispec.common.repository.ApiSpecRepository;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.apispec.rest.controller.ApiSpecController;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.controller.CoreControllerTest;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiSpecControllerTest extends CoreControllerTest {
  private final static String OPEN_API_SPEC_NAME = "OpenApi";

  @Autowired
  ApiSpecController apiSpecController;

  @Autowired
  ApiSpecRepository apiSpecRepository;

  @Test
  @Order(1)
  void postMethodShallSaveNewlyProvidedApiSpecDto() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    saveApiSpec(apiSpecDto);
  }

  @Test
  @Order(2)
  void postMethodShallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    saveApiSpec(apiSpecDto);
  }

  @Test
  @Order(2)
  void postMethodShallSaveSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT2_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    saveApiSpec(apiSpecDto);
  }

  @Test
  @Order(3)
  void postMethodShallNotSaveApiSpecWithoutName() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(null);
    post(apiSpecDto)
        .expectAll(
            spec -> spec.expectStatus().isBadRequest(),
            spec -> spec.expectHeader().doesNotExist("Location")
        )
        .returnResult(Void.class)
        .getResponseHeaders();
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidIdProvided() {
    getWebTestClient(apiSpecController)
        .get()
        .uri(ApiSpecController.API_SPEC + "/1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectAll(
            spec -> spec.expectStatus().isOk(),
            spec -> spec.expectBody().jsonPath("$.id").exists(),
            spec -> spec.expectBody().jsonPath("$.name").exists(),
            spec -> spec.expectBody().jsonPath("$.project").exists()
        );
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    getWebTestClient(apiSpecController)
        .get()
        .uri(uriBuilder ->
            uriBuilder
                .path(ApiSpecController.API_SPEC)
                .queryParam("projectCode", PROJECT_DTO.getCode())
                .queryParam("name", OPEN_API_SPEC_NAME)
                .build())
        .exchange()
        .expectAll(
            spec -> spec.expectStatus().isOk(),
            spec -> spec.expectBody().jsonPath("$.id").exists(),
            spec -> spec.expectBody().jsonPath("$.name").isEqualTo(OPEN_API_SPEC_NAME),
            spec -> spec.expectBody().jsonPath("$.project").isEqualTo(PROJECT_DTO.getCode())
        );
  }

  private void saveApiSpec(ApiSpecDto apiSpecDto) {
    HttpHeaders headers = post(apiSpecDto)
        .expectAll(
            spec -> spec.expectStatus().isCreated(),
            spec -> spec.expectHeader().exists("Location")
        )
        .returnResult(Void.class)
        .getResponseHeaders();

    verifySpec(ResponseEntityUtils.getEntityId(headers), apiSpecDto);
  }

  @NotNull
  private WebTestClient.ResponseSpec post(ApiSpecDto apiSpecDto) {
    return getWebTestClient(apiSpecController)
        .post()
        .uri(ApiSpecController.API_SPEC)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(apiSpecDto))
        .exchange();
  }

  private void verifySpec(Long entityId, ApiSpecDto apiSpecDto) {
    assertThat(entityId, notNullValue());

    ApiSpec apiSpec = apiSpecRepository.findById(entityId).orElse(new ApiSpec());

    assertThat(apiSpec.getName(), IsEqual.equalTo(apiSpecDto.getName()));
    assertThat(apiSpec.getTitle(), IsEqual.equalTo(apiSpecDto.getTitle()));
    assertThat(apiSpec.getFirstTimeSeen().truncatedTo(ChronoUnit.MILLIS), notNullValue());
    assertThat(apiSpec.getLastSyncTime().truncatedTo(ChronoUnit.MILLIS), IsEqual.equalTo(apiSpecDto.getLastSyncTime().truncatedTo(ChronoUnit.MILLIS)));
    assertThat(apiSpec.getVersion(), IsEqual.equalTo(apiSpecDto.getVersion()));
    assertThat(apiSpec.getProject().getCode(), IsEqual.equalTo(apiSpecDto.getProject()));
    assertThat(apiSpecDto.getMetadata(), IsNull.notNullValue());
    assertThat(apiSpecDto.getMetadata().isEmpty(), IsEqual.equalTo(false));
    verifyNameValuePairs(apiSpec.getMetadata(), apiSpecDto.getMetadata());

    for (ApiPath apiPath : apiSpec.getPaths()) {
      ApiPathDto pathDto = apiSpecDto.getPaths().stream().filter(p2 -> apiPath.getUrl().equals(p2.getUrl())).findFirst().orElse(new ApiPathDto());
      assertThat(apiPath.getTitle(), IsEqual.equalTo(pathDto.getTitle()));
      assertThat(apiPath.getUrl(), IsEqual.equalTo(pathDto.getUrl()));
      assertThat(apiPath.getSpec().getId(), notNullValue());
      assertThat(apiPath.getDescription(), IsEqual.equalTo(pathDto.getDescription()));
      assertThat(apiPath.getMethod(), IsEqual.equalTo(pathDto.getMethod()));

      assertThat(pathDto.getParameters(), IsNull.notNullValue());
      assertThat(pathDto.getParameters().isEmpty(), IsEqual.equalTo(false));
      assertThat(pathDto.getParameters(), IsEqual.equalTo(apiPath.getParameters()));

      assertThat(pathDto.getMetadata(), IsNull.notNullValue());
      assertThat(pathDto.getMetadata().isEmpty(), IsEqual.equalTo(false));
      verifyNameValuePairs(apiPath.getMetadata(), pathDto.getMetadata());
    }
  }
}