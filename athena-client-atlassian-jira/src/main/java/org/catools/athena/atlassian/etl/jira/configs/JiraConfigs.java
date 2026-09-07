package org.catools.athena.atlassian.etl.jira.configs;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;

@UtilityClass
public class JiraConfigs {

  static {
    reload();
  }

  @Setter @Getter private static String jiraHost;

  @Setter @Getter private static String jiraAccessToken;

  @Setter @Getter private static String jiraUsername;

  @Setter @Getter private static String jiraPassword;

  @Setter @Getter private static Long delayBetweenCallsInMilliseconds;

  @Setter @Getter private static List<String> issueTypes;

  @Setter @Getter private static List<String> fieldsToRead;

  /** Labels whose first-added date is harvested from the Jira changelog. */
  @Setter @Getter private static List<String> labelHistoryToTrack;

  /** Ceiling for a single Jira response body. Matches the Atlassian client default of 100MB. */
  @Setter @Getter private static long maxResponseSizeInBytes;

  public static void reload() {
    jiraHost = ConfigUtils.getString("athena.jira.host");
    jiraAccessToken = ConfigUtils.getString("athena.jira.access_token");
    jiraUsername = ConfigUtils.getString("athena.jira.username");
    jiraPassword = ConfigUtils.getString("athena.jira.password");
    delayBetweenCallsInMilliseconds =
        ConfigUtils.getLong("athena.jira.delay_between_calls_in_milliseconds", 1000L);
    issueTypes =
        ConfigUtils.getStrings("athena.jira.issue_types", List.of("Epic", "Story", "Test", "Bug"));
    maxResponseSizeInBytes =
        ConfigUtils.getLong("athena.jira.max_response_size_in_bytes", 104857600L);
    labelHistoryToTrack =
        ConfigUtils.getStrings(
            "athena.jira.label_history_to_track",
            List.of("AIGenerated", "AIReviewed", "AIAssisted", "AIFixed"));
    fieldsToRead =
        ConfigUtils.getStrings(
            "athena.jira.fields_to_sync",
            List.of(
                "Affected Version",
                "Affected Version/s",
                "Component",
                "IssueLink",
                "Label",
                "Epic Name",
                "Epic Link",
                "Component Version",
                "Parent",
                "Parent Link",
                "Team",
                "Assignee",
                "Labels",
                "Resolution",
                "Fix Version/s",
                "Affects Version/s",
                "Epic Status",
                "Story Points",
                "Actual Story Points",
                "Baseline Story Points",
                "Completed Story Points",
                "Time Spent"));
  }
}
