package org.catools.atlassian.scale.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.collections.CList;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

@UtilityClass
public class CZScaleConfigs {
  @UtilityClass
  public static class Scale {
    public static String getHomeUri() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_SCALE_HOME);
    }

    public static String getAtmUri() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_SCALE_ATM);
    }

    public static String getTestsUri() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_SCALE_TESTS);
    }

    public static String getActiveFolder() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_SCALE_ACTIVE_FOLDER);
    }

    public static String getUserName() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_SCALE_USERNAME);
    }

    public static String getPassword() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_SCALE_PASSWORD);
    }

    public static CList<String> getDateFormats() {
      return CList.of(CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_SCALE_DATE_FORMAT));
    }

    public static int getSearchBufferSize() {
      return CHocon.asInteger(Configs.CATOOLS_ATLASSIAN_SCALE_SEARCH_BUFFER_SIZE);
    }

    public static int getDelayBetweenCallsInMilliseconds() {
      return CHocon.asInteger(Configs.CATOOLS_ATLASSIAN_SCALE_DELAY_BETWEEN_CALLS_IN_MILLI);
    }

    @Getter
    @AllArgsConstructor
    private enum Configs implements CHoconPath {
      CATOOLS_ATLASSIAN_SCALE_HOME("catools.atlassian.scale.home"),
      CATOOLS_ATLASSIAN_SCALE_ATM("catools.atlassian.scale.atm"),
      CATOOLS_ATLASSIAN_SCALE_TESTS("catools.atlassian.scale.tests"),
      CATOOLS_ATLASSIAN_SCALE_USERNAME("catools.atlassian.scale.username"),
      CATOOLS_ATLASSIAN_SCALE_PASSWORD("catools.atlassian.scale.password"),
      CATOOLS_ATLASSIAN_SCALE_DATE_FORMAT("catools.atlassian.scale.date_format"),
      CATOOLS_ATLASSIAN_SCALE_ACTIVE_FOLDER("catools.atlassian.scale.active_folder"),
      CATOOLS_ATLASSIAN_SCALE_DELAY_BETWEEN_CALLS_IN_MILLI("catools.atlassian.scale.delay_between_calls_in_millisecond"),
      CATOOLS_ATLASSIAN_SCALE_SEARCH_BUFFER_SIZE("catools.atlassian.scale.search_buffer_size");

      private final String path;
    }
  }
}
