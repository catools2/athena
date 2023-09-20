package org.catools.atlassian.etl.jira.translators;

import com.atlassian.jira.rest.client.api.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.jira.configs.CEtlJiraConfigs;
import org.catools.atlassian.etl.jira.translators.parsers.CEtlJiraParser;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;
import org.catools.etl.tms.cache.CEtlCacheManager;
import org.catools.etl.tms.dao.CEtlItemDao;
import org.catools.etl.tms.model.*;
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

    CEtlItem item = CEtlItemDao.find(CEtlItem.class, String.valueOf(issue.getKey()));
    if (item == null) {
      item = new CEtlItem();
      item.setId(String.valueOf(issue.getKey()));
    }

    try {
      CEtlProject project = getProject(issue);
      item.setProject(project);
      item.setVersions(getIssueVersions(issue, project));
      item.setStatus(getStatus(issue));
      item.setPriority(getPriority(issue));
      item.setType(getItemType(issue));
      item.setName(CStringUtil.substring(issue.getSummary(), 0, 1000));
      item.setCreated(issue.getCreationDate().toDate());
      item.setUpdated(issue.getUpdateDate() == null ? null : issue.getUpdateDate().toDate());

      item.getMetadata().clear();
      addIssueMetaData(issue, item);

      item.getStatusTransitions().clear();
      addStatusTransition(issue, item);

      return item;
    } catch (Throwable t) {
      log.error("Failed to translate issue {} to item.", issue, t);
      throw t;
    }
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
      item.addItemMetaData(getMetaData("Component", component.getName()));
    }

    if (issue.getAssignee() != null
        && CStringUtil.isNotBlank(issue.getAssignee().getEmailAddress())) {
      item.addItemMetaData(getMetaData("Assignee", issue.getAssignee().getEmailAddress()));
    }

    if (issue.getLabels() != null) {
      for (String label : issue.getLabels()) {
        item.addItemMetaData(getMetaData("Label", label));
      }
    }

    if (issue.getFields() != null) {
      List<String> fieldsToRead = CEtlJiraConfigs.JiraSync.getFieldsToRead();
      CList<IssueField> noneNull = new CSet<>(issue.getFields()).getAll(CEtlJiraTranslator::valueIsNotNull);
      for (IssueField field : noneNull) {
        CHashMap<String, String> fieldsToSync = CEtlJiraParser.parserJiraField(field).getAll((k, v) -> fieldsToRead.contains(k));
        fieldsToSync.forEach((key, val) -> item.addItemMetaData(getMetaData(key, val)));
      }
    }
  }

  public static CEtlVersion translateVersion(CEtlProject project, Version version) {
    return version == null || CStringUtil.isBlank(version.getName()) ?
        CEtlVersion.UNSET :
        CEtlCacheManager.readVersion(new CEtlVersion(version.getName(), project));
  }

  private static CEtlVersions getIssueVersions(Issue issue, CEtlProject project) {
    CEtlVersions versions = new CEtlVersions();

    if (issue.getFixVersions() != null) {
      for (Version fixVersion : issue.getFixVersions()) {
        versions.add(CEtlCacheManager.readVersion(translateVersion(project, fixVersion)));
      }
    }

    if (issue.getAffectedVersions() != null) {
      for (Version fixVersion : issue.getAffectedVersions()) {
        versions.add(CEtlCacheManager.readVersion(translateVersion(project, fixVersion)));
      }
    }

    return versions;
  }

  private static CEtlItemMetaData getMetaData(String name, String value) {
    return CEtlCacheManager.readMetaData(new CEtlItemMetaData(name, value));
  }

  private static CEtlProject getProject(Issue issue) {
    return issue.getProject() == null || CStringUtil.isBlank(issue.getProject().getName()) ?
        CEtlProject.UNSET :
        CEtlCacheManager.readProject(new CEtlProject(issue.getProject().getName()));
  }

  private static CEtlItemType getItemType(Issue issue) {
    return issue.getIssueType() == null || CStringUtil.isBlank(issue.getIssueType().getName()) ?
        CEtlItemType.UNSET :
        CEtlCacheManager.readType(new CEtlItemType(issue.getIssueType().getName()));
  }

  private static CEtlPriority getPriority(Issue issue) {
    return issue.getPriority() == null || CStringUtil.isBlank(issue.getPriority().getName()) ?
        CEtlPriority.UNSET :
        CEtlCacheManager.readPriority(new CEtlPriority(issue.getPriority().getName().toUpperCase()));
  }

  private static CEtlStatus getStatus(String statusName) {
    return CStringUtil.isBlank(statusName) ?
        CEtlStatus.UNSET :
        CEtlCacheManager.readStatus(new CEtlStatus(statusName.toUpperCase()));
  }

  private static CEtlStatus getStatus(Issue issue) {
    return issue.getStatus() == null || CStringUtil.isBlank(issue.getStatus().getName()) ?
        CEtlStatus.UNSET :
        CEtlCacheManager.readStatus(new CEtlStatus(issue.getStatus().getName().toUpperCase()));
  }

  private static boolean valueIsNotNull(IssueField f) {
    return f.getValue() != null
        && f.getValue() != JSONObject.EXPLICIT_NULL
        && f.getValue() != JSONObject.NULL;
  }
}
