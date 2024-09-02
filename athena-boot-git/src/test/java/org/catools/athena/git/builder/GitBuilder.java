package org.catools.athena.git.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.git.model.TagDto;
import org.instancio.Instancio;

import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class GitBuilder {
  public static GitRepositoryDto buildGitRepositoryDto() {
    return Instancio.of(GitRepositoryDto.class)
        .ignore(field(GitRepositoryDto::getId))
        .generate(field(GitRepositoryDto::getName), gen -> gen.string().length(50, 200))
        .generate(field(GitRepositoryDto::getUrl), gen -> gen.string().length(100, 300))
        .create();
  }

  public static CommitDto buildCommitDto(String repositoryName, UserDto author, UserDto committer) {
    return Instancio.of(CommitDto.class)
        .ignore(field(CommitDto::getId))
        .ignore(field(DiffEntryDto::getId))
        .ignore(field(MetadataDto::getId))
        .generate(field(CommitDto::getHash), gen -> gen.string().length(20, 50))
        .generate(field(CommitDto::getShortMessage), gen -> gen.string().length(500, 1000))
        .set(field(CommitDto::getAuthor), author.getUsername())
        .set(field(CommitDto::getCommitter), committer.getUsername())
        .set(field(CommitDto::getRepository), repositoryName)
        .set(field(CommitDto::getTags), buildTags())
        .set(field(CommitDto::getDiffEntries), buildDiffEntry())
        .set(field(CommitDto::getMetadata), buildMetadata())
        .create();
  }

  public static Set<TagDto> buildTags() {
    return Instancio.of(TagDto.class)
        .ignore(field(TagDto::getId))
        .generate(field(TagDto::getHash), gen -> gen.string().length(32))
        .generate(field(TagDto::getName), gen -> gen.string().length(50, 200))
        .stream()
        .limit(20)
        .collect(Collectors.toSet());
  }

  public static Set<DiffEntryDto> buildDiffEntry() {
    return Instancio.of(DiffEntryDto.class)
        .ignore(field(DiffEntryDto::getId))
        .stream()
        .limit(15)
        .collect(Collectors.toSet());
  }

  public static Set<MetadataDto> buildMetadata() {
    return Instancio.of(MetadataDto.class)
        .ignore(field(MetadataDto::getId))
        .generate(field(MetadataDto::getValue), gen -> gen.string().length(1, 1000))
        .generate(field(MetadataDto::getName), gen -> gen.string().length(1, 300))
        .stream()
        .limit(4)
        .collect(Collectors.toSet());
  }
}
