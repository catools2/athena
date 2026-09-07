package org.catools.athena.spec.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.model.apispec.ApiPathDto;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.apispec.ApiSpecDriftDto;
import org.catools.athena.model.apispec.ApiSpecFreshnessDto;
import org.catools.athena.model.apispec.ApiSpecInventoryDto;
import org.catools.athena.model.apispec.ApiSpecSummaryDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.spec.builder.ApiSpecBuilder;
import org.catools.athena.spec.feign.ApiSpecFeignClient;
import org.catools.athena.spec.feign.PageResponse;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiSpecControllerIT extends AthenaSpringBootIT {
  protected ApiSpecFeignClient apiSpecFeignClient;

  protected ProjectDto projectDto;

  protected ProjectDto project2Dto;

  private static final String OPEN_API_SPEC_NAME = "OpenApi";
  private static final String STALE_API_SPEC_NAME = "LegacyContractStale";

  @BeforeAll
  void beforeAllPackages() {
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
  void shallSaveStaleSpecificationForFreshnessAndDriftCoverage() {
    ApiSpecDto apiSpecDto = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    apiSpecDto.setName(STALE_API_SPEC_NAME);
    apiSpecDto.setLastSyncTime(Instant.now().minus(45, ChronoUnit.DAYS));

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

  @Test
  @Order(3)
  void shallReturnInventoryPageWhenValidFiltersProvided() {
    TypedResponse<PageResponse<ApiSpecInventoryDto>> response = apiSpecFeignClient.getAll(
        0,
        10,
        "name",
        "ASC",
        projectDto.getCode(),
        OPEN_API_SPEC_NAME,
        null,
      null,
      null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getContent(), notNullValue());
    assertThat(response.body().getContent().isEmpty(), equalTo(false));

    ApiSpecInventoryDto specInventoryDto = response.body().getContent().getFirst();
    assertThat(specInventoryDto.getId(), notNullValue());
    assertThat(specInventoryDto.getName(), equalTo(OPEN_API_SPEC_NAME));
    assertThat(specInventoryDto.getProject(), equalTo(projectDto.getCode()));
    assertThat(specInventoryDto.getPathCount(), equalTo(ApiSpecBuilder.buildApiPathDto().size()));
  }

  @Test
  @Order(3)
  void shallReturnWorkspaceSummaryWhenValidFiltersProvided() {
    TypedResponse<ApiSpecSummaryDto> response = apiSpecFeignClient.getSummary(
        projectDto.getCode(),
        OPEN_API_SPEC_NAME,
        null,
        null,
      null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getSpecCount(), equalTo(1L));
    assertThat(response.body().getProjectCount(), equalTo(1L));
    assertThat(response.body().getPathCount(), equalTo((long) ApiSpecBuilder.buildApiPathDto().size()));
    assertThat(response.body().getFreshnessWindowDays(), equalTo(30));
    assertThat(response.body().getLatestSyncTime(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnFreshnessDistributionWhenValidFiltersProvided() {
    TypedResponse<ApiSpecFreshnessDto> response = apiSpecFeignClient.getFreshness(
        projectDto.getCode(),
        STALE_API_SPEC_NAME,
        null,
        null,
      null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getFreshCount(), equalTo(0L));
    assertThat(response.body().getAgingCount(), equalTo(0L));
    assertThat(response.body().getStaleCount(), equalTo(1L));
    assertThat(response.body().getUnknownSyncCount(), equalTo(0L));
    assertThat(response.body().getFreshnessWindowDays(), equalTo(30));
    assertThat(response.body().getWarningWindowDays(), equalTo(7));
  }

  @Test
  @Order(4)
  void shallApplyWindowDaysToWorkspaceSummary() {
    final String windowTitle = "windowed-summary-" + System.currentTimeMillis();
    final Instant recentSync = Instant.now().minus(1, ChronoUnit.HOURS);

    ApiSpecDto staleSpec = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    staleSpec.setName("WindowedSummaryStale");
    staleSpec.setTitle(windowTitle);
    staleSpec.setLastSyncTime(Instant.now().minus(45, ChronoUnit.DAYS));
    verifySpec(apiSpecFeignClient.saveOrUpdate(staleSpec), staleSpec);

    ApiSpecDto recentSpec = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    recentSpec.setName("WindowedSummaryRecent");
    recentSpec.setTitle(windowTitle);
    recentSpec.setLastSyncTime(recentSync);
    verifySpec(apiSpecFeignClient.saveOrUpdate(recentSpec), recentSpec);

    TypedResponse<ApiSpecSummaryDto> response = apiSpecFeignClient.getSummary(
        projectDto.getCode(),
        null,
        windowTitle,
        null,
      null,
        1);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getSpecCount(), equalTo(1L));
    assertThat(response.body().getProjectCount(), equalTo(1L));
    assertThat(response.body().getPathCount(), equalTo((long) ApiSpecBuilder.buildApiPathDto().size()));
    assertThat(response.body().getStaleSpecCount(), equalTo(0L));
    assertThat(response.body().getLatestSyncTime().truncatedTo(ChronoUnit.MILLIS), equalTo(recentSync.truncatedTo(ChronoUnit.MILLIS)));
  }

  @Test
  @Order(4)
  void shallApplyWindowDaysToFreshnessDistribution() {
    final String windowTitle = "windowed-freshness-" + System.currentTimeMillis();
    final Instant recentSync = Instant.now().minus(1, ChronoUnit.HOURS);

    ApiSpecDto staleSpec = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    staleSpec.setName("WindowedFreshnessStale");
    staleSpec.setTitle(windowTitle);
    staleSpec.setLastSyncTime(Instant.now().minus(45, ChronoUnit.DAYS));
    verifySpec(apiSpecFeignClient.saveOrUpdate(staleSpec), staleSpec);

    ApiSpecDto recentSpec = ApiSpecBuilder.buildApiSpecDto(projectDto.getCode());
    recentSpec.setName("WindowedFreshnessRecent");
    recentSpec.setTitle(windowTitle);
    recentSpec.setLastSyncTime(recentSync);
    verifySpec(apiSpecFeignClient.saveOrUpdate(recentSpec), recentSpec);

    TypedResponse<ApiSpecFreshnessDto> response = apiSpecFeignClient.getFreshness(
        projectDto.getCode(),
        null,
        windowTitle,
        null,
      null,
        1);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getFreshCount(), equalTo(1L));
    assertThat(response.body().getAgingCount(), equalTo(0L));
    assertThat(response.body().getStaleCount(), equalTo(0L));
    assertThat(response.body().getUnknownSyncCount(), equalTo(0L));
    assertThat(response.body().getLatestSyncTime().truncatedTo(ChronoUnit.MILLIS), equalTo(recentSync.truncatedTo(ChronoUnit.MILLIS)));
  }

  @Test
  @Order(4)
  void shallReturnDriftQueueWhenValidFiltersProvided() {
    TypedResponse<PageResponse<ApiSpecDriftDto>> response = apiSpecFeignClient.getDrift(
        0,
        10,
        "syncAgeDays",
        "DESC",
        projectDto.getCode(),
        STALE_API_SPEC_NAME,
        null,
      null,
      null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getContent(), notNullValue());
    assertThat(response.body().getContent().isEmpty(), equalTo(false));

    ApiSpecDriftDto driftDto = response.body().getContent().getFirst();
    assertThat(driftDto.getId(), notNullValue());
    assertThat(driftDto.getName(), equalTo(STALE_API_SPEC_NAME));
    assertThat(driftDto.getProject(), equalTo(projectDto.getCode()));
    assertThat(driftDto.getDriftStatus(), equalTo("STALE"));
    assertThat(driftDto.getSyncAgeDays(), greaterThanOrEqualTo(45L));
  }

  @Test
  @Order(4)
  void shallApplyFreshnessFilterToInventory() {
    TypedResponse<PageResponse<ApiSpecInventoryDto>> response = apiSpecFeignClient.getAll(
        0,
        10,
        "name",
        "ASC",
        projectDto.getCode(),
        null,
        null,
        null,
        "STALE");

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getContent(), notNullValue());
    assertThat(response.body().getContent().isEmpty(), equalTo(false));
    assertThat(response.body().getContent().stream().allMatch(spec -> STALE_API_SPEC_NAME.equals(spec.getName())), equalTo(true));
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