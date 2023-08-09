package org.catools.common.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CStringUtil;

import java.util.TimeZone;

public class CDateConfigs {

  /**
   * get default timezone for CDate && CDateUtil default methods.
   * You can control this behaviour by setting CATOOLS_DATE_TIME_ZONE value to valid {@link TimeZone} value
   *
   * @return default timezone
   */
  public static TimeZone getDefaultTimeZone() {
    String val = CHocon.asString(Configs.CATOOLS_DATE_TIME_ZONE);
    return CStringUtil.isBlank(val) ? TimeZone.getDefault() : TimeZone.getTimeZone(val);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_DATE_TIME_ZONE("catools.date.time_zone");

    private final String path;
  }
}
