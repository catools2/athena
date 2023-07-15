package org.catools.atlassian.etl.scale;

import com.atlassian.jira.rest.client.api.domain.BasicProject;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.scale.configs.CEtlZScaleConfigs;
import org.catools.atlassian.etl.scale.translators.CEtlZScaleTestCaseTranslator;
import org.catools.atlassian.scale.CZScaleClient;
import org.catools.atlassian.scale.model.CZScaleTestCase;
import org.catools.atlassian.scale.model.CZScaleTestExecution;
import org.catools.atlassian.scale.model.CZScaleTestRun;
import org.catools.atlassian.scale.model.CZScaleTestRuns;
import org.catools.common.collections.CSet;
import org.catools.common.concurrent.CParallelRunner;
import org.catools.common.date.CDate;
import org.catools.common.utils.CStringUtil;
import org.catools.tms.etl.dao.CEtlExecutionDao;
import org.catools.tms.etl.dao.CEtlItemDao;
import org.catools.tms.etl.dao.CEtlLastSyncDao;
import org.catools.tms.etl.model.*;

import java.util.Date;
import java.util.Stack;

import static org.catools.atlassian.etl.scale.helpers.CEtlZScaleSyncHelper.getProjectByName;
import static org.catools.atlassian.etl.scale.helpers.CEtlZScaleSyncHelper.getProjectVersions;
import static org.catools.atlassian.etl.scale.translators.CEtlZScaleTestRunTranslator.translateExecution;
import static org.catools.atlassian.etl.scale.translators.CEtlZScaleTestRunTranslator.translateTestRun;

@Slf4j
public class CEtlZScaleSyncClient {

  public static void syncScale(String projectNameToSync, int parallelInputCount, int parallelOutputCount) throws Throwable {
    BasicProject project = getProjectByName(projectNameToSync);
    CEtlProject etlProject = new CEtlProject(project.getName());

    CEtlVersions versions = new CEtlVersions(getProjectVersions(project.getKey(), etlProject));

    updateTestCycles(project.getKey(), etlProject, versions, parallelInputCount, parallelOutputCount);
    updateTestRuns(project.getKey(), versions, parallelOutputCount);
  }

  private static void updateTestRuns(String projectKey, CEtlVersions versions, int parallelOutputCount) throws Throwable {
    Date projectSyncStartTime = CDate.now();
    // Skip specific run which has not changed after its last sync
    // we need to double filter in case if major project sync interrupted due to any reason
    // So we can avoid wasting time on re-sync project which has been already synced
    for (String activeFolder : CEtlZScaleConfigs.Scale.getSyncTestCycleFolders()) {
      CZScaleTestRuns testRunsToSync = getTestRunsToSync(projectKey, activeFolder);
      for (CZScaleTestRun scaleTestRun : testRunsToSync) {
        String testRunInfoKey = scaleTestRun.getKey();
        String runDbSyncKey = "SCALE_RUN_" + testRunInfoKey.toUpperCase();
        Date runLastSync = CEtlLastSyncDao.getProjectLastSync(runDbSyncKey, projectKey);

        log.info("Start sync {} run.", testRunInfoKey);
        CZScaleTestRun testRun = CZScaleClient.TestRuns.getTestRun(testRunInfoKey);

        if (testRun.getItems().isNotEmpty()) {
          CEtlVersion version = getVersionForTestRun(versions, testRun);
          updateTestRunExecutions(version, testRunInfoKey, runLastSync, testRun, parallelOutputCount);
        }

        CEtlLastSyncDao.updateProjectLastSync(runDbSyncKey, projectKey, projectSyncStartTime);
        log.info("Finish sync {} run.", testRunInfoKey);
      }
    }
  }

  private static CEtlVersion getVersionForTestRun(CEtlVersions versions, CZScaleTestRun testRun) {
    if (testRun.getVersion() == null) {
      return null;
    }
    return versions.getFirst(v -> CStringUtil.equalsAnyIgnoreCase(v.getName(), testRun.getVersion()));
  }

