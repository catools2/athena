package org.catools.athena.atlassian.etl.jira.translators;

import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getMetaData;

import com.atlassian.jira.rest.client.api.domain.*;
import com.google.common.collect.Sets;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.jira.client.AthenaJiraClient;
import org.catools.athena.atlassian.etl.jira.configs.JiraConfigs;
import org.catools.athena.atlassian.etl.jira.translators.parsers.JiraParser;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.tms.helpers.EtlHelper;
import org.codehaus.jettison.json.JSONObject;

@Slf4j
@UtilityClass
public class TranslatorHelper {
  private static final String UNSET = "UNSET";

  public static String getUser(BasicUser user) {
    if (user == null) {
      return null;
    }

    BasicUser jiraUser = AthenaJiraClient.getUser(user.getName());
    // A deleted account resolves to null; the issue payload still carries a usable name, and
    // attributing the transition to that beats losing the whole issue.
    return CoreCache.readUser(toUserDto(user, jiraUser == null ? user : jiraUser)).getUsername();
  }

  static UserDto toUserDto(BasicUser user, BasicUser jiraUser) {
    // Fall through to the account name last: a deleted account can arrive with no display name at
    // all, and defaultIfBlank over two blanks yields a blank username that then propagates into
    // core as a nameless user.
    String displayName =
        StringUtils.firstNonBlank(
            jiraUser.getDisplayName(), user.getDisplayName(), jiraUser.getName(), user.getName());
    Set<UserAliasDto> aliases =
        Sets.newHashSet(new UserAliasDto(jiraUser.getName()), new UserAliasDto(displayName));
    return new UserDto(displayName, aliases);
  }

  public static void addIssueMetaData(Issue issue, ItemDto item, List<String> fieldsToRead) {
    // JRJC lifts its well-known fields out of issue.getFields() into typed accessors, so they never
    // reach the generic loop below and must be added explicitly -- listing them in fields_to_sync
    // only filters a stream they were never part of.
    if (issue.getFixVersions() != null) {
      for (Version fixVersion : issue.getFixVersions()) {
        item.getMetadata().add(getMetaData("FixVersion", fixVersion.getName()));
      }
    }

    if (issue.getAffectedVersions() != null) {
      for (Version affectedVersion : issue.getAffectedVersions()) {
        item.getMetadata().add(getMetaData("AffectedVersion", affectedVersion.getName()));
      }
    }

    if (issue.getResolution() != null && StringUtils.isNotBlank(issue.getResolution().getName())) {
      item.getMetadata().add(getMetaData("Resolution", issue.getResolution().getName()));
    }

    if (issue.getParentTask() != null
        && StringUtils.isNotBlank(issue.getParentTask().getIssueKey())) {
      item.getMetadata().add(getMetaData("Parent", issue.getParentTask().getIssueKey()));
    }

    addLabelHistory(issue, item, JiraConfigs.getLabelHistoryToTrack());

    for (BasicComponent component : issue.getComponents()) {
      item.getMetadata().add(getMetaData("Component", component.getName()));
    }

    if (issue.getAssignee() != null
        && StringUtils.isNotBlank(issue.getAssignee().getEmailAddress())) {
      item.getMetadata().add(getMetaData("Assignee", issue.getAssignee().getEmailAddress()));
    }

    if (issue.getLabels() != null) {
      for (final String label : issue.getLabels()) {
        item.getMetadata().add(getMetaData("Label", label));
      }
    }

    if (issue.getIssueLinks() != null) {
      for (final IssueLink issueLink : issue.getIssueLinks()) {
        item.getMetadata()
            .add(
                getMetaData(
                    "Link-" + issueLink.getIssueLinkType().getName(),
                    issueLink.getTargetIssueKey()));
      }
    }

    if (issue.getSubtasks() != null) {
      for (final RelativeTask issueLink : issue.getSubtasks()) {
        item.getMetadata().add(getMetaData("SubTask", issueLink.getIssueKey()));
      }
    }

    if (issue.getFields() != null) {
      Set<IssueField> noneNull =
          Sets.newHashSet(issue.getFields().iterator()).stream()
              .filter(TranslatorHelper::fieldIsNotNull)
              .collect(Collectors.toSet());
      for (IssueField field : noneNull) {
        Stream<Map.Entry<String, String>> fieldsToSync =
            JiraParser.parserJiraField(field).entrySet().stream();

        if (!fieldsToRead.isEmpty()) {
          fieldsToSync =
              fieldsToSync.filter(
                  e ->
                      fieldsToRead.stream()
                          .anyMatch(f -> StringUtils.equalsIgnoreCase(f, e.getKey())));
        }

        fieldsToSync.forEach(e -> item.getMetadata().add(getMetaData(e.getKey(), e.getValue())));
      }
    }
  }

