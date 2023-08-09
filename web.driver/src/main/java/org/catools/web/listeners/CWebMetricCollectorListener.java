package org.catools.web.listeners;

import org.catools.common.date.CDate;
import org.catools.web.entities.CWebPageInfo;
import org.catools.web.metrics.CWebMetric;
import org.catools.web.metrics.CWebPageTransitionInfo;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CWebMetricCollectorListener implements CDriverListener {
  private ThreadLocal<CWebMetric> pageMetricThreadLocal = ThreadLocal.withInitial(CWebMetric::new);

  public CWebMetricCollectorListener() {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> pageMetricThreadLocal.remove()));
  }

  /**
   * Get web metrics since the driver has been created. {@link CWebMetric}
   *
   * @return web metrics
   */
  public CWebMetric getWebMetric() {
    return pageMetricThreadLocal.get();
  }

  @Override
  public void afterAction(String actionName, RemoteWebDriver webDriver, CWebPageInfo pageBeforeAction, CWebPageInfo pageAfterAction, CWebPageTransitionInfo driverMetricInfo, CDate startTime, long durationInNano) {
    getWebMetric().addActionMetric(actionName, pageBeforeAction, pageAfterAction, startTime);
  }

  @Override
  public void onPageChanged(RemoteWebDriver webDriver, CWebPageTransitionInfo pageTransitionInfo, CDate startTime, long durationInNano) {
    getWebMetric().addPageLoadMetric(webDriver, startTime);
    getWebMetric().addPagePerformance(pageTransitionInfo);
  }
}
