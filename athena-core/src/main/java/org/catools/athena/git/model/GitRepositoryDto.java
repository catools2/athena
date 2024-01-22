package org.catools.athena.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GitRepositoryDto implements Serializable {

    private Long id;
    private String name;
    private String url;
  private Instant lastUpdate;

}
