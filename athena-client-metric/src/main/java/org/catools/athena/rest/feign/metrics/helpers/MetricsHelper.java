package org.catools.athena.rest.feign.metrics.helpers;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getEntityId;

import feign.Response;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.metrics.configs.MetricsConfigs;
import org.catools.athena.rest.feign.metrics.utils.MetricsUtils;

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
