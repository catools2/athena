package org.catools.athena.atlassian.etl.scale.client;

import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
import org.catools.athena.rest.feign.common.utils.FeignUtils;

public class ScaleClient {
  public static <T> T getScaleAtmClient(Class<T> clazz) {
    return getClient(clazz, ScaleConfigs.getAtmUri());
  }

  public static <T> T getScaleTestsClient(Class<T> clazz) {
    return getClient(clazz, ScaleConfigs.getTestsUri());
  }

  private static <T> T getClient(Class<T> clazz, String host) {
    String scaleAccessToken = ScaleConfigs.getScaleAccessToken();
    if (StringUtils.isNoneBlank(scaleAccessToken)) {
      return FeignUtils.getClient(clazz, host, ScaleConfigs.getScaleAccessToken());
    }

    return FeignUtils.getClient(clazz, host, ScaleConfigs.getScaleUsername(), ScaleConfigs.getScalePassword());
  }

}
