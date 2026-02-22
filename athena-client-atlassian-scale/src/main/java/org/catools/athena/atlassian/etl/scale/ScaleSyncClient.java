package org.catools.athena.atlassian.etl.scale;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestCase;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestRun;
import org.catools.athena.atlassian.etl.scale.rest.cycle.TestRunClient;
import org.catools.athena.atlassian.etl.scale.rest.testcase.TestCaseClient;
import org.catools.athena.atlassian.etl.scale.translators.ScaleTestCaseTranslator;
import org.catools.athena.atlassian.etl.scale.translators.ScaleTestRunTranslator;
import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.tms.clients.TmsClient;

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

      TestCaseClient.processProjectTestCases(
          CoreConfigs.getThreadsCount(),
          CoreConfigs.getTimeoutInMinutes(),
          folderToSync,
          "createdOn,updatedOn,key",
          testCase -> {
            if (testCase != null && !testCaseIsSynced(projectLastSync, testCase)) {
              log.debug("Processing test case {}.", testCase.getKey());
              ScaleTestCase testCaseItem = TestCaseClient.getTestCase(testCase.getKey());
              TmsClient.saveItem(
                  ScaleTestCaseTranslator.translateTestCase(projectCode, testCaseItem));
            }
          });

      TmsClient.saveSyncInfo(projectCode, SYNC_SCALE_CASE, syncComponent, projectSyncStartTime);
    }
  }

  public void syncTestRuns() {
    syncTestRuns(ScaleConfigs.getTestRuns(), ScaleConfigs.getTestRunFoldersToSync());
  }

  public void syncTestRuns(final List<String> testRuns, final List<String> testRunFoldersToSync) {

    final String projectCode = CoreConfigs.getProjectCode();
    final int threadsCount = CoreConfigs.getThreadsCount();
    final long timeoutInMinutes = CoreConfigs.getTimeoutInMinutes();

    CoreCache.readProject(CoreConfigs.getProject());

    Instant projectSyncStartTime = Instant.now();
    for (String testRun : new HashSet<>(testRuns)) {
      processTestRun(threadsCount, timeoutInMinutes, testRun, projectCode, projectSyncStartTime);
    }

    for (String folder : new HashSet<>(testRunFoldersToSync)) {
      log.info("Start sync test run in {} folder.", folder);
      TestRunClient.processTestRuns(
          threadsCount,
          timeoutInMinutes,
          "",
          folder,
          scaleTestRun -> {
            String testRunInfoKey = scaleTestRun.getKey();
            processTestRun(
                threadsCount, timeoutInMinutes, testRunInfoKey, projectCode, projectSyncStartTime);
          });
      log.info("Finish sync test run in {} folder.", folder);
    }
  }

  private static void processTestRun(
      final int threadsCount,
      final long timeoutInMinutes,
      String testRunInfoKey,
      String projectCode,
      Instant projectSyncStartTime) {
    log.info("Start sync {} run.", testRunInfoKey);
    String runDbSyncKey = "SCALE_RUN_" + testRunInfoKey.toUpperCase();

    log.debug("Start reading {} run from scale.", testRunInfoKey);
    ScaleTestRun testRun = TestRunClient.getTestRun(testRunInfoKey);
    log.debug("Finished reading {} run from scale.", testRunInfoKey);
    syncTestRunExecutions(threadsCount, timeoutInMinutes, projectCode, testRun);

    TmsClient.saveSyncInfo(projectCode, SYNC_SCALE_RUN, runDbSyncKey, projectSyncStartTime);
    log.info("Finish sync {} run.", testRunInfoKey);
  }

  private boolean testCaseIsSynced(Date projectLastSync, ScaleTestCase testcase) {
    if (projectLastSync == null) {
      return false;
    }
    return testcase.getUpdatedOn() != null
        ? testcase.getUpdatedOn().before(projectLastSync)
        : testcase.getCreatedOn().before(projectLastSync);
  }

  private void syncTestRunExecutions(
      final int threadsCount,
      final long timeoutInMinutes,
      final String projectKey,
      final ScaleTestRun testRun) {
    log.debug(
        "Start updating test run {} with {} execution items.",
        testRun.getKey(),
        testRun.getItems().size());
    TestCycleDto cycle =
        ScaleTestRunTranslator.translateTestRun(
            threadsCount, timeoutInMinutes, projectKey, testRun);
    TmsClient.saveTestCycle(cycle);
    log.debug(
        "Finish updating test run {} with {} execution items.",
        testRun.getKey(),
        testRun.getItems().size());
  }
}
