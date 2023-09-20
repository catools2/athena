package org.catools.atlassian.etl.scale.translators;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.scale.helpers.CEtlZScaleSyncHelper;
import org.catools.atlassian.scale.CZScaleClient;
import org.catools.atlassian.scale.model.CZScaleTestCase;
import org.catools.atlassian.scale.model.CZScaleTestExecution;
import org.catools.atlassian.scale.model.CZScaleTestRun;
import org.catools.atlassian.scale.rest.cycle.CZScaleExecutionStatus;
import org.catools.common.utils.CStringUtil;
import org.catools.etl.tms.cache.CEtlCacheManager;
import org.catools.etl.tms.dao.CEtlExecutionDao;
import org.catools.etl.tms.model.*;

import java.security.InvalidParameterException;
import java.util.Objects;

@Slf4j
@UtilityClass
public class CEtlZScaleTestRunTranslator {
  public static CEtlCycle translateTestRun(CEtlVersion version, CZScaleTestRun testRun) {
    Objects.requireNonNull(testRun);

    try {
      String folder = "";

      if (CStringUtil.isNotBlank(testRun.getFolder())) {
        folder = testRun.getFolder().trim();
        if (!folder.endsWith("/")) {
          folder += "/";
        }
      }

      CEtlCycle etlCycle = CEtlExecutionDao.find(CEtlCycle.class, testRun.getKey());
      if (etlCycle == null) {
        etlCycle = new CEtlCycle();
        etlCycle.setId(testRun.getKey());
      }

      etlCycle.setVersion(version);
      etlCycle.setName(folder + testRun.getName());
      etlCycle.setEndDate(testRun.getPlannedEndDate());
      etlCycle.setStartDate(testRun.getPlannedStartDate());

      return etlCycle;
    } catch (Exception e) {
      log.error("Failed to translate test run {} to cycle.", testRun, e);
      throw e;
    }
  }

  public static CEtlExecution translateExecution(CZScaleTestRun testRun, CEtlCycle cycle, CZScaleTestExecution execution) {
    Objects.requireNonNull(execution);

    CEtlExecution etlExecution = CEtlExecutionDao.find(CEtlExecution.class, String.valueOf(execution.getId()));
    if (etlExecution == null) {
      etlExecution = new CEtlExecution();
      etlExecution.setId(String.valueOf(execution.getId()));
    }

    try {
      try {
        etlExecution.setItem(CEtlCacheManager.readItem(execution.getTestCaseKey()));
      } catch (InvalidParameterException e) {
        CZScaleTestCase testcase = CZScaleClient.TestCases.getTestCase(execution.getTestCaseKey());
        if (testcase == null) {
          log.error("Failed to read testcase {} after 5 retry", execution.getTestCaseKey());
          return null;
        }
        etlExecution.setItem(CEtlZScaleSyncHelper.addItem(testcase));
      }

      etlExecution.setStatus(getStatus(execution.getStatus()));
      etlExecution.setExecutor(getExecutor(execution));
      etlExecution.setCycle(cycle);
      etlExecution.setCreated(testRun.getCreatedOn());
      etlExecution.setExecuted(execution.getExecutionDate());

      return etlExecution;
    } catch (Exception e) {
      log.error("Failed to translate execution {}.", execution, e);
      throw e;
    }
  }

  private static CEtlUser getExecutor(CZScaleTestExecution execution) {
    return CStringUtil.isBlank(execution.getExecutedBy()) ?
        CEtlUser.UNSET :
        CEtlCacheManager.readUser(new CEtlUser(execution.getExecutedBy()));
  }

  private static CEtlExecutionStatus getStatus(CZScaleExecutionStatus status) {
    return status == null || CStringUtil.isBlank(status.getScaleName()) ?
        CEtlExecutionStatus.UNSET :
        CEtlCacheManager.readExecutionStatus(new CEtlExecutionStatus(status.getScaleName()));
  }
}
