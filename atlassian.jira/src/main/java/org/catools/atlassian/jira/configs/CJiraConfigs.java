package org.catools.atlassian.jira.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CStringUtil;

import java.net.URI;
import java.util.List;

public class CJiraConfigs {
  public static class Jira {
    public static URI getHomeUri() {
      try {
        String string = CHocon.asString(Configs.CATOOLS_ATLASSIAN_JIRA_HOME);
        if (CStringUtil.isBlank(string)) {
          return null;
        }
        return new URI(string);
      } catch (Throwable t) {
        throw new RuntimeException(t);
      }
    }

    public static String getUserName() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_JIRA_USERNAME);
    }

    public static String getPassword() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_JIRA_PASSWORD);
    }

    public static String getProjectKey() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_JIRA_PROJECT_KEY);
    }

    public static String getVersionName() {
      return CHocon.asString(Configs.CATOOLS_ATLASSIAN_JIRA_VERSION_NAME);
    }

    public static List<String> getDateFormats() {
      return CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_JIRA_DATE_FORMAT);
    }

    public static int getSearchBufferSize() {
      return CHocon.asInteger(Configs.CATOOLS_ATLASSIAN_JIRA_SEARCH_BUFFER_SIZE);
    }

    public static int getDelayBetweenCallsInMilliseconds() {
      return CHocon.asInteger(Configs.CATOOLS_ATLASSIAN_JIRA_DELAY_BETWEEN_CALLS_IN_MILLI);
    }

    @Getter
    @AllArgsConstructor
    private enum Configs implements CHoconPath {
      CATOOLS_ATLASSIAN_JIRA_HOME("catools.atlassian.jira.home"),
      CATOOLS_ATLASSIAN_JIRA_USERNAME("catools.atlassian.jira.username"),
      CATOOLS_ATLASSIAN_JIRA_PASSWORD("catools.atlassian.jira.password"),
      CATOOLS_ATLASSIAN_JIRA_DATE_FORMAT("catools.atlassian.jira.date_format"),
      CATOOLS_ATLASSIAN_JIRA_PROJECT_KEY("catools.atlassian.jira.project_key"),
      CATOOLS_ATLASSIAN_JIRA_VERSION_NAME("catools.atlassian.jira.version_name"),
      CATOOLS_ATLASSIAN_JIRA_DELAY_BETWEEN_CALLS_IN_MILLI("catools.atlassian.jira.delay_between_calls_in_millisecond"),
      CATOOLS_ATLASSIAN_JIRA_SEARCH_BUFFER_SIZE("catools.atlassian.jira.search_buffer_size");

      private final String path;
    }
  }
}
