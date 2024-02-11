package org.catools.athena.git.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
@Accessors(chain = true)
public class CommitDto implements Serializable {

  private Long id;

  @NotNull(message = "The git commit 'parent count' must be provided.")
  private Integer parentCount;

  @NotBlank(message = "The git commit hash must be provided.")
  @Size(max = 50, message = "The git commit hash can be at most 50 character.")
  private String hash;

  @NotBlank(message = "The commit repository name must be provided.")
  @Size(max = 200, message = "The commit repository name can be at most 200 character.")
  private String repository;

  @Size(max = 50, message = "The git commit parent hash can be at most 50 character.")
  private String parentHash;

  @NotBlank(message = "The git commit short message must be provided.")
  @Size(max = 5000, message = "The git commit short message can be at most 5000 character.")
  private String shortMessage;

  @NotNull(message = "The git commit 'commit time' must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant commitTime;

  @NotNull(message = "The git commit author must be provided.")
  private String author;

  @NotNull(message = "The git commit committer must be provided.")
  private String committer;

  private Set<DiffEntryDto> diffEntries = new HashSet<>();

  private Set<TagDto> tags = new HashSet<>();

  private Set<MetadataDto> metadata = new HashSet<>();
}
