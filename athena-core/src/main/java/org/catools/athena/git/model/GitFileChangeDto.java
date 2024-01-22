package org.catools.athena.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GitFileChangeDto implements Serializable {

  private Long id;
  private GitCommitDto commit;
  private String path;
  private String newPath;
  private int inserted;
  private int deleted;

}
