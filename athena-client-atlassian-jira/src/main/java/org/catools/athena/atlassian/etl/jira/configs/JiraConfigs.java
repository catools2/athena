package org.catools.athena.atlassian.etl.jira.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;

import java.util.List;

@UtilityClass
public class JiraConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static String jiraHost;

  @Setter
  @Getter
  private static String jiraAccessToken;

  @Setter
  @Getter
  private static String jiraUsername;

  @Setter
  @Getter
  private static String jiraPassword;

  @Setter
  @Getter
  private static Long delayBetweenCallsInMilliseconds;

  @Setter
  @Getter
  private static List<String> issueTypes;

  @Setter
  @Getter
  private static List<String> fieldsToRead;

  public static void reload() {
    jiraHost = ConfigUtils.getString("athena.jira.host");
    jiraAccessToken = ConfigUtils.getString("athena.jira.access_token");
    jiraUsername = ConfigUtils.getString("athena.jira.username");
    jiraPassword = ConfigUtils.getString("athena.jira.password");
    delayBetweenCallsInMilliseconds = ConfigUtils.getLong("athena.jira.delay_between_calls_in_milliseconds", 1000L);
    issueTypes = ConfigUtils.getStrings("athena.jira.issue_types", List.of("Epic", "Story", "Test", "Bug"));
    fieldsToRead = ConfigUtils.getStrings("athena.jira.fields_to_sync",
        List.of("Affected Version",
            "Affected Version/s",
            "Component",
            "IssueLink",
            "Label",
            "Epic Name",
            "Epic Link",
            "Component Version",
            "Parent",
            "Parent Link",
            "Team"));
  }

}
