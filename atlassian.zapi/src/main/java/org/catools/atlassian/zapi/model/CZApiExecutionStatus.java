package org.catools.atlassian.zapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CZApiExecutionStatus {
  private Long id;
  private String description;
  private String name;
}
