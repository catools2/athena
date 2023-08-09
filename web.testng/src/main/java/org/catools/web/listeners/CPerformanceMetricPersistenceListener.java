package org.catools.web.listeners;

import org.catools.common.date.CDate;
import org.catools.metrics.configs.CMetricsConfigs;
import org.catools.metrics.dao.CMetricsDao;
import org.catools.metrics.model.CMetric;
import org.catools.metrics.model.CMetricAction;
import org.catools.web.metrics.CWebPageTransitionInfo;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CPerformanceMetricPersistenceListener implements CDriverListener {

  @Override
  public void onPageChanged(RemoteWebDriver webDriver, CWebPageTransitionInfo driverMetricInfo, CDate startTime, long durationInNano) {
    if (!CMetricsConfigs.isEnabled() || driverMetricInfo == null) return;

    CMetricAction record = new CMetricAction();
    record.setName("Page Transition");

    record.setProject(CMetricsConfigs.getProject());
    record.setEnvironment(CMetricsConfigs.getEnvironment());

    record.setActionTime(driverMetricInfo.getActionTime());
    record.setDuration(durationInNano);

    if (driverMetricInfo.getMetrics() != null) {
      driverMetricInfo.getMetrics().entrySet().forEach(e -> record.getMetadata().add(new CMetric(e.getKey(), e.getValue())));
    }

    CMetricsDao.merge(record);
  }
}
