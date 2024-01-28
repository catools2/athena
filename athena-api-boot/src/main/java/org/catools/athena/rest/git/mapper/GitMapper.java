package org.catools.athena.rest.git.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.git.model.TagDto;
import org.catools.athena.rest.core.mapper.CoreMapperService;
import org.catools.athena.rest.git.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Mapper(uses = {CoreMapperService.class})
public abstract class GitMapper {
  @Autowired
  @SuppressWarnings("unused")
  private CoreMapperService coreMapperService;

  public abstract GitRepository gitRepositoryDtoToGitRepository(GitRepositoryDto entity);

  public abstract GitRepositoryDto gitRepositoryToGitRepositoryDto(GitRepository entity);

  @Mapping(source = "author.username", target = "author")
  @Mapping(source = "committer.username", target = "committer")
  public abstract CommitDto commitToCommitDto(Commit commit);

  public Commit commitDtoToCommit(CommitDto commitDto) {
    if (commitDto == null) {
      return null;
    }

    Commit commit = new Commit();

    commit.setId(commitDto.getId());
    commit.setHash(commitDto.getHash());
    commit.setParentCount(commitDto.getParentCount());
    commit.setShortMessage(commitDto.getShortMessage());
    commit.setFullMessage(commitDto.getFullMessage());
    commit.setCommitTime(commitDto.getCommitTime());
    commit.setAuthor(coreMapperService.search(commitDto.getAuthor()));
    commit.setCommitter(coreMapperService.search(commitDto.getCommitter()));
    commit.setDiffEntries(diffEntryDtoSetToDiffEntrySet1(commitDto.getDiffEntries(), commit));
    commit.setTags(tagDtoSetToTagSet(commitDto.getTags()));
    commit.setMetadata(metadataDtoSetToCommitMetadataSet(commitDto.getMetadata()));

    return commit;
  }

  public Set<DiffEntry> diffEntryDtoSetToDiffEntrySet1(Set<DiffEntryDto> diffEntries, Commit commit) {
    if (diffEntries == null) {
      return Collections.emptySet();
    }

    HashSet<DiffEntry> hashSet = new HashSet<>();
    for (DiffEntryDto diffEntryDto : diffEntries) {
      hashSet.add(diffEntryDtoToDiffEntry(diffEntryDto, commit));
    }

    return hashSet;
  }

  public abstract Set<Tag> tagDtoSetToTagSet(Set<TagDto> tags);

  public abstract Set<CommitMetadata> metadataDtoSetToCommitMetadataSet(Set<MetadataDto> metadata);

  public abstract MetadataDto commitMetadataToMetadataDto(CommitMetadata commitMetadata);

  public abstract CommitMetadata metadataDtoToCommitMetadata(MetadataDto metadata);

  public abstract Tag tagDtoToTag(TagDto tagDto);

  public abstract TagDto tagToTagDto(Tag tag);

  public abstract DiffEntryDto diffEntryToDiffEntryDto(DiffEntry diffEntry);

  @Mapping(source = "commit", target = "commit")
  @Mapping(source = "diffEntryDto.id", target = "id")
  public abstract DiffEntry diffEntryDtoToDiffEntry(DiffEntryDto diffEntryDto, Commit commit);

}