  private static void updateTestRunExecutions(CEtlVersion version, String testRunInfoKey, Date runLastSync, CZScaleTestRun testRun, int parallelOutputCount) throws Throwable {
    log.info("Start updating {} run execution with {} items.", testRun.getKey(), testRun.getItems().size());
    CEtlCycle cycle = translateTestRun(version, testRun);
    Stack<CZScaleTestExecution> executionsToSync = new Stack<>();
    executionsToSync.addAll(testRun.getItems().getAll(item -> itemShouldSync(runLastSync, item)));

    log.info("{} items need to be updated for {} run.", executionsToSync.size(), testRun.getKey());
    new CParallelRunner<>(String.format("Update %s Test Run Executions", testRun.getKey()), parallelOutputCount, () -> {
      while (true) {
        CZScaleTestExecution testExecution;
        synchronized (executionsToSync) {
          if (executionsToSync.isEmpty()) break;
          testExecution = executionsToSync.pop();
        }
        CEtlExecution execution = translateExecution(testRun, cycle, testExecution);
        if (execution != null)
          CEtlExecutionDao.mergeExecution(execution);
      }
      return true;
    }).invokeAll();

    CSet<String> issueKeysFromScale = testRun.getItems().mapToSet(CZScaleTestExecution::getTestCaseKey);
    CSet<String> issueKeysFromDB = CEtlExecutionDao.getExecutionsByCycleId(testRunInfoKey);
    CSet<String> idsToDeleteFromCycle = issueKeysFromDB.getAll(issueKeysFromScale::notContains).toSet();

    if (idsToDeleteFromCycle.isNotEmpty()) {
      CEtlExecutionDao.deleteExecutions(testRunInfoKey, idsToDeleteFromCycle);
    }

    log.info("Finish updating {} run execution with {} items.", testRun.getKey(), testRun.getItems().size());
  }

  private static void updateTestCycles(String projectKey, CEtlProject project, CEtlVersions versions, int parallelInputCount, int parallelOutputCount) {
    Date projectSyncStartTime = CDate.now();
    Date projectLastSync = CEtlLastSyncDao.getProjectLastSync("SCALE_TEST_CYCLES", projectKey);
    for (String activeFolder : CEtlZScaleConfigs.Scale.getSyncTestCasesFolders()) {
      CZScaleClient.TestCases.getProjectTestCases(projectKey, activeFolder, "createdOn,updatedOn,key", parallelInputCount, parallelOutputCount, testCase -> {
        if (testCase != null && !testCaseIsSynced(projectLastSync, testCase)) {
          CZScaleTestCase testCaseItem = CZScaleClient.TestCases.getTestCase(testCase.getKey());
          if (testCaseItem != null)
            CEtlItemDao.mergeItem(CEtlZScaleTestCaseTranslator.translateTestCase(project, versions, testCaseItem));
        }
      });
    }

    CEtlLastSyncDao.updateProjectLastSync("SCALE_TEST_CYCLES", projectKey, projectSyncStartTime);
  }

  private static CZScaleTestRuns getTestRunsToSync(String projectKey, String activeFolder) {
    return CZScaleClient.TestRuns.getAllTestRuns(projectKey, activeFolder, "createdOn,updatedOn,key");
  }

  private static boolean itemShouldSync(Date runLastSync, CZScaleTestExecution item) {
    return item.getExecutionDate() == null || runLastSync == null || item.getExecutionDate().after(runLastSync);
  }

  private static boolean testCaseIsSynced(Date projectLastSync, CZScaleTestCase testcase) {
    if (projectLastSync == null) return false;
    return testcase.getUpdatedOn() != null ? testcase.getUpdatedOn().before(projectLastSync) : testcase.getCreatedOn().before(projectLastSync);
  }
}
