package org.catools.athena.git.rest.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.controller.CoreControllerTest;
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
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GitRepositoryControllerTest extends CoreControllerTest {
  @Autowired
  GitRepositoryController repositoryController;

  @Autowired
  GitRepositoryRepository repositoryRepository;

  @Test
  void shallSaveTheRecordWhenValidInformationProvided() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.save(gitRepositoryDto);

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


  @Test
  void shallReturnTheRecordWhenSearchByValidId() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    ResponseEntity<Void> response = repositoryController.save(gitRepositoryDto);

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
    ResponseEntity<Void> response = repositoryController.save(gitRepositoryDto);

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
    ResponseEntity<Void> response = repositoryController.save(gitRepositoryDto);

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
    ResponseEntity<Void> response = repositoryController.save(gitRepositoryDto);

    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    ResponseEntity<GitRepositoryDto> searchResponse = repositoryController.search(gitRepositoryDto.getUrl() + "ASD");
    assertThat(searchResponse.getStatusCode().value(), equalTo(204));
    GitRepositoryDto gitRepository = searchResponse.getBody();
    assertThat(gitRepository, nullValue());
  }

}