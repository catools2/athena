package org.catools.athena.git.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.common.entity.Commit;
import org.catools.athena.git.common.entity.GitRepository;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.feign.CommitFeignClient;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.git.CommitDto;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.catools.athena.git.utils.GitTestUtils.verifyDiffDtoEntriesHaveCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyDiffEntriesHaveCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyTagsDtoHasCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyTagsHasCorrectValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommitControllerIT extends AthenaSpringBootIT {
  protected CommitFeignClient commitFeignClient;
  private static String repositoryName;
  private static UserDto author;
  private static UserDto committer;

  @Autowired
  GitMapper gitMapper;

  @Autowired
  GitRepositoryRepository repositoryRepository;

  @Autowired
  CommitRepository commitRepository;

  @BeforeAll
  void beforeAll() {
    if (commitFeignClient == null) {
      commitFeignClient = testFeignBuilder.getClient(CommitFeignClient.class);
    }

    GitRepository gitRepository = gitMapper.gitRepositoryDtoToGitRepository(GitBuilder.buildGitRepositoryDto());
    repositoryName = repositoryRepository.saveAndFlush(gitRepository).getName();

    author = StagedTestData.getUser(1);

    committer = StagedTestData.getUser(2);
  }

  @Test
  @Transactional(readOnly = true)
  void shallSaveTheRecordWhenValidInformationProvided() {
    CommitDto commitDto = buildCommit();
    Commit commit = commitRepository.findByIdWithRelations(commitDto.getId()).orElse(new Commit());

    verifyCommits(commit, commitDto);
  }


  @Test
  @Transactional(readOnly = true)
  void shallUpdateTheRecordWhenValidInformationProvidedAndRecordExists() {
    CommitDto commitDto1 = buildCommit();

    commitDto1.getDiffEntries().add(commitDto1.getDiffEntries().stream().findAny().get());
    commitDto1.getTags().add(commitDto1.getTags().stream().findAny().get());
    commitDto1.getMetadata().add(commitDto1.getMetadata().stream().findAny().get());

    CommitDto commitDto2 = buildCommit(commitDto1);
    Commit commit2 = commitRepository.findByIdWithRelations(commitDto1.getId()).orElse(new Commit());

    verifyCommits(commit2, commitDto2);
  }


  @Test
  void shallReturnTheRecordWhenSearchByValidId() {
    CommitDto commitDto = buildCommit();

    TypedResponse<CommitDto> searchResponse = commitFeignClient.getById(commitDto.getId());
    assertThat(searchResponse.status(), equalTo(200));
    CommitDto commit = searchResponse.body();

    verifyCommits(commit, commitDto);
  }

  @Test
  void shallReturnTheRecordWhenSearchByValidHash() {
    CommitDto commitDto = buildCommit();

    TypedResponse<CommitDto> searchResponse = commitFeignClient.search(commitDto.getHash());
    assertThat(searchResponse.status(), equalTo(200));
    CommitDto commit = searchResponse.body();

    verifyCommits(commit, commitDto);
  }

  @Test
  void shallReturnTheRecordWhenSearchByInvalidHash() {
    CommitDto commitDto = buildCommit();

    TypedResponse<CommitDto> searchResponse = commitFeignClient.search(commitDto.getHash() + "ASSD");
    assertThat(searchResponse.status(), equalTo(204));
    CommitDto commit = searchResponse.body();
    assertThat(commit, nullValue());
  }

  public CommitDto buildCommit() {
    return buildCommit(GitBuilder.buildCommitDto(repositoryName, author, committer));
  }

  public CommitDto buildCommit(CommitDto commitDto) {
    TypedResponse<Void> response = commitFeignClient.saveOrUpdate(commitDto);

    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
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
    assertThat(commit.getAuthorId(), equalTo(StagedTestData.getUserByUsername(commitDto.getAuthor()).getId()));
    assertThat(commit.getCommitterId(), equalTo(StagedTestData.getUserByUsername(commitDto.getCommitter()).getId()));
    assertThat(commit.getCommitTime().truncatedTo(MILLIS), IsEqual.equalTo(commitDto.getCommitTime().truncatedTo(MILLIS)));
  }
}