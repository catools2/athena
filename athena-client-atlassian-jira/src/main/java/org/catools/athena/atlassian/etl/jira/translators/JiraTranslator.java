package org.catools.athena.atlassian.etl.jira.translators;

import com.atlassian.jira.rest.client.api.domain.ChangelogGroup;
import com.atlassian.jira.rest.client.api.domain.ChangelogItem;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.model.tms.StatusTransitionDto;
import org.catools.athena.rest.feign.tms.helpers.EtlHelper;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class JiraTranslator {

  public static ItemDto translateIssue(Issue issue, String projectKey, List<String> fieldsToRead) {
    Objects.requireNonNull(issue);

    ItemDto item = new ItemDto();
    item.setCode(issue.getKey());

    item.setProject(TranslatorHelper.getProject(issue.getProject()));
    item.setVersions(TranslatorHelper.getIssueVersions(issue, projectKey));
    item.setStatus(TranslatorHelper.getStatus(issue.getStatus()));
    item.setPriority(TranslatorHelper.getPriority(issue.getPriority()));
    item.setType(TranslatorHelper.getItemType(issue.getIssueType()));
    item.setName(StringUtils.substring(issue.getSummary(), 0, 1000));
    item.setCreatedOn(issue.getCreationDate().toDate().toInstant());
    item.setCreatedBy(TranslatorHelper.getUser(issue.getReporter()));
    item.setUpdatedOn(issue.getUpdateDate() == null ? null : issue.getUpdateDate().toDate().toInstant());

    item.getMetadata().clear();
    TranslatorHelper.addIssueMetaData(issue, item, fieldsToRead);

    item.getStatusTransitions().clear();
    addStatusTransition(issue, item);

    item.getStatusTransitions()
        .stream()
        .filter(st -> st.getAuthor() != null)
        .min(Comparator.comparing(StatusTransitionDto::getOccurred))
        .ifPresent(st -> item.setUpdatedBy(EtlHelper.getUser(st.getAuthor())));

    log.trace("translate issue:\n {} \nto:\n {}", issue, item);

    return item;
  }

  private static void addStatusTransition(Issue issue, ItemDto item) {
    if (issue.getChangelog() != null) {
      for (ChangelogGroup changelog : issue.getChangelog()) {
        if (changelog == null || changelog.getAuthor() == null) {
          continue;
        }

        List<ChangelogItem> transitions = Sets.newHashSet(changelog.getItems().iterator())
            .stream()
            .filter(f -> f != null && StringUtils.equalsIgnoreCase(f.getField(), "status"))
            .collect(Collectors.toList());

        String author = TranslatorHelper.getUser(changelog.getAuthor());

        for (ChangelogItem statusChangelog : transitions) {
          if (statusChangelog == null) {
            continue;
          }

          Instant occurred = changelog.getCreated() == null ? null : changelog.getCreated().toDate().toInstant();
          String from = EtlHelper.getStatus(statusChangelog.getFromString());
          String to = EtlHelper.getStatus(statusChangelog.getToString());
          item.getStatusTransitions().add(new StatusTransitionDto(from, to, author, occurred));
        }
      }
    }
  }

}
