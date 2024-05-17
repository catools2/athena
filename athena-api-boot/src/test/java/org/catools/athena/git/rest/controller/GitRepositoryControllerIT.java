package org.catools.athena.git.rest.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.common.model.GitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.model.GitRepositoryDto;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GitRepositoryControllerIT extends CoreControllerIT {
  @Autowired
  GitRepositoryController repositoryController;

  @Autowired
  GitRepositoryRepository repositoryRepository;

  @Test
  void shallSaveTheRecordWhenValidInformationProvided() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepositoryDto);

    verifyRepository(response, gitRepositoryDto);
  }

  @Test
  void shallUpdateTheRecordWhenRecordWithSameNameExists() {
    GitRepositoryDto gitRepository1 = GitBuilder.buildGitRepositoryDto();
    repositoryController.saveOrUpdate(gitRepository1);

    GitRepositoryDto gitRepository2 = GitBuilder.buildGitRepositoryDto();
    gitRepository2.setName(gitRepository1.getName());
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepository2);

    verifyRepository(response, gitRepository2);
  }

  @Test
  void shallUpdateTheRecordWhenRecordWithSameUrlExists() {
    GitRepositoryDto gitRepository1 = GitBuilder.buildGitRepositoryDto();
    repositoryController.saveOrUpdate(gitRepository1);

    GitRepositoryDto gitRepository2 = GitBuilder.buildGitRepositoryDto();
    gitRepository2.setUrl(gitRepository1.getUrl());
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepository2);

    verifyRepository(response, gitRepository2);
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidId() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepositoryDto);

    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    Long entityId = ResponseEntityUtils.getEntityId(response);
    assertThat(entityId, notNullValue());

    ResponseEntity<GitRepositoryDto> searchResponse = repositoryController.getById(entityId);
    assertThat(searchResponse.getStatusCode().value(), equalTo(200));
    GitRepositoryDto gitRepository = searchResponse.getBody();

    assertThat(gitRepository.getId(), IsEqual.equalTo(entityId));
    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidName() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepositoryDto);

    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    ResponseEntity<GitRepositoryDto> searchResponse = repositoryController.search(gitRepositoryDto.getName());
    assertThat(searchResponse.getStatusCode().value(), equalTo(200));
    GitRepositoryDto gitRepository = searchResponse.getBody();

    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidUrl() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepositoryDto);

    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    ResponseEntity<GitRepositoryDto> searchResponse = repositoryController.search(gitRepositoryDto.getUrl());
    assertThat(searchResponse.getStatusCode().value(), equalTo(200));
    GitRepositoryDto gitRepository = searchResponse.getBody();

    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }

  @Test
  void shallReturnTheRecordWhenSearchByInvalidParameter() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.saveOrUpdate(gitRepositoryDto);

    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    ResponseEntity<GitRepositoryDto> searchResponse = repositoryController.search(gitRepositoryDto.getUrl() + "ASD");
    assertThat(searchResponse.getStatusCode().value(), equalTo(204));
    GitRepositoryDto gitRepository = searchResponse.getBody();
    assertThat(gitRepository, nullValue());
  }


  private void verifyRepository(ResponseEntity<Void> response, GitRepositoryDto gitRepositoryDto) {
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    Long entityId = ResponseEntityUtils.getEntityId(response);
    assertThat(entityId, notNullValue());

    GitRepository gitRepository = repositoryRepository.findById(entityId).orElse(new GitRepository());

    assertThat(gitRepository.getId(), IsEqual.equalTo(entityId));
    assertThat(gitRepository.getName(), IsEqual.equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), IsEqual.equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync().truncatedTo(MILLIS), IsEqual.equalTo(gitRepositoryDto.getLastSync().truncatedTo(MILLIS)));
  }
}