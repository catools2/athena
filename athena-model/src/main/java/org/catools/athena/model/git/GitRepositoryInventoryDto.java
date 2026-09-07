package org.catools.athena.model.git;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class GitRepositoryInventoryDto implements Serializable {

  private Long id;

  private String name;

  private String url;

  private String host;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSync;

  private Long syncAgeDays;

  private String freshnessStatus;
}