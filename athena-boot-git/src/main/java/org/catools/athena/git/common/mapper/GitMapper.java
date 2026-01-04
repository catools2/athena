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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {GitMapperService.class})
public interface GitMapper {

  GitRepository gitRepositoryDtoToGitRepository(GitRepositoryDto entity);

  GitRepositoryDto gitRepositoryToGitRepositoryDto(GitRepository entity);

  @Mapping(source = "authorId", target = "author", qualifiedByName = "getUsername")
  @Mapping(source = "committerId", target = "committer", qualifiedByName = "getUsername")
  @Mapping(source = "repository.name", target = "repository")
  CommitDto commitToCommitDto(Commit commit);

  @Mapping(source = "author", target = "authorId", qualifiedByName = "getUserId")
  @Mapping(source = "committer", target = "committerId", qualifiedByName = "getUserId")
  @Mapping(source = "repository", target = "repository", qualifiedByName = "findRepositoryByName")
  @Mapping(target = "diffEntries", expression = "java(commitDto == null ? null : diffEntryDtoSetToDiffEntrySet1(commitDto.getDiffEntries(), commit))")
  @Mapping(target = "tags", expression = "java(commitDto == null ? null : tagDtoSetToTagSet(commitDto.getTags()))")
  @Mapping(target = "metadata", expression = "java(commitDto == null ? null : metadataDtoSetToCommitMetadataSet(commitDto.getMetadata()))")
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

  @Mapping(source = "diffEntryDto.id", target = "id")
  @Mapping(source = "diffEntryDto.inserted", target = "inserted")
  @Mapping(source = "diffEntryDto.deleted", target = "deleted")
  DiffEntry diffEntryDtoToDiffEntry(DiffEntryDto diffEntryDto, Commit commit);

}
