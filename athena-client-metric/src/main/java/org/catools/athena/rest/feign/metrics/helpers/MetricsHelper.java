package org.catools.athena.rest.feign.metrics.helpers;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.metrics.configs.MetricsConfigs;
import org.catools.athena.rest.feign.metrics.utils.MetricsUtils;

import java.util.Optional;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getEntityId;


@Slf4j
public class MetricsHelper {

  public static Optional<Long> saveMetric(MetricDto metric) {
    if (!MetricsConfigs.isEnable()) return Optional.empty();
    metric.setEnvironment(CoreCache.readEnvironment(CoreConfigs.getEnvironment()).getCode());
    metric.setProject(CoreCache.readProject(CoreConfigs.getProject()).getCode());
    Response response = MetricsUtils.getMetricClient().saveMetric(metric);
    return getEntityId(response);
  }
}