  /**
   * Harvest, from the changelog, the first time each tracked label was added to the issue. A label
   * only records presence -- it cannot express "checked, and the answer is no" -- so the add date is
   * what makes a population statistic answerable: it separates "not labelled yet" from "labelled
   * after date X". Stored as "&lt;Label&gt;On" so a tracked label lands next to its own flag.
   *
   * <p>Labels are matched on a normalised form (lower-case, alphanumerics only) because the same
   * concept is spelled several ways in practice: AIGenerated, AI-Generated, GenAIGenerated.
   */
  static void addLabelHistory(Issue issue, ItemDto item, List<String> labelsToTrack) {
    if (issue.getChangelog() == null || labelsToTrack == null || labelsToTrack.isEmpty()) {
      return;
    }

    Map<String, Instant> firstAdded = new HashMap<>();

    for (ChangelogGroup group : issue.getChangelog()) {
      if (group == null || group.getCreated() == null || group.getItems() == null) {
        continue;
      }

      Instant occurred = group.getCreated().toDate().toInstant();

      for (ChangelogItem change : group.getItems()) {
        if (change == null || !StringUtils.equalsIgnoreCase(change.getField(), "labels")) {
          continue;
        }

        Set<String> before = normalizeLabels(change.getFromString());
        Set<String> after = normalizeLabels(change.getToString());

        for (String tracked : labelsToTrack) {
          String normalized = normalizeLabel(tracked);
          if (!after.contains(normalized) || before.contains(normalized)) {
            continue;
          }
          // A label can be removed and re-added; the first add is the one that dates the decision.
          firstAdded.merge(tracked, occurred, (a, b) -> a.isBefore(b) ? a : b);
        }
      }
    }

    firstAdded.forEach(
        (label, occurred) -> item.getMetadata().add(getMetaData(label + "On", occurred.toString())));
  }

  static Set<String> normalizeLabels(String labels) {
    Set<String> normalized = new HashSet<>();
    if (StringUtils.isBlank(labels)) {
      return normalized;
    }
    for (String label : StringUtils.split(labels)) {
      String value = normalizeLabel(label);
      if (StringUtils.isNotBlank(value)) {
        normalized.add(value);
      }
    }
    return normalized;
  }

  static String normalizeLabel(String label) {
    return label == null ? "" : label.toLowerCase().replaceAll("[^a-z0-9]", "");
  }

  public static String getVersion(Version version, String projectKey) {
    return version == null
        ? EtlHelper.getVersion(UNSET, projectKey)
        : EtlHelper.getVersion(version.getName(), projectKey);
  }

  public static Set<String> getIssueVersions(Issue issue, String projectKey) {
    Set<String> versions = new HashSet<>();

    if (issue.getFixVersions() != null) {
      for (Version fixVersion : issue.getFixVersions()) {
        versions.add(getVersion(fixVersion, projectKey));
      }
    }

    if (issue.getAffectedVersions() != null) {
      for (Version fixVersion : issue.getAffectedVersions()) {
        versions.add(getVersion(fixVersion, projectKey));
      }
    }

    return versions;
  }

  public static String getProject(BasicProject project) {
    return project == null || StringUtils.isBlank(project.getName())
        ? CoreCache.readProject(new ProjectDto(UNSET, UNSET)).getCode()
        : CoreCache.readProject(new ProjectDto(project.getKey(), project.getName())).getCode();
  }

  public static String getItemType(IssueType issueType) {
    return issueType == null
        ? EtlHelper.getItemType(null)
        : EtlHelper.getItemType(issueType.getName());
  }

  public static String getPriority(BasicPriority priority) {
    return priority == null
        ? EtlHelper.getPriority(null)
        : EtlHelper.getPriority(priority.getName());
  }

  public static String getStatus(Status status) {
    return status == null ? EtlHelper.getStatus(null) : EtlHelper.getStatus(status.getName());
  }

  public static boolean fieldIsNotNull(IssueField f) {
    return f.getValue() != null
        && !"None".equals(String.valueOf(f.getValue()))
        && f.getValue() != JSONObject.EXPLICIT_NULL
        && f.getValue() != JSONObject.NULL;
  }
}
