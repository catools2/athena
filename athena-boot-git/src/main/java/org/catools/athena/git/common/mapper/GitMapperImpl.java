package org.catools.athena.git.common.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GitMapperImpl implements GitMapper {

  private final GitMapperService gitMapperService;

  @Override
  public GitRepository gitRepositoryDtoToGitRepository(final GitRepositoryDto entity) {
    if (entity == null) {
      return null;
    }

    return new GitRepository()
        .setId(entity.getId())
        .setName(entity.getName())
        .setUrl(entity.getUrl())
        .setLastSync(entity.getLastSync());
  }

  @Override
  public GitRepositoryDto gitRepositoryToGitRepositoryDto(final GitRepository entity) {
    if (entity == null) {
      return null;
    }

    return new GitRepositoryDto()
        .setId(entity.getId())
        .setName(entity.getName())
        .setUrl(entity.getUrl())
        .setLastSync(entity.getLastSync());
  }

  @Override
  public CommitDto commitToCommitDto(final Commit commit) {
    if (commit == null) {
      return null;
    }

    return new CommitDto()
        .setAuthor(gitMapperService.getUsername(commit.getAuthorId()))
        .setCommitter(gitMapperService.getUsername(commit.getCommitterId()))
        .setRepository(commit.getRepository() == null ? null : commit.getRepository().getName())
        .setId(commit.getId())
        .setParentCount(commit.getParentCount())
        .setHash(commit.getHash())
        .setParentHash(commit.getParentHash())
        .setShortMessage(commit.getShortMessage())
        .setCommitTime(commit.getCommitTime())
        .setDiffEntries(diffEntrySetToDiffEntryDtoSet(commit.getDiffEntries()))
        .setTags(tagSetToTagDtoSet(commit.getTags()))
        .setMetadata(commitMetadataSetToMetadataDtoSet(commit.getMetadata()));
  }

  @Override
  public Commit commitDtoToCommit(final CommitDto commitDto) {
    if (commitDto == null) {
      return null;
    }

    final Commit commit = new Commit()
        .setAuthorId(gitMapperService.getUserId(commitDto.getAuthor()))
        .setCommitterId(gitMapperService.getUserId(commitDto.getCommitter()))
        .setRepository(gitMapperService.findRepositoryByName(commitDto.getRepository()))
        .setId(commitDto.getId())
        .setHash(commitDto.getHash())
        .setParentHash(commitDto.getParentHash())
        .setShortMessage(commitDto.getShortMessage())
        .setCommitTime(commitDto.getCommitTime())
        .setParentCount(commitDto.getParentCount());

    commit.setDiffEntries(diffEntryDtoSetToDiffEntrySet1(commitDto.getDiffEntries(), commit));
    commit.setTags(tagDtoSetToTagSet(commitDto.getTags()));
    commit.setMetadata(metadataDtoSetToCommitMetadataSet(commitDto.getMetadata()));
    return commit;
  }

  @Override
  public Set<Tag> tagDtoSetToTagSet(final Set<TagDto> tags) {
    if (tags == null) {
      return null;
    }

    final Set<Tag> mapped = new LinkedHashSet<>();
    for (TagDto tagDto : tags) {
      mapped.add(tagDtoToTag(tagDto));
    }
    return mapped;
  }

  @Override
  public Set<CommitMetadata> metadataDtoSetToCommitMetadataSet(final Set<MetadataDto> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<CommitMetadata> mapped = new LinkedHashSet<>();
    for (MetadataDto metadataDto : metadata) {
      mapped.add(metadataDtoToCommitMetadata(metadataDto));
    }
    return mapped;
  }

  @Override
  public MetadataDto commitMetadataToMetadataDto(final CommitMetadata commitMetadata) {
    if (commitMetadata == null) {
      return null;
    }

    return new MetadataDto()
        .setId(commitMetadata.getId())
        .setName(commitMetadata.getName())
        .setValue(commitMetadata.getValue());
  }

  @Override
  public CommitMetadata metadataDtoToCommitMetadata(final MetadataDto metadata) {
    if (metadata == null) {
      return null;
    }

    return new CommitMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public Tag tagDtoToTag(final TagDto tagDto) {
    if (tagDto == null) {
      return null;
    }

    return new Tag()
        .setId(tagDto.getId())
        .setHash(tagDto.getHash())
        .setName(tagDto.getName());
  }

  @Override
  public TagDto tagToTagDto(final Tag tag) {
    if (tag == null) {
      return null;
    }

    return new TagDto()
        .setId(tag.getId())
        .setHash(tag.getHash())
        .setName(tag.getName());
  }

  @Override
  public DiffEntryDto diffEntryToDiffEntryDto(final DiffEntry diffEntry) {
    if (diffEntry == null) {
      return null;
    }

    return new DiffEntryDto()
        .setId(diffEntry.getId())
        .setOldPath(diffEntry.getOldPath())
        .setNewPath(diffEntry.getNewPath())
        .setInserted(diffEntry.getInserted())
        .setDeleted(diffEntry.getDeleted())
        .setChangeType(diffEntry.getChangeType());
  }

  @Override
  public DiffEntry diffEntryDtoToDiffEntry(final DiffEntryDto diffEntryDto, final Commit commit) {
    if (diffEntryDto == null && commit == null) {
      return null;
    }

    final DiffEntry diffEntry = new DiffEntry().setCommit(commit);
    if (diffEntryDto == null) {
      return diffEntry;
    }

    return diffEntry
        .setId(diffEntryDto.getId())
        .setInserted(diffEntryDto.getInserted())
        .setDeleted(diffEntryDto.getDeleted())
        .setOldPath(diffEntryDto.getOldPath())
        .setNewPath(diffEntryDto.getNewPath())
        .setChangeType(diffEntryDto.getChangeType());
  }

  private Set<DiffEntryDto> diffEntrySetToDiffEntryDtoSet(final Set<DiffEntry> entries) {
    if (entries == null) {
      return null;
    }

    final Set<DiffEntryDto> mapped = new LinkedHashSet<>();
    for (DiffEntry diffEntry : entries) {
      mapped.add(diffEntryToDiffEntryDto(diffEntry));
    }
    return mapped;
  }

  private Set<TagDto> tagSetToTagDtoSet(final Set<Tag> tags) {
    if (tags == null) {
      return null;
    }

    final Set<TagDto> mapped = new LinkedHashSet<>();
    for (Tag tag : tags) {
      mapped.add(tagToTagDto(tag));
    }
    return mapped;
  }

  private Set<MetadataDto> commitMetadataSetToMetadataDtoSet(final Set<CommitMetadata> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<MetadataDto> mapped = new LinkedHashSet<>();
    for (CommitMetadata item : metadata) {
      mapped.add(commitMetadataToMetadataDto(item));
    }
    return mapped;
  }
}