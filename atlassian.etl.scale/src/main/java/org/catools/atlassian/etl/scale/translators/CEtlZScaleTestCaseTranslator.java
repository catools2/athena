package org.catools.atlassian.etl.scale.translators;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.atlassian.scale.CZScaleClient;
import org.catools.atlassian.scale.model.CZScaleChangeHistory;
import org.catools.atlassian.scale.model.CZScaleChangeHistoryItem;
import org.catools.atlassian.scale.model.CZScaleTestCase;
import org.catools.common.collections.CList;
import org.catools.common.utils.CStringUtil;
import org.catools.tms.etl.model.*;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class CEtlZScaleTestCaseTranslator {
  private static final String TEST_CASE_NAME = "Test";

  public static CEtlItem translateTestCase(CEtlProject project, CEtlVersions versions, String testCaseKey) {
    return translateTestCase(project, versions, CZScaleClient.TestCases.getTestCase(testCaseKey));
  }

  public static CEtlItem translateTestCase(CEtlProject project, CEtlVersions versions, CZScaleTestCase testCase) {
    Objects.requireNonNull(project);
    Objects.requireNonNull(versions);
    Objects.requireNonNull(testCase);
    try {
      CEtlVersions testCaseVersions = getIssueVersions(versions, testCase);
      CEtlStatus status = getStatus(testCase.getStatus());
      CEtlPriority priority = getPriority(testCase.getPriority());
      CEtlItemType type = getItemType();

      CEtlItem item = new CEtlItem(
          testCase.getKey(),
          testCase.getName(),
          testCase.getCreatedOn(),
          testCase.getUpdatedOn(),
          project,
          type,
          testCaseVersions,
          status,
          priority);

      getIssueMetaData(testCase, item);
      getStatusTransition(testCase, item);

      return item;
    } catch (Exception e) {
      log.error("Failed to translate testcase {} to item.", testCase, e);
      throw e;
    }
  }

  private static CEtlItemType getItemType() {
    return new CEtlItemType(TEST_CASE_NAME);
  }

  private static void getStatusTransition(CZScaleTestCase issue, CEtlItem item) {
    if (issue.getHistories() != null) {
      CList<CEtlItemStatusTransition> statusTransitions = readStatusTransitionInfoFromResponse(issue, item);

      if (statusTransitions.isNotEmpty()) {
        // Set from status to previous status for each transition
        setFromStatusToPreviousStatus(item, statusTransitions);
      }
    }
  }

  private static void setFromStatusToPreviousStatus(CEtlItem item, CList<CEtlItemStatusTransition> statusTransitions) {
    statusTransitions.sort(Comparator.comparing(CEtlItemStatusTransition::getOccurred));
    statusTransitions.get(0).setFrom(new CEtlStatus("Open"));
    for (int i = 1; i < statusTransitions.size(); i++) {
      statusTransitions.get(i).setFrom(statusTransitions.get(i - 1).getTo());
    }

    item.setStatusTransitions(statusTransitions.toSet());
  }

  private static CList<CEtlItemStatusTransition> readStatusTransitionInfoFromResponse(CZScaleTestCase issue, CEtlItem item) {
    CList<CEtlItemStatusTransition> statusTransitions = new CList<>();
    for (CZScaleChangeHistory changelog : issue.getHistories()) {
      if (changelog.getChangeHistoryItems() == null) continue;

      for (CZScaleChangeHistoryItem statusChangelog : changelog.getChangeHistoryItems()) {
        if (!CStringUtil.equalsIgnoreCase(statusChangelog.getFieldName(), "status")) continue;

        Date occurred = changelog.getHistoryDate();
        CEtlStatus to = getStatus(statusChangelog.getNewValue());
        statusTransitions.add(new CEtlItemStatusTransition(occurred, null, to, item));
      }
    }
    return statusTransitions;
  }

  private static void getIssueMetaData(CZScaleTestCase testCase, CEtlItem item) {
    if (StringUtils.isNotEmpty(testCase.getComponent())) {
      item.addItemMetaData(new CEtlItemMetaData("Component", testCase.getComponent()));
    }

    if (StringUtils.isNotEmpty(testCase.getLastTestResultStatus())) {
      item.addItemMetaData(new CEtlItemMetaData("LastTestResultStatus", testCase.getLastTestResultStatus()));
    }

    if (StringUtils.isNotEmpty(testCase.getFolder())) {
      item.addItemMetaData(new CEtlItemMetaData("Folder", testCase.getFolder()));
    }

    if (StringUtils.isNotEmpty(testCase.getOwner())) {
      item.addItemMetaData(new CEtlItemMetaData("Owner", testCase.getOwner()));
    }

    if (StringUtils.isNotEmpty(testCase.getCreatedBy())) {
      item.addItemMetaData(new CEtlItemMetaData("CreatedBy", testCase.getCreatedBy()));
    }

    if (testCase.getLabels() != null) {
      for (String label : testCase.getLabels()) {
        item.addItemMetaData(new CEtlItemMetaData("Label", label));
      }
    }

    if (testCase.getIssueLinks() != null) {
      for (String issueLink : testCase.getIssueLinks()) {
        item.addItemMetaData(new CEtlItemMetaData("IssueLink", issueLink));
      }
    }

    if (testCase.getCustomFields() != null) {
      testCase.getCustomFields()
          .forEach((k, v) -> item.addItemMetaData(new CEtlItemMetaData(k, v)));
    }
  }

  private static CEtlStatus getStatus(String statusName) {
    return CStringUtil.isBlank(statusName) ? CEtlStatus.UNSET : new CEtlStatus(statusName);
  }

  private static CEtlPriority getPriority(String priorityName) {
    return CStringUtil.isBlank(priorityName) ? CEtlPriority.UNSET : new CEtlPriority(priorityName);
  }

  private static CEtlVersions getIssueVersions(CEtlVersions versions, CZScaleTestCase testCase) {
    if (testCase == null || testCase.getCustomFields() == null || testCase.getCustomFields().isEmpty()) {
      return new CEtlVersions();
    }

    CList<String> potentialVersions = testCase
        .getCustomFields()
        .getAll((k, v) -> k.toLowerCase().contains("version")).values();

    return new CEtlVersions(versions.getAll(v -> potentialVersions.contains(v.getName())));
  }
}
