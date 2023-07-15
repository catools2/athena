package org.catools.atlassian.etl.jira.translators;

import com.atlassian.jira.rest.client.api.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.jira.configs.CEtlJiraConfigs;
import org.catools.atlassian.etl.jira.translators.parsers.CEtlJiraParser;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;
import org.catools.tms.etl.model.*;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CEtlJiraTranslator {
  public static CEtlItems translateIssues(CSet<Issue> issues) {
    return new CEtlItems(issues.mapToSet(CEtlJiraTranslator::translateIssue));
  }

  public static CEtlItem translateIssue(Issue issue) {
    Objects.requireNonNull(issue);

    try {
      CEtlProject project = getProject(issue);
      CEtlVersions versions = getIssueVersions(issue, project);
      CEtlStatus status = getStatus(issue);
      CEtlPriority priority = getPriority(issue);
      CEtlItemType type = getItemType(issue);

      CEtlItem item = new CEtlItem(
          issue.getKey(),
          issue.getSummary(),
          issue.getCreationDate().toDate(),
          issue.getUpdateDate() == null ? null : issue.getUpdateDate().toDate(),
          project,
          type,
          versions,
          status,
          priority);

      addIssueMetaData(issue, item);
      addStatusTransition(issue, item);

      return item;
    } catch (Throwable t) {
      log.error("Failed to translate issue {} to item.", issue, t);
      throw t;
    }
  }

  public static CEtlVersion translateVersion(CEtlProject project, Version version) {
    return version == null || CStringUtil.isBlank(version.getName()) ?
        CEtlVersion.UNSET :
        new CEtlVersion(version.getName(), project);
  }

  private static void addStatusTransition(Issue issue, CEtlItem item) {
    if (issue.getChangelog() != null) {
      for (ChangelogGroup changelog : issue.getChangelog()) {
        if (changelog == null || changelog.getAuthor() == null) {
          continue;
        }

        CList<ChangelogItem> transitions = new CSet<>(changelog.getItems())
            .getAll(f -> f != null && CStringUtil.equalsIgnoreCase(f.getField(), "status"));

        for (ChangelogItem statusChangelog : transitions) {
          if (statusChangelog == null) continue;

          Date occurred = changelog.getCreated() == null ? null : changelog.getCreated().toDate();
          CEtlStatus from = getStatus(statusChangelog.getFromString());
          CEtlStatus to = getStatus(statusChangelog.getToString());
          item.addStatusTransition(new CEtlItemStatusTransition(occurred, from, to, item));
        }
      }
    }
  }

  private static void addIssueMetaData(Issue issue, CEtlItem item) {
    for (BasicComponent component : issue.getComponents()) {
      item.addItemMetaData(new CEtlItemMetaData("Component", component.getName()));
    }

    if (issue.getAssignee() != null
        && CStringUtil.isNotBlank(issue.getAssignee().getEmailAddress())) {
      item.addItemMetaData(new CEtlItemMetaData("Assignee", issue.getAssignee().getEmailAddress()));
    }

    if (issue.getLabels() != null) {
      for (String label : issue.getLabels()) {
        item.addItemMetaData(new CEtlItemMetaData("Label", label));
      }
    }

    if (issue.getFields() != null) {
      List<String> fieldsToRead = CEtlJiraConfigs.JiraSync.getFieldsToRead();
      CList<IssueField> noneNull = new CSet<>(issue.getFields()).getAll(CEtlJiraTranslator::valueIsNotNull);
      for (IssueField field : noneNull) {
        CHashMap<String, String> fieldsToSync = CEtlJiraParser.parserJiraField(field).getAll((k, v) -> fieldsToRead.contains(k));
        fieldsToSync.forEach((key, val) -> item.addItemMetaData(new CEtlItemMetaData(key, val)));
      }
    }
  }

  private static CEtlVersions getIssueVersions(Issue issue, CEtlProject project) {
    CEtlVersions versions = new CEtlVersions();

    if (issue.getFixVersions() != null) {
      for (Version fixVersion : issue.getFixVersions()) {
        versions.add(translateVersion(project, fixVersion));
      }
    }

    if (issue.getAffectedVersions() != null) {
      for (Version fixVersion : issue.getAffectedVersions()) {
        versions.add(translateVersion(project, fixVersion));
      }
    }

    return versions;
  }

  private static CEtlProject getProject(Issue issue) {
    return issue.getProject() == null || CStringUtil.isBlank(issue.getProject().getName()) ?
        CEtlProject.UNSET :
        new CEtlProject(issue.getProject().getName());
  }

  private static CEtlItemType getItemType(Issue issue) {
    return issue.getIssueType() == null || CStringUtil.isBlank(issue.getIssueType().getName()) ?
        CEtlItemType.UNSET :
        new CEtlItemType(issue.getIssueType().getName());
  }

  private static CEtlPriority getPriority(Issue issue) {
    return issue.getPriority() == null || CStringUtil.isBlank(issue.getPriority().getName()) ?
        CEtlPriority.UNSET :
        new CEtlPriority(issue.getPriority().getName());
  }

  private static CEtlStatus getStatus(String statusName) {
    return CStringUtil.isBlank(statusName) ? CEtlStatus.UNSET : new CEtlStatus(statusName);
  }

  private static CEtlStatus getStatus(Issue issue) {
    return issue.getStatus() == null || CStringUtil.isBlank(issue.getStatus().getName()) ?
        CEtlStatus.UNSET :
        new CEtlStatus(issue.getStatus().getName());
  }

  private static boolean valueIsNotNull(IssueField f) {
    return f.getValue() != null
        && f.getValue() != JSONObject.EXPLICIT_NULL
        && f.getValue() != JSONObject.NULL;
  }
}
