package org.catools.atlassian.zapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.date.CDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CZApiCycle {
  private Long id;
  private String description;
  private CZApiProject project;
  private CZApiVersion version;
  private String environment;
  private CDate endDate;
  private String build;
  private String name;
  private String modifiedBy;
  private CDate startDate;
}
