package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodStatusDto {
  private Long id;
  private String name;
  private String phase;
  private String message;
  private String reason;
}
