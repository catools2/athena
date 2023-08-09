package org.catools.web.metrics;

import lombok.Data;
import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.web.entities.CWebPageInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v114.performance.model.Metric;

import java.util.Date;

@Data
public class CWebMetric {
  private CWebActionMetrics actionPerformances = new CWebActionMetrics();
  private CWebPageTransitionsInfo pagePerformances = new CWebPageTransitionsInfo();
  private CWebPageLoadMetrics pageLoadMetrics = new CWebPageLoadMetrics();

  public void addActionMetric(String name, CWebPageInfo pageBeforeAction, CWebPageInfo pageAfterAction, CDate startTime) {
    CWebActionMetric action = new CWebActionMetric()
        .setName(name)
        .setPageBeforeAction(pageBeforeAction)
        .setPageAfterAction(pageAfterAction)
        .setActionTime(startTime)
        .setDuration(startTime.getDurationToNow().getNano());
    actionPerformances.add(action);
  }

  public void addPagePerformance(String actionName, CWebPageInfo pageBeforeAction, CWebPageInfo pageAfterAction, CList<Metric> metricList, Date actionTime) {
    CWebPageTransitionInfo pagePerformance = new CWebPageTransitionInfo(actionName, pageBeforeAction, pageAfterAction, metricList, actionTime);
    addPagePerformance(pagePerformance);
  }

  public void addPagePerformance(CWebPageTransitionInfo pageTransitionInfo) {
    pagePerformances.add(pageTransitionInfo);
  }

  public void addPageLoadMetric(WebDriver driver, CDate startTime) {
    CWebPageLoadMetric pageLoad = new CWebPageLoadMetric()
        .setName("Page Load")
        .setTitle(driver.getTitle())
        .setUrl(driver.getCurrentUrl())
        .setActionTime(startTime)
        .setDuration(startTime.getDurationToNow().getNano());
    pageLoadMetrics.add(pageLoad);
  }
}
