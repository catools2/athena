package org.catools.athena.rest.feign.metrics.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.metrics.clients.MetricClient;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getClient;

@UtilityClass
public class MetricsUtils {
  public static MetricClient getMetricClient() {
    return getClient(MetricClient.class, CoreConfigs.getAthenaHost());
  }

}
