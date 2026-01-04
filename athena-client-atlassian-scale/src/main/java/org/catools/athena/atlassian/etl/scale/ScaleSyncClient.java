package org.catools.athena.atlassian.etl.scale;

import com.google.common.hash.Hashing;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestCase;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestExecution;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestRun;
import org.catools.athena.atlassian.etl.scale.rest.cycle.TestRunClient;
import org.catools.athena.atlassian.etl.scale.rest.testcase.TestCaseClient;
import org.catools.athena.atlassian.etl.scale.translators.ScaleTestCaseTranslator;
import org.catools.athena.atlassian.etl.scale.translators.ScaleTestRunTranslator;
import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.rest.feign.common.utils.JsonUtils;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.tms.clients.TmsClient;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@Slf4j
@UtilityClass
public class ScaleSyncClient {

  private final String SYNC_SCALE_RUN = "SYNC_SCALE_RUN";
  private final String SYNC_SCALE_CASE = "SYNC_SCALE_CASE";

  public void syncTestCases() {
    final String projectCode = CoreConfigs.getProjectCode();
    CoreCache.readProject(CoreConfigs.getProject());

    Instant projectSyncStartTime = Instant.now();

    for (final String folderToSync : ScaleConfigs.getTestCasesFoldersToSync()) {
      String syncComponent = "FOLDER::>" + StringUtils.defaultIfBlank(folderToSync, "ALL");
      Date projectLastSync = TmsClient.getLastSyncInfo(projectCode, SYNC_SCALE_CASE, syncComponent);

      TestCaseClient.processProjectTestCases(CoreConfigs.getThreadsCount(),
          CoreConfigs.getTimeoutInMinutes(),
          folderToSync,
          "createdOn,updatedOn,key",
          testCase -> {
            if (testCase != null && !testCaseIsSynced(projectLastSync, testCase)) {
              log.info("Processing test case {}.", testCase.getKey());
              ScaleTestCase testCaseItem = TestCaseClient.getTestCase(testCase.getKey());
              TmsClient.saveItem(ScaleTestCaseTranslator.translateTestCase(projectCode, testCaseItem));
            }
          });

      TmsClient.saveSyncInfo(projectCode, SYNC_SCALE_CASE, syncComponent, projectSyncStartTime);
    }
  }

  public void syncTestRuns() {
    final String projectCode = CoreConfigs.getProjectCode();
    CoreCache.readProject(CoreConfigs.getProject());

    Instant projectSyncStartTime = Instant.now();
    for (String folder : ScaleConfigs.getTestRunFoldersToSync()) {
      log.info("Start sync test run in {} folder.", folder);
      TestRunClient.processTestRuns(CoreConfigs.getThreadsCount(), CoreConfigs.getTimeoutInMinutes(), "", folder, scaleTestRun -> {
        String testRunInfoKey = scaleTestRun.getKey();
        log.info("Start sync {} run.", testRunInfoKey);
        String runDbSyncKey = "SCALE_RUN_" + testRunInfoKey.toUpperCase();

        ScaleTestRun testRun = TestRunClient.getTestRun(testRunInfoKey);
        syncTestRunExecutions(projectCode, testRun);

        TmsClient.saveSyncInfo(projectCode, SYNC_SCALE_RUN, runDbSyncKey, projectSyncStartTime);
        log.info("Finish sync {} run.", testRunInfoKey);
      });
      log.info("Finish sync test run in {} folder.", folder);
    }
  }

  private static String generateSHA256(ScaleTestRun scaleTestRun) {
    StringBuilder builder = new StringBuilder();
    builder.append(scaleTestRun.getKey())
        .append(scaleTestRun.getName())
        .append(scaleTestRun.getIssueKey())
        .append(scaleTestRun.getDescription())
        .append(scaleTestRun.getVersion())
        .append(scaleTestRun.getProjectKey())
        .append(scaleTestRun.getOwner())
        .append(scaleTestRun.getUpdatedBy())
        .append(scaleTestRun.getUpdatedOn())
        .append(scaleTestRun.getCreatedOn())
        .append(scaleTestRun.getCreatedBy())
        .append(scaleTestRun.getFolder())
        .append(scaleTestRun.getStatus())
        .append(scaleTestRun.getPlannedEndDate())
        .append(scaleTestRun.getPlannedStartDate())
        .append(scaleTestRun.getIssueCount())
        .append(scaleTestRun.getExecutionTime())
        .append(scaleTestRun.getEstimatedTime())
        .append(scaleTestRun.getTestCaseCount())
        .append(scaleTestRun.getExecutionSummary());

    if (scaleTestRun.getItems() != null) {
      ScaleTestExecution[] itemsArray = scaleTestRun.getItems().toArray(new ScaleTestExecution[]{});
      Arrays.sort(itemsArray, Comparator.comparing(ScaleTestExecution::getId));
      for (ScaleTestExecution scaleTestExecution : itemsArray) {
        builder.append(JsonUtils.writeValueAsString(scaleTestExecution));
      }
    }

    return Hashing.sha256().hashString(builder.toString(), StandardCharsets.UTF_8).toString();
  }

  private boolean testCaseIsSynced(Date projectLastSync, ScaleTestCase testcase) {
    if (projectLastSync == null) {
      return false;
    }
    return testcase.getUpdatedOn() != null ? testcase.getUpdatedOn().before(projectLastSync) : testcase.getCreatedOn().before(projectLastSync);
  }

  private void syncTestRunExecutions(final String projectKey, ScaleTestRun testRun) {
    log.debug("Start updating test run {} with {} execution items.", testRun.getKey(), testRun.getItems().size());
    TestCycleDto cycle = ScaleTestRunTranslator.translateTestRun(projectKey, testRun);
    TmsClient.saveTestCycle(cycle);
    log.debug("Finish updating test run {} with {} execution items.", testRun.getKey(), testRun.getItems().size());
  }
}
