package org.catools.athena.rest.feign.apispec.configs;

import lombok.Getter;
import lombok.Setter;
import org.catools.athena.rest.feign.apispec.entity.SpecInfoSet;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;
import org.catools.athena.rest.feign.common.utils.JsonUtils;

import java.util.List;

public class OpenApiConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static List<String> specNames;

  @Setter
  @Getter
  private static List<String> specUrls;

  @Setter
  @Getter
  private static SpecInfoSet specInfoSet;

  public static void setSpecInfo(String input) {
    specInfoSet = JsonUtils.readValue(input, SpecInfoSet.class);
  }

  public static void reload() {
    specNames = ConfigUtils.getStrings("athena.openapi.spec.names");
    specUrls = ConfigUtils.getStrings("athena.openapi.spec.urls");
    specInfoSet = ConfigUtils.asModel("athena.openapi.specs", SpecInfoSet.class);
  }
}