package org.catools.athena.spec.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.spec.builder.ApiSpecBuilder;
import org.catools.athena.spec.feign.ApiSpecFeignClient;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiSpecControllerIT extends AthenaSpringBootIT {
  protected ApiSpecFeignClient apiSpecFeignClient;

  protected ProjectDto projectDto;

  protected ProjectDto project2Dto;

  private static final String OPEN_API_SPEC_NAME = "OpenApi";

  @BeforeAll
  public void beforeAllPackages() {
    if (apiSpecFeignClient == null) {
      apiSpecFeignClient = testFeignBuilder.getClient(ApiSpecFeignClient.class);
    }

    if (projectDto == null) {
      projectDto = StagedTestData.getProject(1);
      project2Dto = StagedTestData.getProject(2);
    }
  }

  @Test
  @Order(1)
  void postMethodShallSaveNewlyProvidedApiSpecDto() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    TypedResponse<Void> response = apiSpecFeignClient.saveOrUpdate(apiSpecDto);
    verifySpec(response, apiSpecDto);
  }

  @Test
  @Order(2)
  void shallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject() {
    ApiSpecDto apiSpecDto1 = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    apiSpecFeignClient.saveOrUpdate(apiSpecDto1);

    ApiSpecDto apiSpecDto2 = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    apiSpecDto2.setName(apiSpecDto1.getName());
    apiSpecDto2.getMetadata().add(apiSpecDto1.getMetadata().stream().findFirst().orElse(null));
    apiSpecDto2.getPaths().add(apiSpecDto1.getPaths().stream().findFirst().orElse(null));

    TypedResponse<Void> response = apiSpecFeignClient.saveOrUpdate(apiSpecDto2);

    verifySpec(response, apiSpecDto2);
  }

  @Test
  @Order(2)
  void shallSaveOpenApiSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(project2Dto.getCode());
    apiSpecDto.setName(OPEN_API_SPEC_NAME);
    TypedResponse<Void> response = apiSpecFeignClient.saveOrUpdate(apiSpecDto);

    verifySpec(response, apiSpecDto);
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidIdProvided() {
    TypedResponse<ApiSpecDto> response = apiSpecFeignClient.getById(1L);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
    assertThat(response.body().getProject(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    TypedResponse<ApiSpecDto> response = apiSpecFeignClient.search(projectDto.getCode(), OPEN_API_SPEC_NAME);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), notNullValue());
    assertThat(response.body().getName(), equalTo(OPEN_API_SPEC_NAME));
    assertThat(response.body().getProject(), equalTo(projectDto.getCode()));
  }

  private void verifySpec(TypedResponse<Void> response, ApiSpecDto apiSpecDto) {
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());

    ApiSpecDto apiSpec = apiSpecFeignClient.getById(entityId).body();

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