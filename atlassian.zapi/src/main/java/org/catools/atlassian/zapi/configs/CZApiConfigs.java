package org.catools.atlassian.zapi.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

@UtilityClass
public class CZApiConfigs {

  @UtilityClass
  public static class ZApi {
    public static final CHashMap<String, String> statusMap = new CHashMap<>();

    public static String getZApiUri() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_ZAPI_HOME);
    }

    public static CHashMap<String, String> getStatusMap() {
      if (!statusMap.isEmpty()) {
        return statusMap;
      }

      for (String[] strings :
          CList.of(CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_ZAPI_STATUS_MAP))
              .mapToSet(s -> s.split(":"))) {
        statusMap.put(strings[0].trim(), strings[1].trim());
      }

      // we need to ensure at the beginning that we map everything to avoid issues after execution
      assert statusMap.isNotEmpty();

      return statusMap;
    }

    public static String getUserName() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_ZAPI_USERNAME);
    }

    public static String getPassword() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_ZAPI_PASSWORD);
    }

    public static CList<String> getDateFormats() {
      return CList.of(CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_ZAPI_DATE_FORMAT));
    }

    public static int getSearchBufferSize() {
      return CHocon.asInteger(Configs.CATOOLS_ATLASSIAN_ZAPI_SEARCH_BUFFER_SIZE);
    }

    public static int getDelayBetweenCallsInMilliseconds() {
      return CHocon.asInteger(Configs.CATOOLS_ATLASSIAN_ZAPI_DELAY_BETWEEN_CALLS_IN_MILLISECOND);
    }

    @Getter
    @AllArgsConstructor
    private enum Configs implements CHoconPath {
      CATOOLS_ATLASSIAN_ZAPI_HOME("catools.atlassian.zapi.home"),
      CATOOLS_ATLASSIAN_ZAPI_USERNAME("catools.atlassian.zapi.username"),
      CATOOLS_ATLASSIAN_ZAPI_PASSWORD("catools.atlassian.zapi.password"),
      CATOOLS_ATLASSIAN_ZAPI_STATUS_MAP("catools.atlassian.zapi.status_map"),
      CATOOLS_ATLASSIAN_ZAPI_DATE_FORMAT("catools.atlassian.zapi.date_format"),
      CATOOLS_ATLASSIAN_ZAPI_SEARCH_BUFFER_SIZE("catools.atlassian.zapi.search_buffer_size"),
      CATOOLS_ATLASSIAN_ZAPI_DELAY_BETWEEN_CALLS_IN_MILLISECOND("catools.atlassian.zapi.delay_between_calls_in_millisecond");

      private final String path;
    }
  }
}
