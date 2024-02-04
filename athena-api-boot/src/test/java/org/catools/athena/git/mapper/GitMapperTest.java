package org.catools.athena.git.mapper;

import org.catools.athena.AthenaBaseTest;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.utils.UserPersistentHelper;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Commit;
import org.catools.athena.git.common.model.GitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.GitRepositoryDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.catools.athena.git.utils.GitTestUtils.verifyDiffEntriesHaveCorrectValue;
import static org.catools.athena.git.utils.GitTestUtils.verifyTagsHasCorrectValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class GitMapperTest extends AthenaBaseTest {
  private static User AUTHOR;
  private static User COMMITTER;

  @Autowired
  GitMapper gitMapper;

  @Autowired
  GitRepositoryRepository gitRepositoryRepository;

  @Autowired
  UserPersistentHelper userPersistentHelper;

  @BeforeAll
  public void beforeAll() {
    AUTHOR = userPersistentHelper.save(CoreBuilder.buildUser(CoreBuilder.buildUserDto())).orElse(null);
    COMMITTER = userPersistentHelper.save(CoreBuilder.buildUser(CoreBuilder.buildUserDto())).orElse(null);
  }

  @Test
  void gitRepositoryDtoToGitRepositoryShallReturnCorrectValue() {
    GitRepositoryDto gitRepositoryDto = GitBuilder.buildGitRepositoryDto();
    GitRepository gitRepository = gitMapper.gitRepositoryDtoToGitRepository(gitRepositoryDto);

    assertThat(gitRepository.getId(), equalTo(gitRepositoryDto.getId()));
    assertThat(gitRepository.getName(), equalTo(gitRepositoryDto.getName()));
    assertThat(gitRepository.getUrl(), equalTo(gitRepositoryDto.getUrl()));
    assertThat(gitRepository.getLastSync(), equalTo(gitRepositoryDto.getLastSync()));
  }

  @Test
  void gitRepositoryToGitRepositoryDtoShallReturnCorrectValue() {
    GitRepositoryDto repository = GitBuilder.buildGitRepositoryDto();
    GitRepositoryDto repositoryDto = gitMapper.gitRepositoryToGitRepositoryDto(GitBuilder.buildGitRepository(repository));

    assertThat(repositoryDto.getId(), equalTo(repository.getId()));
    assertThat(repositoryDto.getName(), equalTo(repository.getName()));
    assertThat(repositoryDto.getUrl(), equalTo(repository.getUrl()));
    assertThat(repositoryDto.getLastSync(), equalTo(repository.getLastSync()));
  }

  @Test
  void commitToCommitDtoShallReturnCorrectValue() {
    GitRepositoryDto repository = GitBuilder.buildGitRepositoryDto();
    GitRepository gitRepository = gitMapper.gitRepositoryDtoToGitRepository(repository);
    gitRepositoryRepository.saveAndFlush(gitRepository);

    CommitDto commitDto = GitBuilder.buildCommitDto(repository.getName(), CoreBuilder.buildUserDto(AUTHOR), CoreBuilder.buildUserDto(COMMITTER));
    Commit commit = gitMapper.commitDtoToCommit(commitDto);

    assertThat(commit.getId(), equalTo(commitDto.getId()));
    assertThat(commit.getHash(), equalTo(commitDto.getHash()));
    assertThat(commit.getCommitTime(), equalTo(commitDto.getCommitTime()));
    assertThat(commit.getShortMessage(), equalTo(commitDto.getShortMessage()));

    assertThat(commit.getInserted(), equalTo(commitDto.getInserted()));
    assertThat(commit.getDeleted(), equalTo(commitDto.getDeleted()));

    verifyTagsHasCorrectValue(commit.getTags(), commitDto.getTags());
    verifyNameValuePairs(commit.getMetadata(), commitDto.getMetadata());
    verifyDiffEntriesHaveCorrectValue(commit.getDiffEntries(), commitDto.getDiffEntries());
    assertThat(commit.getAuthor().getUsername(), equalTo(commitDto.getAuthor()));
    assertThat(commit.getCommitter().getUsername(), equalTo(commitDto.getCommitter()));
  }
}