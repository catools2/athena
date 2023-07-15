package org.catools.atlassian.etl.zapi;

import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.zapi.translators.CEtlZApiTranslator;
import org.catools.atlassian.zapi.CZApiClient;
import org.catools.atlassian.zapi.model.*;
import org.catools.common.collections.CSet;
import org.catools.common.date.CDate;
import org.catools.common.utils.CStringUtil;
import org.catools.tms.etl.dao.CEtlExecutionDao;
import org.catools.tms.etl.dao.CEtlLastSyncDao;
import org.catools.tms.etl.model.CEtlExecution;

import java.util.Date;

@Slf4j
public class CEtlZApiSyncClient {
  private static final String ZAPI = "ZAPI";
  private static final String UNSCHEDULED = "Unscheduled";

  public static void syncZephyr(CSet<String> projectNamesToSync, int parallelInputCount, int parallelOutputCount) {
    CZApiProjects projects = CZApiClient.Project.getProjects();

    for (CZApiProject project : projects.getAll(p -> projectNamesToSync.contains(p.getName()))) {
      CZApiVersions projectVersions = CZApiClient.Version.getProjectVersions(project).getAllVersions();

      // remove Unscheduled cycles
      projectVersions.removeIf(v -> CStringUtil.containsIgnoreCase(UNSCHEDULED, v.getName()));

      // If there were full sync then we read diff and update versions to be update to read only
      // changed ones.
      // We have to read information again based on changed versions as there is no indicator In Erp
      // level for Cycle
      // to ensure that we are working with. We can add additional fields like internal id or
      // something like that but
      // depend on Test Data Management this option will be very so for now we just read records
      // again.
      // Assuming tht Erp usually run at least once a day, the number of diff should not be that
      // much to be worry about.
      // In future having more TDM we can make a better decision.
      Date projectSyncStartTime = CDate.now();
      Date projectLastSync = CEtlLastSyncDao.getProjectLastSync(ZAPI, project.getName());

      if (projectLastSync != null) {
        CZApiExecutions executions =
            CZApiClient.Search.getExecutions(
                String.format("project='%s'", project.getName()),
                projectLastSync,
                parallelInputCount,
                parallelOutputCount,
                null);
        CSet<String> versionsToUpdate = executions.mapToSet(CZApiExecution::getVersionName);
        projectVersions.removeIf(v -> !versionsToUpdate.contains(v.getName()));
      }

      for (CZApiVersion version : projectVersions) {
        CZApiCycles cycles =
            CZApiClient.Cycle.getAllCycle(
                new CZApiProject().setId(project.getId()).setName(project.getName()),
                new CZApiVersion().setId(version.getId()).setName(version.getName()));

        if (cycles != null && cycles.isNotEmpty()) {
          addExecutions(
              project,
              version,
              cycles,
              parallelInputCount,
              parallelOutputCount);
        }
      }
      CEtlLastSyncDao.updateProjectLastSync(ZAPI, project.getName(), projectSyncStartTime);
    }
  }

  private static void addExecutions(
      CZApiProject project,
      CZApiVersion version,
      CZApiCycles cycles,
      int parallelInputCount,
      int parallelOutputCount) {
    Date lastSync = CEtlLastSyncDao.getExecutionLastSync(ZAPI, project.getName(), version.getName());
    String zql = String.format("project=\"%s\" AND fixVersion = \"%s\"", project.getName(), version.getName());

    Date syncStartTime = CDate.now();
    CZApiClient.Search.getExecutions(
        zql,
        lastSync,
        parallelInputCount,
        parallelOutputCount,
        zApiExecutions -> {
          if (zApiExecutions != null && zApiExecutions.isNotEmpty()) {
            // I surprised when I notice that in particular moment, search can return items which
            // are not related to
            // project or version we search for, so we need to clean them up before continuing.
            // I have no clue why and could not found any information online about this.
            zApiExecutions.removeIf(e -> !project.getName().equalsIgnoreCase(e.getProjectName())
                || !version.getName().equalsIgnoreCase(e.getVersionName()));

            if (zApiExecutions.isNotEmpty()) {
              for (CZApiExecution execution : zApiExecutions) {
                CEtlExecution translatedExecution = CEtlZApiTranslator.translateExecution(project, version, cycles, execution);
                CEtlExecutionDao.mergeExecution(translatedExecution);
              }
            }
          }
        });

    CEtlLastSyncDao.updateExecutionLastSync(ZAPI, project.getName(), version.getName(), syncStartTime);
  }
}
