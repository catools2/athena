package org.catools.athena.git.rest.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.utils.UserPersistentHelper;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Commit;
import org.catools.athena.git.common.model.GitRepository;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.model.CommitDto;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.catools.athena.git.utils.GitTestUtils.verifyDiffDtoEntriesHaveCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyDiffEntriesHaveCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyTagsDtoHasCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyTagsHasCorrectValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommitControllerIT extends CoreControllerIT {
  private static String REPOSITORY_NAME;
  private static UserDto AUTHOR_DTO;
  private static UserDto COMMITTER_DTO;

  @Autowired
  GitMapper gitMapper;

  @Autowired
  GitRepositoryRepository repositoryRepository;

  @Autowired
  CommitController commitController;

  @Autowired
  CommitRepository commitRepository;

  @Autowired
  UserPersistentHelper userPersistentHelper;

  @BeforeAll
  public void beforeAll() {
    GitRepository gitRepository = gitMapper.gitRepositoryDtoToGitRepository(GitBuilder.buildGitRepositoryDto());
    REPOSITORY_NAME = repositoryRepository.saveAndFlush(gitRepository).getName();

    AUTHOR_DTO = CoreBuilder.buildUserDto();
    userPersistentHelper.save(CoreBuilder.buildUser(AUTHOR_DTO));

    COMMITTER_DTO = CoreBuilder.buildUserDto();
    userPersistentHelper.save(CoreBuilder.buildUser(COMMITTER_DTO));
  }

  @Test
  void shallSaveTheRecordWhenValidInformationProvided() {
    CommitDto commitDto = buildCommit();
    Commit commit = commitRepository.findById(commitDto.getId()).orElse(new Commit());

    verifyCommits(commit, commitDto);
  }


  @Test
  void shallUpdateTheRecordWhenValidInformationProvidedAndRecordExists() {
    CommitDto commitDto1 = buildCommit();

    commitDto1.getDiffEntries().add(commitDto1.getDiffEntries().stream().findAny().get());
    commitDto1.getTags().add(commitDto1.getTags().stream().findAny().get());
    commitDto1.getMetadata().add(commitDto1.getMetadata().stream().findAny().get());

    CommitDto commitDto2 = buildCommit(commitDto1);
    Commit commit2 = commitRepository.findById(commitDto1.getId()).orElse(new Commit());

    verifyCommits(commit2, commitDto2);
  }


  @Test
  void shallReturnTheRecordWhenSearchByValidId() {
    CommitDto commitDto = buildCommit();

    ResponseEntity<CommitDto> searchResponse = commitController.getById(commitDto.getId());
    assertThat(searchResponse.getStatusCode().value(), equalTo(200));
    CommitDto commit = searchResponse.getBody();

    verifyCommits(commit, commitDto);
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidHash() {
    CommitDto commitDto = buildCommit();

    ResponseEntity<CommitDto> searchResponse = commitController.search(commitDto.getHash());
    assertThat(searchResponse.getStatusCode().value(), equalTo(200));
    CommitDto commit = searchResponse.getBody();

    verifyCommits(commit, commitDto);
  }

  @Test
  void shallReturnTheRecordWhenSearchByInvalidHash() {
    CommitDto commitDto = buildCommit();

    ResponseEntity<CommitDto> searchResponse = commitController.search(commitDto.getHash() + "ASSD");
    assertThat(searchResponse.getStatusCode().value(), equalTo(204));
    CommitDto commit = searchResponse.getBody();
    assertThat(commit, nullValue());
  }

  public CommitDto buildCommit() {
    return buildCommit(GitBuilder.buildCommitDto(REPOSITORY_NAME, AUTHOR_DTO, COMMITTER_DTO));
  }

  public CommitDto buildCommit(String hash) {
    CommitDto commitDto = GitBuilder.buildCommitDto(REPOSITORY_NAME, AUTHOR_DTO, COMMITTER_DTO);
    if (hash != null)
      commitDto.setHash(hash);

    return buildCommit(commitDto);
  }

  public CommitDto buildCommit(CommitDto commitDto) {
    ResponseEntity<Void> response = commitController.saveOrUpdate(commitDto);

    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    Long entityId = ResponseEntityUtils.getEntityId(response);
    assertThat(entityId, notNullValue());

    commitDto.setId(entityId);
    return commitDto;
  }

  private void verifyCommits(CommitDto commit, CommitDto commitDto) {
    assertThat(commit.getId(), equalTo(commitDto.getId()));
    assertThat(commit.getHash(), equalTo(commitDto.getHash()));
    assertThat(commit.getShortMessage(), equalTo(commitDto.getShortMessage()));

    verifyTagsDtoHasCorrectValue(commit.getTags(), commitDto.getTags());
    verifyNameValuePairs(commit.getMetadata(), commitDto.getMetadata());
    verifyDiffDtoEntriesHaveCorrectValue(commit.getDiffEntries(), commitDto.getDiffEntries());
    assertThat(commit.getAuthor(), equalTo(commitDto.getAuthor()));
    assertThat(commit.getCommitter(), equalTo(commitDto.getCommitter()));
    assertThat(commit.getCommitTime().truncatedTo(MILLIS), IsEqual.equalTo(commitDto.getCommitTime().truncatedTo(MILLIS)));
  }

  private void verifyCommits(Commit commit, CommitDto commitDto) {
    assertThat(commit.getId(), equalTo(commitDto.getId()));
    assertThat(commit.getHash(), equalTo(commitDto.getHash()));
    assertThat(commit.getShortMessage(), equalTo(commitDto.getShortMessage()));

    verifyTagsHasCorrectValue(commit.getTags(), commitDto.getTags());
    verifyNameValuePairs(commit.getMetadata(), commitDto.getMetadata());
    verifyDiffEntriesHaveCorrectValue(commit.getDiffEntries(), commitDto.getDiffEntries());
    assertThat(commit.getAuthor().getUsername(), equalTo(commitDto.getAuthor()));
    assertThat(commit.getCommitter().getUsername(), equalTo(commitDto.getCommitter()));
    assertThat(commit.getCommitTime().truncatedTo(MILLIS), IsEqual.equalTo(commitDto.getCommitTime().truncatedTo(MILLIS)));
  }
}