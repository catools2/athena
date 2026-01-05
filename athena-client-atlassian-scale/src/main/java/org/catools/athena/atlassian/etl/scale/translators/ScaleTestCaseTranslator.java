package org.catools.athena.atlassian.etl.scale.translators;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.model.ScaleChangeHistory;
import org.catools.athena.atlassian.etl.scale.model.ScaleChangeHistoryItem;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestCase;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.model.tms.StatusTransitionDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getItemType;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getMetaData;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getPriority;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getStatus;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getUser;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getVersion;

@Slf4j
public class ScaleTestCaseTranslator {

  public static ItemDto translateTestCase(final String projectCode, ScaleTestCase testCase) {
    Objects.requireNonNull(projectCode);
    Objects.requireNonNull(testCase);

    ItemDto item = new ItemDto();
    item.setCode(testCase.getKey());

    item.setName(testCase.getName());
    item.setCreatedBy(getUser(testCase.getCreatedBy()));
    item.setCreatedOn(testCase.getCreatedOn() == null ? null : testCase.getCreatedOn().toInstant());
    item.setUpdatedBy(getUser(testCase.getUpdatedBy()));
    item.setUpdatedOn(testCase.getUpdatedOn() == null ? null : testCase.getUpdatedOn().toInstant());
    item.setProject(projectCode);
    item.setStatus(getStatus(testCase.getStatus()));
    item.setPriority(getPriority(testCase.getPriority()));
    item.setType(getItemType("Test"));
    item.setVersions(getIssueVersions(testCase, projectCode));

    item.getMetadata().clear();
    addIssueMetaData(testCase, item);

    item.getStatusTransitions().clear();
    addStatusTransition(testCase, item);

    return item;
  }

  private static void addStatusTransition(ScaleTestCase issue, ItemDto item) {
    if (issue.getHistories() != null) {
      List<StatusTransitionDto> statusTransitions = readStatusTransitionInfoFromResponse(issue);

      if (!statusTransitions.isEmpty()) {
        // Set from status to previous status for each transition
        setFromStatusToPreviousStatus(item, statusTransitions);
      }
    }
  }

  private static void setFromStatusToPreviousStatus(ItemDto item, List<StatusTransitionDto> statusTransitions) {
    statusTransitions.sort(Comparator.comparing(StatusTransitionDto::getOccurred));
    statusTransitions.get(0).setFrom(getStatus("Open"));

    for (int i = 1; i < statusTransitions.size(); i++) {
      statusTransitions.get(i).setFrom(statusTransitions.get(i - 1).getTo());
    }

    item.setStatusTransitions(statusTransitions.stream().collect(Collectors.toSet()));
  }

  private static List<StatusTransitionDto> readStatusTransitionInfoFromResponse(ScaleTestCase issue) {
    List<StatusTransitionDto> statusTransitions = new ArrayList<>();
    for (ScaleChangeHistory changelog : issue.getHistories()) {
      if (changelog.getChangeHistoryItems() == null) {
        continue;
      }

      for (ScaleChangeHistoryItem statusChangelog : changelog.getChangeHistoryItems()) {
        if (!StringUtils.equalsIgnoreCase(statusChangelog.getFieldName(), "status")) {
          continue;
        }

        Instant occurred = changelog.getHistoryDate().toInstant();
        String to = getStatus(statusChangelog.getNewValue());
        statusTransitions.add(new StatusTransitionDto(null, to, getUser(changelog.getUserKey()), occurred));
      }
    }
    return statusTransitions;
  }

  private static void addIssueMetaData(ScaleTestCase testCase, ItemDto item) {
    if (StringUtils.isNotEmpty(testCase.getComponent())) {
      item.getMetadata().add(getMetaData("Component", testCase.getComponent()));
    }

    if (StringUtils.isNotEmpty(testCase.getLastTestResultStatus())) {
      item.getMetadata().add(getMetaData("LastTestResultStatus", testCase.getLastTestResultStatus()));
    }

    if (StringUtils.isNotEmpty(testCase.getFolder())) {
      item.getMetadata().add(getMetaData("Folder", testCase.getFolder()));
    }

    if (StringUtils.isNotEmpty(testCase.getOwner())) {
      item.getMetadata().add(getMetaData("Owner", testCase.getOwner()));
    }

    if (StringUtils.isNotEmpty(testCase.getCreatedBy())) {
      item.getMetadata().add(getMetaData("CreatedBy", testCase.getCreatedBy()));
    }

    if (testCase.getLabels() != null) {
      for (final String label : testCase.getLabels()) {
        item.getMetadata().add(getMetaData("Label", label));
      }
    }

    if (testCase.getIssueLinks() != null) {
      for (final String issueLink : testCase.getIssueLinks()) {
        item.getMetadata().add(getMetaData("IssueLink", issueLink));
      }
    }

    if (testCase.getCustomFields() != null) {
      testCase.getCustomFields().forEach((k, v) -> item.getMetadata().add(getMetaData(k, v)));
    }
  }

  private static Set<String> getIssueVersions(ScaleTestCase testCase, String projectCode) {
    HashSet<String> versions = new HashSet<>();
    if (testCase == null || testCase.getCustomFields() == null || testCase.getCustomFields().isEmpty()) {
      return versions;
    }

    Set<String> potentialVersions = testCase.getCustomFields()
        .entrySet()
        .stream()
        .filter(e -> e.getKey().toLowerCase().contains("version"))
        .map(Map.Entry::getValue)
        .collect(Collectors.toSet());

    for (final String potentialVersion : potentialVersions) {
      for (final String version : potentialVersion.split(",\\s+")) {
        versions.add(getVersion(version, projectCode));
      }
    }

    return versions;
  }
}
