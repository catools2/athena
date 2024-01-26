package org.catools.athena.rest.git.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.git.model.BranchDto;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.utils.UserPersistentHelper;
import org.catools.athena.rest.git.builder.GitBuilder;
import org.catools.athena.rest.git.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class GitMapperTest extends AthenaBaseTest {
  private static User AUTHOR;
  private static User COMMITTER;

  @Autowired
  GitMapper gitMapper;

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
  void branchDtoToBranchShallReturnCorrectValue() {
    BranchDto branchDto = GitBuilder.buildBranchDto();
    Branch branch = gitMapper.branchDtoToBranch(branchDto);

    assertThat(branchDto.getId(), equalTo(branch.getId()));
    assertThat(branchDto.getHash(), equalTo(branch.getHash()));
    assertThat(branchDto.getName(), equalTo(branch.getName()));
  }

  @Test
  void branchToBranchDtoShallReturnCorrectValue() {
    Branch branch = GitBuilder.buildBranch(GitBuilder.buildBranchDto());
    BranchDto branchDto = gitMapper.branchToBranchDto(branch);

    assertThat(branchDto.getId(), equalTo(branch.getId()));
    assertThat(branchDto.getHash(), equalTo(branch.getHash()));
    assertThat(branchDto.getName(), equalTo(branch.getName()));
  }

  @Test
  void commitToCommitDtoShallReturnCorrectValue() {
    BranchDto branchDto = GitBuilder.buildBranchDto();
    CommitDto commitDto = GitBuilder.buildCommitDto(branchDto, CoreBuilder.buildUserDto(AUTHOR), CoreBuilder.buildUserDto(COMMITTER));
    Commit commit = gitMapper.commitDtoToCommit(commitDto);

    assertThat(commit.getId(), equalTo(commitDto.getId()));
    assertThat(commit.getHash(), equalTo(commitDto.getHash()));
    assertThat(commit.getCommitTime(), equalTo(commitDto.getCommitTime()));
    assertThat(commit.getShortMessage(), equalTo(commitDto.getShortMessage()));
    assertThat(commit.getFullMessage(), equalTo(commitDto.getFullMessage()));
    assertThat(commit.getMerged(), equalTo(commitDto.getMerged()));

    verifyDiffEntriesHasCorrectValue(commit.getTags(), commitDto.getTags());
    verifyBranchesHaveCorrectValue(commit.getBranches(), commitDto.getBranches());
    verifyNameValuePairs(commit.getMetadata(), commitDto.getMetadata());
    verifyDiffEntriesHaveCorrectValue(commit.getDiffEntries(), commitDto.getDiffEntries());
    assertThat(commit.getAuthor().getUsername(), equalTo(commitDto.getAuthor()));
    assertThat(commit.getCommitter().getUsername(), equalTo(commitDto.getCommitter()));
  }

  private void verifyDiffEntriesHasCorrectValue(Set<Tag> tags1, Set<MetadataDto> tags2) {
    assertThat(tags1, notNullValue());
    assertThat(tags2, notNullValue());

    for (MetadataDto t2 : tags2) {
      Optional<Tag> t1 = tags1.stream().filter(b -> b.getName().equals(t2.getName())).findFirst();
      assertThat(t1.isPresent(), equalTo(true));
      assertThat(t1.get().getId(), equalTo(t2.getId()));
      assertThat(t1.get().getName(), equalTo(t2.getName()));
      assertThat(t1.get().getHash(), equalTo(t2.getValue()));
    }
  }

  private void verifyBranchesHaveCorrectValue(Set<Branch> b1, Set<BranchDto> b2) {
    assertThat(b1, notNullValue());
    assertThat(b2, notNullValue());

    for (BranchDto branchDto : b2) {
      Optional<Branch> branch1 = b1.stream().filter(b -> b.getHash().equals(branchDto.getHash())).findFirst();
      assertThat(branch1.isPresent(), equalTo(true));
      assertThat(branch1.get().getId(), equalTo(branchDto.getId()));
      assertThat(branch1.get().getHash(), equalTo(branchDto.getHash()));
      assertThat(branch1.get().getName(), equalTo(branchDto.getName()));
    }
  }

  private void verifyDiffEntriesHaveCorrectValue(Set<DiffEntry> diffEntry, Set<DiffEntryDto> diffEntryDto) {
    assertThat(diffEntry.isEmpty(), equalTo(false));
    assertThat(diffEntryDto.isEmpty(), equalTo(false));

    for (DiffEntryDto entryDto : diffEntryDto) {
      Optional<DiffEntry> actual = diffEntry.stream().filter(d -> d.getOldPath().equals(entryDto.getOldPath())).findFirst();
      assertThat(actual.isPresent(), equalTo(true));
      verifyDiffEntryHasCorrectValue(actual.get(), entryDto);
    }
  }

  private void verifyDiffEntryHasCorrectValue(DiffEntry diffEntry, DiffEntryDto diffEntryDto) {
    assertThat(diffEntry.getCommit(), notNullValue());
    assertThat(diffEntry.getId(), equalTo(diffEntryDto.getId()));
    assertThat(diffEntry.getDeleted(), equalTo(diffEntryDto.getDeleted()));
    assertThat(diffEntry.getInserted(), equalTo(diffEntryDto.getInserted()));
    assertThat(diffEntry.getOldPath(), equalTo(diffEntryDto.getOldPath()));
    assertThat(diffEntry.getNewPath(), equalTo(diffEntryDto.getNewPath()));
    assertThat(diffEntry.getSimilarityScore(), equalTo(diffEntryDto.getSimilarityScore()));
  }
}