package org.catools.web.listeners;

import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.metrics.configs.CMetricsConfigs;
import org.catools.metrics.model.CMetric;
import org.catools.metrics.utils.CMetricsUtils;
import org.catools.web.metrics.CWebPageTransitionInfo;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CPerformanceMetricPersistenceListener implements CDriverListener {

  @Override
  public void onPageChanged(RemoteWebDriver webDriver, CWebPageTransitionInfo driverMetricInfo, CDate startTime, long durationInNano) {
    if (!CMetricsConfigs.isWebRecorderEnabled() || driverMetricInfo == null || driverMetricInfo.getMetrics() == null) return;

    CMetricsUtils.addMetric(
        "Page Transition",
        driverMetricInfo.getActionTime(),
        durationInNano,
        CList.of(driverMetricInfo.getMetrics().entrySet()).mapToList(e -> new CMetric(e.getKey(), "", e.getValue()))
    );
  }
}
