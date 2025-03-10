package org.catools.athena.git.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.common.model.GitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.feign.GitRepositoryFeignClient;
import org.catools.athena.git.model.GitRepositoryDto;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GitRepositoryControllerIT extends AthenaSpringBootIT {
  protected GitRepositoryFeignClient repositoryFeignClient;

  @Autowired
  GitRepositoryRepository repositoryRepository;

  @BeforeAll
  public void beforeAllPackages() {
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
}