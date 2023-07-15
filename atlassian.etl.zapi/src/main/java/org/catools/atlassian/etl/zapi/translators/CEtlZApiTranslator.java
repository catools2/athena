package org.catools.atlassian.etl.zapi.translators;

import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.zapi.model.*;
import org.catools.common.utils.CStringUtil;
import org.catools.tms.etl.cache.CEtlCacheManager;
import org.catools.tms.etl.model.*;

import java.util.Objects;

@Slf4j
public class CEtlZApiTranslator {
  public static CEtlCycle translateCycle(CZApiProject zProject, CZApiVersion zVersion, CZApiCycle cycle) {
    Objects.requireNonNull(zProject);
    Objects.requireNonNull(zVersion);
    Objects.requireNonNull(cycle);

    try {
      CEtlProject project = new CEtlProject(zProject.getName());
      CEtlVersion version = new CEtlVersion(zVersion.getName(), project);
      return new CEtlCycle(
          cycle.getId().toString(),
          cycle.getName(),
          version,
          cycle.getEndDate(),
          cycle.getStartDate()
      );
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

    try {
      CEtlItem item = CEtlCacheManager.readItem(execution.getIssueKey());
      CEtlUser executor = getExecutor(execution);
      CEtlExecutionStatus status = getStatus(execution.getExecutionStatus());
      CEtlCycle cycle = translateCycle(project, version, cycles.getById(execution.getCycleId()));

      return new CEtlExecution(
          String.valueOf(execution.getId()),
          item,
          cycle,
          execution.getCreatedOn(),
          execution.getExecutedOn(),
          executor,
          status);
    } catch (Throwable t) {
      log.error("Failed to translate execution.", t);
      throw t;
    }
  }

  private static CEtlUser getExecutor(CZApiExecution execution) {
    return CStringUtil.isBlank(execution.getExecutedByUserName()) ?
        CEtlUser.UNSET :
        new CEtlUser(execution.getExecutedByUserName());
  }

  private static CEtlExecutionStatus getStatus(String statusName) {
    return CStringUtil.isBlank(statusName) ?
        CEtlExecutionStatus.UNSET :
        new CEtlExecutionStatus(statusName);
  }
}
