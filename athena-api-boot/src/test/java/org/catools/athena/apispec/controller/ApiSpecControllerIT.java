package org.catools.athena.apispec.controller;

import org.catools.athena.apispec.builder.ApiSpecBuilder;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.apispec.rest.controller.ApiSpecController;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.controller.CoreControllerIT;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiSpecControllerIT extends CoreControllerIT {
  private final static String OPEN_API_SPEC_NAME = "OpenApi";

  @Autowired
  ApiSpecController apiSpecController;

  @Test
  @Order(1)
  void postMethodShallSaveNewlyProvidedApiSpecDto() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    ResponseEntity<Void> response = apiSpecController.saveOrUpdate(apiSpecDto);
    verifySpec(response, apiSpecDto);
  }

  @Test
  @Order(2)
  void shallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject() {
    ApiSpecDto apiSpecDto1 = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecController.saveOrUpdate(apiSpecDto1);

    ApiSpecDto apiSpecDto2 = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto2.setName(apiSpecDto1.getName());
    apiSpecDto2.getMetadata().add(apiSpecDto1.getMetadata().stream().findFirst().orElse(null));
    apiSpecDto2.getPaths().add(apiSpecDto1.getPaths().stream().findFirst().orElse(null));

    ResponseEntity<Void> response = apiSpecController.saveOrUpdate(apiSpecDto2);

    verifySpec(response, apiSpecDto2);
  }

  @Test
  @Order(2)
  void shallSaveOpenApiSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT2_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    ResponseEntity<Void> response = apiSpecController.saveOrUpdate(apiSpecDto);

    verifySpec(response, apiSpecDto);
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidIdProvided() {
    ResponseEntity<ApiSpecDto> response = apiSpecController.getById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
    assertThat(response.getBody().getProject(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    ResponseEntity<ApiSpecDto> response = apiSpecController.search(PROJECT_DTO.getCode(), OPEN_API_SPEC_NAME);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(OPEN_API_SPEC_NAME));
    assertThat(response.getBody().getProject(), equalTo(PROJECT_DTO.getCode()));
  }

  private void verifySpec(ResponseEntity<Void> response, ApiSpecDto apiSpecDto) {
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    Long entityId = ResponseEntityUtils.getEntityId(response);
    assertThat(entityId, notNullValue());

    ApiSpecDto apiSpec = apiSpecController.getById(entityId).getBody();

    assertThat(apiSpec.getName(), IsEqual.equalTo(apiSpecDto.getName()));
    assertThat(apiSpec.getTitle(), IsEqual.equalTo(apiSpecDto.getTitle()));
    assertThat(apiSpec.getFirstTimeSeen().truncatedTo(ChronoUnit.MILLIS), notNullValue());
    assertThat(apiSpec.getLastSyncTime().truncatedTo(ChronoUnit.MILLIS), IsEqual.equalTo(apiSpecDto.getLastSyncTime().truncatedTo(ChronoUnit.MILLIS)));
    assertThat(apiSpec.getVersion(), IsEqual.equalTo(apiSpecDto.getVersion()));
    assertThat(apiSpec.getProject(), IsEqual.equalTo(apiSpecDto.getProject()));
    assertThat(apiSpecDto.getMetadata(), IsNull.notNullValue());
    assertThat(apiSpecDto.getMetadata().isEmpty(), IsEqual.equalTo(false));
    verifyNameValuePairs(apiSpec.getMetadata(), apiSpecDto.getMetadata());

    for (ApiPathDto pathDto : apiSpecDto.getPaths()) {
      ApiPathDto apiPath = apiSpec.getPaths().stream().filter(p2 -> pathDto.getUrl().equals(p2.getUrl())).findFirst().orElse(new ApiPathDto());
      assertThat(apiPath.getTitle(), IsEqual.equalTo(pathDto.getTitle()));
      assertThat(apiPath.getUrl(), IsEqual.equalTo(pathDto.getUrl()));
      assertThat(apiPath.getSpecId(), notNullValue());
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