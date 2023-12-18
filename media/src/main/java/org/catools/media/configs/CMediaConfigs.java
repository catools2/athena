package org.catools.media.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

@UtilityClass
public class CMediaConfigs {
  public static float getDefaultMatchPrecision() {
    return CHocon.asDouble(Configs.CATOOLS_MEDIA_DEFAULT_MATCH_PRECISION).floatValue();
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_MEDIA_DEFAULT_MATCH_PRECISION("catools.media.default_match_precision");

    private final String path;
  }
}
