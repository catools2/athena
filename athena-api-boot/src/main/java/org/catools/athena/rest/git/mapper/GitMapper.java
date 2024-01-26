package org.catools.athena.rest.git.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.git.model.BranchDto;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.GitRepositoryDto;
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
  private CoreMapperService coreMapperService;

  public abstract GitRepository gitRepositoryDtoToGitRepository(GitRepositoryDto entity);

  public abstract GitRepositoryDto gitRepositoryToGitRepositoryDto(GitRepository entity);

  public abstract Branch branchDtoToBranch(BranchDto entity);

  public abstract BranchDto branchToBranchDto(Branch entity);

  @Mapping(source = "hash", target = "value")
  public abstract MetadataDto tagToMetadataDto(Tag tag);

  @Mapping(source = "value", target = "hash")
  public abstract Tag metadataDtoToTag(MetadataDto metadata);

  @Mapping(source = "author.username", target = "author")
  @Mapping(source = "committer.username", target = "committer")
  public abstract CommitDto commitToCommitDto(Commit commit);

  public Commit commitDtoToCommit(CommitDto metadata) {
    if (metadata == null) {
      return null;
    }

    Commit commit = new Commit();

    commit.setId(metadata.getId());
    commit.setHash(metadata.getHash());
    commit.setShortMessage(metadata.getShortMessage());
    commit.setFullMessage(metadata.getFullMessage());
    commit.setCommitTime(metadata.getCommitTime());
    commit.setMerged(metadata.getMerged());
    commit.setAuthor(coreMapperService.search(metadata.getAuthor()));
    commit.setCommitter(coreMapperService.search(metadata.getCommitter()));
    commit.setDiffEntries(diffEntryDtoSetToDiffEntrySet1(metadata.getDiffEntries(), commit));
    commit.setBranches(branchDtoSetToBranchSet(metadata.getBranches()));
    commit.setTags(metadataDtoSetToTagSet(metadata.getTags()));
    commit.setMetadata(metadataDtoSetToCommitMetadataSet(metadata.getMetadata()));

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

  public abstract Set<Branch> branchDtoSetToBranchSet(Set<BranchDto> branches);

  public abstract Set<Tag> metadataDtoSetToTagSet(Set<MetadataDto> tags);

  public abstract Set<CommitMetadata> metadataDtoSetToCommitMetadataSet(Set<MetadataDto> metadata);

  public abstract MetadataDto commitMetadataToMetadataDto(CommitMetadata commitMetadata);

  public abstract CommitMetadata metadataDtoToCommitMetadata(MetadataDto metadata);

  public abstract DiffEntryDto diffEntryToDiffEntryDto(DiffEntry diffEntry);

  @Mapping(source = "commit", target = "commit")
  @Mapping(source = "diffEntryDto.id", target = "id")
  public abstract DiffEntry diffEntryDtoToDiffEntry(DiffEntryDto diffEntryDto, Commit commit);

}
