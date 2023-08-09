package org.catools.web.metrics;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.web.entities.CWebPageInfo;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CWebActionMetric {
  private String name;
  private CWebPageInfo pageBeforeAction;
  private CWebPageInfo pageAfterAction;
  private Date actionTime;
  private long duration;
}
