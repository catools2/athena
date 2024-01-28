package org.catools.athena.rest.git.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.git.model.TagDto;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.git.model.*;
import org.instancio.Instancio;

import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class GitBuilder {
  public static Tag buildTag(TagDto tag) {
    return new Tag()
        .setId(tag.getId())
        .setHash(tag.getHash())
        .setName(tag.getName());
  }

  public static TagDto buildTagDto() {
    return Instancio.of(TagDto.class)
        .ignore(field(TagDto::getId))
        .generate(field(TagDto::getName), gen -> gen.string().length(50, 200))
        .generate(field(TagDto::getHash), gen -> gen.string().length(30, 50))
        .create();
  }

  public static GitRepositoryDto buildGitRepositoryDto() {
    return Instancio.of(GitRepositoryDto.class)
        .ignore(field(GitRepositoryDto::getId))
        .generate(field(GitRepositoryDto::getName), gen -> gen.string().length(50, 200))
        .generate(field(GitRepositoryDto::getUrl), gen -> gen.string().length(100, 300))
        .create();
  }

  public static GitRepository buildGitRepository(GitRepositoryDto repositoryDto) {
    return new GitRepository()
        .setId(repositoryDto.getId())
        .setUrl(repositoryDto.getUrl())
        .setName(repositoryDto.getName())
        .setLastSync(repositoryDto.getLastSync());
  }

  public static DiffEntryDto buildFileChangeDto() {
    return Instancio.of(DiffEntryDto.class)
        .ignore(field(DiffEntryDto::getId))
        .generate(field(DiffEntryDto::getOldPath), gen -> gen.string().length(5, 10))
        .generate(field(DiffEntryDto::getNewPath), gen -> gen.string().length(5, 10))
        .create();
  }

  public static DiffEntry buildFileChange(DiffEntryDto diffEntryDto, Commit commit) {
    return new DiffEntry()
        .setId(diffEntryDto.getId())
        .setOldPath(diffEntryDto.getOldPath())
        .setNewPath(diffEntryDto.getNewPath())
        .setInserted(diffEntryDto.getInserted())
        .setDeleted(diffEntryDto.getDeleted())
        .setCommit(commit);
  }

  public static CommitDto buildCommitDto(UserDto author, UserDto committer) {
    return Instancio.of(CommitDto.class)
        .ignore(field(CommitDto::getId))
        .generate(field(CommitDto::getHash), gen -> gen.string().length(20, 50))
        .generate(field(CommitDto::getShortMessage), gen -> gen.string().length(500, 1000))
        .generate(field(CommitDto::getFullMessage), gen -> gen.string().length(1000, 5000))
        .set(field(CommitDto::getAuthor), author.getUsername())
        .set(field(CommitDto::getCommitter), committer.getUsername())
        .create();
  }

  public static Commit buildCommit(CommitDto commitDto, User author, User committer) {
    Commit commit = new Commit();
    return commit
        .setId(commitDto.getId())
        .setHash(commitDto.getHash())
        .setShortMessage(commitDto.getShortMessage())
        .setFullMessage(commitDto.getFullMessage())
        .setCommitTime(commitDto.getCommitTime())
        .setAuthor(author)
        .setCommitter(committer)
        .setDiffEntries(commitDto.getDiffEntries().stream().map(c -> buildFileChange(c, commit)).collect(Collectors.toSet()))
        .setTags(commitDto.getTags().stream().map(GitBuilder::buildTag).collect(Collectors.toSet()))
        .setMetadata(commitDto.getMetadata().stream().map(GitBuilder::buildMetadata).collect(Collectors.toSet()));
  }

  public static CommitMetadata buildMetadata(MetadataDto metadata) {
    return new CommitMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }
}
