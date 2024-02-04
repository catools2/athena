package org.catools.athena.git.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.git.common.model.GitRepository;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.git.model.TagDto;
import org.instancio.Instancio;

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

  public static GitRepository buildGitRepository(GitRepositoryDto repositoryDto) {
    return new GitRepository()
        .setId(repositoryDto.getId())
        .setUrl(repositoryDto.getUrl())
        .setName(repositoryDto.getName())
        .setLastSync(repositoryDto.getLastSync());
  }

  public static CommitDto buildCommitDto(String repositoryName, UserDto author, UserDto committer) {
    return Instancio.of(CommitDto.class)
        .ignore(field(CommitDto::getId))
        .ignore(field(TagDto::getId))
        .ignore(field(DiffEntryDto::getId))
        .ignore(field(MetadataDto::getId))
        .generate(field(CommitDto::getHash), gen -> gen.string().length(20, 50))
        .generate(field(CommitDto::getShortMessage), gen -> gen.string().length(500, 1000))
        .set(field(CommitDto::getAuthor), author.getUsername())
        .set(field(CommitDto::getCommitter), committer.getUsername())
        .set(field(CommitDto::getRepository), repositoryName)
        .create();
  }
}
