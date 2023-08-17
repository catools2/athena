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
import org.catools.tms.etl.cache.CEtlCacheManager;
import org.catools.tms.etl.model.*;

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

      return new CEtlCycle(
          testRun.getKey(),
          folder + testRun.getName(),
          version,
          testRun.getPlannedEndDate(),
          testRun.getPlannedStartDate());
    } catch (Exception e) {
      log.error("Failed to translate test run {} to cycle.", testRun, e);
      throw e;
    }
  }

  public static CEtlExecution translateExecution(CZScaleTestRun testRun, CEtlCycle cycle, CZScaleTestExecution execution) {
    Objects.requireNonNull(execution);
    CEtlItem item;
    try {
      try {
        item = CEtlCacheManager.readItem(execution.getTestCaseKey());
      } catch (InvalidParameterException e) {
        CZScaleTestCase testcase = CZScaleClient.TestCases.getTestCase(execution.getTestCaseKey());
        if (testcase == null) {
          log.error("Failed to read testcase {} after 5 retry", execution.getTestCaseKey());
          return null;
        }
        item = CEtlZScaleSyncHelper.addItem(testcase);
      }

      CEtlExecutionStatus status = getStatus(execution.getStatus());
      CEtlUser executor = getExecutor(execution);

      return new CEtlExecution(
          String.valueOf(execution.getId()),
          item,
          cycle,
          // We do not createdData for execution, so we replace it with CreatedOn from test run
          testRun.getCreatedOn(),
          execution.getExecutionDate(),
          executor,
          status);
    } catch (Exception e) {
      log.error("Failed to translate execution {}.", execution, e);
      throw e;
    }
  }

  private static CEtlUser getExecutor(CZScaleTestExecution execution) {
    return CStringUtil.isBlank(execution.getExecutedBy()) ? CEtlUser.UNSET : new CEtlUser(execution.getExecutedBy());
  }

  private static CEtlExecutionStatus getStatus(CZScaleExecutionStatus status) {
    return status == null || CStringUtil.isBlank(status.getScaleName()) ?
        CEtlExecutionStatus.UNSET :
        new CEtlExecutionStatus(status.getScaleName());
  }
}
