package org.catools.athena.git.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.common.entity.GitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.feign.PageResponse;
import org.catools.athena.git.feign.GitRepositoryFeignClient;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.GitRepositoryInventoryDto;
import org.catools.athena.model.git.GitRepositorySummaryDto;
import org.catools.athena.model.git.GitRepositoryTrendPointDto;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GitRepositoryControllerIT extends AthenaSpringBootIT {
  private static final String DASHBOARD_PREFIX = "git-dashboard-" + System.currentTimeMillis();

  protected GitRepositoryFeignClient repositoryFeignClient;

  @Autowired
  GitRepositoryRepository repositoryRepository;

  @BeforeAll
  void beforeAllPackages() {
    if (repositoryFeignClient == null) {
      repositoryFeignClient = testFeignBuilder.getClient(GitRepositoryFeignClient.class);
    }
  }

  @Test
  void shallSaveTheRecordWhenValidInformationProvided() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepositoryDto);

    verifyRepository(response, gitRepositoryDto);
  }

  @Test
  void shallUpdateTheRecordWhenRecordWithSameNameExists() {
    GitRepositoryDto gitRepository1 = GitBuilder.buildGitRepositoryDto();
    repositoryFeignClient.saveOrUpdate(gitRepository1);

    GitRepositoryDto gitRepository2 = GitBuilder.buildGitRepositoryDto();
    gitRepository2.setName(gitRepository1.getName());
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepository2);

    verifyRepository(response, gitRepository2);
  }

  @Test
  void shallUpdateTheRecordWhenRecordWithSameUrlExists() {
    GitRepositoryDto gitRepository1 = GitBuilder.buildGitRepositoryDto();
    repositoryFeignClient.saveOrUpdate(gitRepository1);

    GitRepositoryDto gitRepository2 = GitBuilder.buildGitRepositoryDto();
    gitRepository2.setUrl(gitRepository1.getUrl());
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepository2);

    verifyRepository(response, gitRepository2);
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidId() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepositoryDto);

    assertThat(response.status(), equalTo(201));

    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());

    TypedResponse<GitRepositoryDto> searchResponse = repositoryFeignClient.getById(entityId);
    assertThat(searchResponse.status(), equalTo(200));
    GitRepositoryDto gitRepository = searchResponse.body();

    assertThat(gitRepository.getId(), IsEqual.equalTo(entityId));
    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidName() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepositoryDto);

    assertThat(response.status(), equalTo(201));
    Long id = FeignUtils.getIdFromLocationHeader(response);
    assertThat(id, notNullValue());

    TypedResponse<GitRepositoryDto> searchResponse = repositoryFeignClient.search(gitRepositoryDto.getName());
    assertThat(searchResponse.status(), equalTo(200));
    GitRepositoryDto gitRepository = searchResponse.body();

    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidUrl() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepositoryDto);

    assertThat(response.status(), equalTo(201));
    Long id = FeignUtils.getIdFromLocationHeader(response);
    assertThat(id, notNullValue());

    TypedResponse<GitRepositoryDto> searchResponse = repositoryFeignClient.search(gitRepositoryDto.getUrl());
    assertThat(searchResponse.status(), equalTo(200));
    GitRepositoryDto gitRepository = searchResponse.body();

    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  @Test
  void shallReturnTheRecordWhenSearchByInvalidParameter() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepositoryDto);

    assertThat(response.status(), equalTo(201));
    Long id = FeignUtils.getIdFromLocationHeader(response);
    assertThat(id, notNullValue());

    TypedResponse<GitRepositoryDto> searchResponse = repositoryFeignClient.search(gitRepositoryDto.getUrl() + "ASD");
    assertThat(searchResponse.status(), equalTo(204));
    GitRepositoryDto gitRepository = searchResponse.body();
    assertThat(gitRepository, nullValue());
  }

  @Test
  void shallReturnRepositoryDashboardSummary() {
    ensureDashboardRepositories();

    TypedResponse<GitRepositorySummaryDto> response = repositoryFeignClient.getSummary(DASHBOARD_PREFIX, null, null, null);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), equalTo(2L));
    assertThat(response.body().getHostCount(), equalTo(2L));
    assertThat(response.body().getFreshCount(), equalTo(2L));
    assertThat(response.body().getAgingCount(), equalTo(0L));
    assertThat(response.body().getStaleCount(), equalTo(0L));
    assertThat(response.body().getUnknownSyncCount(), equalTo(0L));
  }

  @Test
  void shallReturnRepositoryDashboardSummaryWithinWindow() {
    ensureDashboardRepositories();

    TypedResponse<GitRepositorySummaryDto> response = repositoryFeignClient.getSummary(DASHBOARD_PREFIX, null, null, 1);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), equalTo(1L));
    assertThat(response.body().getHostCount(), equalTo(1L));
    assertThat(response.body().getFreshCount(), equalTo(1L));
    assertThat(response.body().getAgingCount(), equalTo(0L));
    assertThat(response.body().getStaleCount(), equalTo(0L));
    assertThat(response.body().getUnknownSyncCount(), equalTo(0L));
  }

  @Test
  void shallReturnRepositoryDashboardTrend() {
    ensureDashboardRepositories();

    TypedResponse<List<GitRepositoryTrendPointDto>> response = repositoryFeignClient.getTrend(DASHBOARD_PREFIX, null, null, null);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), equalTo(2));
    assertThat(response.body().get(0).getRepositoryCount(), equalTo(1L));
    assertThat(response.body().get(0).getUniqueHostCount(), equalTo(1L));
    assertThat(response.body().get(1).getRepositoryCount(), equalTo(1L));
    assertThat(response.body().get(1).getUniqueHostCount(), equalTo(1L));
  }

  @Test
  void shallReturnRepositoryDashboardTrendWithinWindow() {
    ensureDashboardRepositories();

    TypedResponse<List<GitRepositoryTrendPointDto>> response = repositoryFeignClient.getTrend(DASHBOARD_PREFIX, null, null, 1);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), equalTo(1));
    assertThat(response.body().getFirst().getBucketStart(), equalTo(Instant.now().truncatedTo(ChronoUnit.DAYS).minus(1, ChronoUnit.DAYS)));
    assertThat(response.body().getFirst().getRepositoryCount(), equalTo(1L));
  }

  @Test
  void shallReturnRepositoryDashboardInventory() {
    ensureDashboardRepositories();

    TypedResponse<PageResponse<GitRepositoryInventoryDto>> response = repositoryFeignClient.getAll(
        0,
        10,
        "lastSync",
        "DESC",
        DASHBOARD_PREFIX,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalElements(), equalTo(2L));
    assertThat(response.body().getContent().size(), equalTo(2));
    assertThat(response.body().getContent().get(0).getName(), equalTo(DASHBOARD_PREFIX + "-beta"));
    assertThat(response.body().getContent().get(0).getFreshnessStatus(), equalTo("FRESH"));
    assertThat(response.body().getContent().get(1).getName(), equalTo(DASHBOARD_PREFIX + "-alpha"));
  }


  private void verifyRepository(TypedResponse<Void> response, GitRepositoryDto gitRepositoryDto) {
    assertThat(response.status(), equalTo(201));

    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());

    GitRepository gitRepository = repositoryRepository.findById(entityId).orElse(new GitRepository());

    assertThat(gitRepository.getId(), IsEqual.equalTo(entityId));
    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  private void ensureDashboardRepositories() {
    if (repositoryRepository.findByName(DASHBOARD_PREFIX + "-alpha").isPresent()) {
      return;
    }

    final Instant base = Instant.now().truncatedTo(ChronoUnit.DAYS);
    saveDashboardRepository(
        DASHBOARD_PREFIX + "-alpha",
        "https://github.com/athena/" + DASHBOARD_PREFIX + "-alpha.git",
        base.minus(2, ChronoUnit.DAYS).plus(9, ChronoUnit.HOURS));
    saveDashboardRepository(
        DASHBOARD_PREFIX + "-beta",
        "https://gitlab.com/athena/" + DASHBOARD_PREFIX + "-beta.git",
        base.minus(1, ChronoUnit.DAYS).plus(10, ChronoUnit.HOURS));
  }

  private void saveDashboardRepository(String name, String url, Instant lastSync) {
    GitRepositoryDto gitRepositoryDto = new GitRepositoryDto()
        .setName(name)
        .setUrl(url)
        .setLastSync(lastSync);
    TypedResponse<Void> response = repositoryFeignClient.saveOrUpdate(gitRepositoryDto);
    assertThat(response.status(), equalTo(201));
  }
}