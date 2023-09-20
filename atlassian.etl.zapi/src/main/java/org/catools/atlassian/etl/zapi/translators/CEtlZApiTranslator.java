package org.catools.atlassian.etl.zapi.translators;

import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.zapi.model.*;
import org.catools.common.utils.CStringUtil;
import org.catools.etl.tms.cache.CEtlCacheManager;
import org.catools.etl.tms.dao.CEtlExecutionDao;
import org.catools.etl.tms.model.*;

import java.util.Objects;

@Slf4j
public class CEtlZApiTranslator {
  public static CEtlCycle translateCycle(CZApiProject zProject, CZApiVersion zVersion, CZApiCycle cycle) {
    Objects.requireNonNull(zProject);
    Objects.requireNonNull(zVersion);
    Objects.requireNonNull(cycle);

    CEtlCycle etlCycle = CEtlExecutionDao.find(CEtlCycle.class, cycle.getId().toString());
    if (etlCycle == null) {
      etlCycle = new CEtlCycle();
      etlCycle.setId(cycle.getId().toString());
    }

    try {
      CEtlProject project = CEtlCacheManager.readProject(new CEtlProject(zProject.getName()));
      CEtlVersion version = CEtlCacheManager.readVersion(new CEtlVersion(zVersion.getName(), project));

      etlCycle.setVersion(version);
      etlCycle.setName(cycle.getName());
      etlCycle.setEndDate(cycle.getEndDate());
      etlCycle.setStartDate(cycle.getStartDate());

      return etlCycle;
    } catch (Throwable t) {
      log.error("Failed to translate cycle.", t);
      throw t;
    }
  }

  public static CEtlExecution translateExecution(CZApiProject project,
                                                 CZApiVersion version,
                                                 CZApiCycles cycles,
                                                 CZApiExecution execution) {
    Objects.requireNonNull(project);
    Objects.requireNonNull(version);
    Objects.requireNonNull(cycles);
    Objects.requireNonNull(execution);

    CEtlExecution etlExecution = CEtlExecutionDao.find(CEtlExecution.class, String.valueOf(execution.getId()));
    if (etlExecution == null) {
      etlExecution = new CEtlExecution();
      etlExecution.setId(String.valueOf(execution.getId()));
    }

    try {
      etlExecution.setItem(CEtlCacheManager.readItem(execution.getIssueKey()));
      etlExecution.setStatus(getStatus(execution.getExecutionStatus()));
      etlExecution.setExecutor(getExecutor(execution));
      etlExecution.setCycle(translateCycle(project, version, cycles.getById(execution.getCycleId())));
      etlExecution.setCreated(execution.getCreatedOn());
      etlExecution.setExecuted(execution.getExecutedOn());

      return etlExecution;
    } catch (Throwable t) {
      log.error("Failed to translate execution.", t);
      throw t;
    }
  }

  private static CEtlUser getExecutor(CZApiExecution execution) {
    return CStringUtil.isBlank(execution.getExecutedByUserName()) ?
        CEtlUser.UNSET :
        CEtlCacheManager.readUser(new CEtlUser(execution.getExecutedByUserName()));
  }

  private static CEtlExecutionStatus getStatus(String statusName) {
    return CStringUtil.isBlank(statusName) ?
        CEtlExecutionStatus.UNSET :
        CEtlCacheManager.readExecutionStatus(new CEtlExecutionStatus(statusName.toUpperCase()));
  }
}
