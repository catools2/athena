package org.catools.web.metrics;

import lombok.Data;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.web.entities.CWebPageInfo;
import org.openqa.selenium.devtools.v114.performance.model.Metric;

import java.util.Date;
import java.util.List;

@Data
public class CWebPageTransitionInfo {
  private String actionName;
  private CWebPageInfo pageBeforeAction;
  private CWebPageInfo pageAfterAction;
  private CMap<String, Number> metrics = new CHashMap<>();
  private Date actionTime;

  private CWebPageTransitionInfo() {
  }

  public CWebPageTransitionInfo(String actionName, CWebPageInfo pageBeforeAction, CWebPageInfo pageAfterAction) {
    this.actionName = actionName;
    this.pageBeforeAction = pageBeforeAction;
    this.pageAfterAction = pageAfterAction;
  }

  public CWebPageTransitionInfo(String actionName, CWebPageInfo pageBeforeAction, CWebPageInfo pageAfterAction, CList<Metric> metricList, Date actionTime) {
    this.actionName = actionName;
    this.pageBeforeAction = pageBeforeAction;
    this.pageAfterAction = pageAfterAction;

    metricList.forEach(m -> metrics.put(m.getName(), m.getValue()));

    this.actionTime = actionTime;
  }

  public String getTitleBeforeAction() {
    return pageBeforeAction == null ? null : pageBeforeAction.getTitle();
  }

  public String getUrlBeforeAction() {
    return pageBeforeAction == null ? null : pageBeforeAction.getUrl();
  }

  public String getTitleAfterAction() {
    return pageAfterAction == null ? null : pageAfterAction.getTitle();
  }

  public String getUrlAfterAction() {
    return pageAfterAction == null ? null : pageAfterAction.getUrl();
  }

  private static double getMetric(List<Metric> metricList, String metricName) {
    Metric metric = metricList.stream().filter(m -> m.getName().equals(metricName)).findFirst().orElse(null);
    return metric == null ? -1 : metric.getValue().doubleValue();
  }
}
