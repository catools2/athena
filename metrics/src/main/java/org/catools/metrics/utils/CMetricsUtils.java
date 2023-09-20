package org.catools.metrics.utils;

import lombok.experimental.UtilityClass;
import org.catools.metrics.configs.CMetricsConfigs;
import org.catools.metrics.dao.CMetricsDao;
import org.catools.metrics.model.CMetric;
import org.catools.metrics.model.CMetricAction;
import org.catools.metrics.model.CMetricEnvironment;
import org.catools.metrics.model.CMetricProject;

import java.util.Date;
import java.util.List;

@UtilityClass
public class CMetricsUtils {

  public void addMetric(String actionName, Date actionTime, long duration, List<CMetric> metrics) {
    addMetric(CMetricsConfigs.getProject(), CMetricsConfigs.getEnvironment(), actionName, actionTime, duration, metrics);
  }

  public void addMetric(CMetricProject project, CMetricEnvironment environment, String actionName, Date actionTime, long duration, List<CMetric> metrics) {
    CMetricAction record = new CMetricAction();
    record.setName(actionName);

    record.setProject(project);
    record.setEnvironment(environment);

    record.setActionTime(actionTime);
    record.setDuration(duration);

    if (metrics != null) {
      record.getMetrics().addAll(metrics);
    }

    CMetricsDao.merge(record);
  }
}
