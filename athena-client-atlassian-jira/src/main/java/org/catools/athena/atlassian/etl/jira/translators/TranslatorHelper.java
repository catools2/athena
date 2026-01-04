package org.catools.athena.atlassian.etl.jira.translators;

import com.atlassian.jira.rest.client.api.domain.BasicComponent;
import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.BasicUser;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.IssueLink;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.RelativeTask;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.atlassian.jira.rest.client.api.domain.Version;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.jira.translators.parsers.JiraParser;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.tms.helpers.EtlHelper;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getMetaData;

@Slf4j
@UtilityClass
public class TranslatorHelper {
  private static final String UNSET = "UNSET";

  public static String getUser(BasicUser user) {
    if (user == null) {
      return null;
    }

    Set<UserAliasDto> aliases = Sets.newHashSet(new UserAliasDto(user.getName()), new UserAliasDto(user.getDisplayName()));
    return CoreCache.readUser(new UserDto(user.getName(), aliases)).getUsername();
  }

  public static void addIssueMetaData(Issue issue, ItemDto item, List<String> fieldsToRead) {
    for (BasicComponent component : issue.getComponents()) {
      item.getMetadata().add(getMetaData("Component", component.getName()));
    }

    if (issue.getAssignee() != null && StringUtils.isNotBlank(issue.getAssignee().getEmailAddress())) {
      item.getMetadata().add(getMetaData("Assignee", issue.getAssignee().getEmailAddress()));
    }

    if (issue.getLabels() != null) {
      for (final String label : issue.getLabels()) {
        item.getMetadata().add(getMetaData("Label", label));
      }
    }

    if (issue.getIssueLinks() != null) {
      for (final IssueLink issueLink : issue.getIssueLinks()) {
        item.getMetadata().add(getMetaData("Link-" + issueLink.getIssueLinkType().getName(), issueLink.getTargetIssueKey()));
      }
    }

    if (issue.getSubtasks() != null) {
      for (final RelativeTask issueLink : issue.getSubtasks()) {
        item.getMetadata().add(getMetaData("SubTask", issueLink.getIssueKey()));
      }
    }

    if (issue.getFields() != null) {
      Set<IssueField> noneNull = Sets.newHashSet(issue.getFields().iterator())
          .stream()
          .filter(TranslatorHelper::fieldIsNotNull)
          .collect(Collectors.toSet());
      for (IssueField field : noneNull) {
        Stream<Map.Entry<String, String>> fieldsToSync = JiraParser.parserJiraField(field).entrySet().stream();

        if (!fieldsToRead.isEmpty()) {
          fieldsToSync = fieldsToSync.filter(e -> fieldsToRead.stream().anyMatch(f -> StringUtils.equalsIgnoreCase(f, e.getKey())));
        }

        fieldsToSync.forEach(e -> item.getMetadata().add(getMetaData(e.getKey(), e.getValue())));
      }
    }
  }

  public static String getVersion(Version version, String projectKey) {
    return version == null ? UNSET : EtlHelper.getVersion(version.getName(), projectKey);
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
    return project == null || StringUtils.isBlank(project.getName()) ?
        UNSET :
        CoreCache.readProject(new ProjectDto(project.getKey(), project.getName())).getCode();
  }

  public static String getItemType(IssueType issueType) {
    return issueType == null ? UNSET : EtlHelper.getItemType(issueType.getName());
  }

  public static String getPriority(BasicPriority priority) {
    return priority == null ? UNSET : EtlHelper.getPriority(priority.getName());
  }

  public static String getStatus(Status status) {
    return status == null ? UNSET : EtlHelper.getStatus(status.getName());
  }

  public static boolean fieldIsNotNull(IssueField f) {
    return f.getValue() != null && !"None".equals(String.valueOf(f.getValue())) && f.getValue() != JSONObject.EXPLICIT_NULL && f.getValue() != JSONObject.NULL;
  }
}
