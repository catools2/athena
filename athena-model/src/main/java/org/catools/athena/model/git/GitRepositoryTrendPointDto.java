package org.catools.athena.model.git;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class GitRepositoryTrendPointDto implements Serializable {

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant bucketStart;

  private Long repositoryCount;

  private Long uniqueHostCount;
}