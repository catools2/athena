package org.catools.athena.git.common.mapper;

import org.catools.athena.git.common.entity.Commit;
import org.catools.athena.git.common.entity.CommitMetadata;
import org.catools.athena.git.common.entity.DiffEntry;
import org.catools.athena.git.common.entity.GitRepository;
import org.catools.athena.git.common.entity.Tag;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.git.CommitDto;
import org.catools.athena.model.git.DiffEntryDto;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.TagDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface GitMapper {

  GitRepository gitRepositoryDtoToGitRepository(GitRepositoryDto entity);

  GitRepositoryDto gitRepositoryToGitRepositoryDto(GitRepository entity);

  CommitDto commitToCommitDto(Commit commit);

  Commit commitDtoToCommit(CommitDto commitDto);

  default Set<DiffEntry> diffEntryDtoSetToDiffEntrySet1(Set<DiffEntryDto> diffEntries, Commit commit) {
    if (diffEntries == null) {
      return Collections.emptySet();
    }

    HashSet<DiffEntry> hashSet = new HashSet<>();
    for (DiffEntryDto diffEntryDto : diffEntries) {
      hashSet.add(diffEntryDtoToDiffEntry(diffEntryDto, commit));
    }

    return hashSet;
  }

  Set<Tag> tagDtoSetToTagSet(Set<TagDto> tags);

  Set<CommitMetadata> metadataDtoSetToCommitMetadataSet(Set<MetadataDto> metadata);

  MetadataDto commitMetadataToMetadataDto(CommitMetadata commitMetadata);

  CommitMetadata metadataDtoToCommitMetadata(MetadataDto metadata);

  Tag tagDtoToTag(TagDto tagDto);

  TagDto tagToTagDto(Tag tag);

  DiffEntryDto diffEntryToDiffEntryDto(DiffEntry diffEntry);

  DiffEntry diffEntryDtoToDiffEntry(DiffEntryDto diffEntryDto, Commit commit);

}
