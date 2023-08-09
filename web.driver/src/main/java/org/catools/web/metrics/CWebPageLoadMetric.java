package org.catools.web.metrics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CWebPageLoadMetric {
  private String name;
  private String title;
  private String url;
  private Date actionTime;
  private long duration;
}
