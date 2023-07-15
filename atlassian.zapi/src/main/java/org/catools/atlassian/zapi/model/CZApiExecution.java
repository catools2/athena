package org.catools.atlassian.zapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.date.CDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CZApiExecution {
  private Long id;
  private Long orderId;
  private String executionStatus;
  private CDate executedOn;
  private String executedByUserName;
  private String comment;
  private Long cycleId;
  private String cycleName;
  private String versionName;
  private CDate createdOn;
  private Long issueId;
  private String issueKey;
  private String projectKey;
  private String projectName;
  private CZApiExecutionDefects executionDefects;
  private Long executionDefectCount;
  private Long stepDefectCount;
  private Long totalDefectCount;
}
