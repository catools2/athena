package org.catools.atlassian.etl.jira.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

import java.util.List;

public class CEtlJiraConfigs {

  public static class JiraSync {
    public static List<String> getFieldsToRead() {
      return CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_ETL_JIRA_FIELDS_TO_SYNC);
    }

    @Getter
    @AllArgsConstructor
    private enum Configs implements CHoconPath {
      CATOOLS_ATLASSIAN_ETL_JIRA_FIELDS_TO_SYNC("catools.atlassian.etl.jira.fields_to_sync");
      private final String path;
    }
  }
}
