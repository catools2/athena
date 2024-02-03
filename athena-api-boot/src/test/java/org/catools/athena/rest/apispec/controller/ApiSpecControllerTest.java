package org.catools.athena.rest.apispec.controller;

import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.builder.ApiSpecBuilder;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.repository.ApiSpecRepository;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.controller.CoreControllerTest;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
  void shallSaveOpenApiSpecification() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    ResponseEntity<Void> response = apiSpecController.save(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    verifySpec(response, apiSpecDto);
  }

  @Test
  @Order(2)
  void shallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    ResponseEntity<Void> response = apiSpecController.save(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    verifySpec(response, apiSpecDto);
  }

  @Test
  @Order(2)
  void shallSaveOpenApiSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(PROJECT2_DTO.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    ResponseEntity<Void> response = apiSpecController.save(apiSpecDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

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
    Long entityId = ResponseEntityUtils.getEntityId(response);
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
      Set<String> collect = apiSpecDto.getPaths().stream().map(ApiPathDto::getUrl).collect(Collectors.toSet());
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