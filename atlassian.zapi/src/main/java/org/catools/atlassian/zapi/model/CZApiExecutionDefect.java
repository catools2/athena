package org.catools.atlassian.zapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CZApiExecutionDefect {
  private Long defectId;
  private String defectKey;
  private String defectSummary;
  private String defectStatus;
  private String defectResolutionId;
}
