package org.catools.athena.atlassian.etl.scale.translators;

import feign.FeignException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestCase;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestExecution;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestRun;
import org.catools.athena.atlassian.etl.scale.rest.testcase.TestCaseClient;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.model.tms.StatusDto;
import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.rest.feign.common.utils.EtlUtils;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.tms.cache.TmsCache;
import org.catools.athena.rest.feign.tms.clients.TmsClient;

import java.util.Objects;

import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getStatus;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getUser;
import static org.catools.athena.rest.feign.tms.helpers.EtlHelper.getVersion;

@Slf4j
@UtilityClass
public class ScaleTestRunTranslator {
  public static TestCycleDto translateTestRun(final String projectKey, ScaleTestRun testRun) {
    log.info("Start translating test run {} with {} execution items.", testRun.getKey(), testRun.getItems().size());

    Objects.requireNonNull(testRun);

    String folder = "";

    if (StringUtils.isNotBlank(testRun.getFolder())) {
      folder = testRun.getFolder().trim();
      if (!folder.endsWith("/")) {
        folder += "/";
      }
    }

    TestCycleDto etlCycle = new TestCycleDto();
    etlCycle.setCode(testRun.getKey());
    etlCycle.setProject(projectKey);
    etlCycle.setVersion(getVersion(testRun.getVersion(), projectKey));
    etlCycle.setName(folder + testRun.getName());
    etlCycle.setEndDate(testRun.getPlannedEndDate() == null ? null : testRun.getPlannedEndDate().toInstant());
    etlCycle.setStartDate(testRun.getPlannedStartDate() == null ? null : testRun.getPlannedStartDate().toInstant());

    for (ScaleTestExecution item : testRun.getItems()) {
      addTestCaseIfNotExists(projectKey, testRun, item);
      etlCycle.getTestExecutions().add(translateExecution(testRun, item));
    }

    log.debug("Finish translating test run {} with {} execution items.", testRun.getKey(), testRun.getItems().size());

    return etlCycle;
  }

  private static void addTestCaseIfNotExists(String projectKey, ScaleTestRun testRun, ScaleTestExecution item) {
    if (TmsClient.searchItem(item.getTestCaseKey()).isEmpty()) {
      // if the test does not exist then try to read it and add it but
      // if it does not exist in scale, then, it means that it archive.
      // In that case, we just add a placeholder to keep statistics consistent
      try {
        ScaleTestCase testCaseItem = TestCaseClient.getTestCase(item.getTestCaseKey());
        TmsClient.saveItem(ScaleTestCaseTranslator.translateTestCase(CoreConfigs.getProjectCode(), testCaseItem));
      } catch (FeignException.NotFound t) {
        createPlaceHolderForArchivedItem(projectKey, testRun, item);
      }
    }
  }

  private static void createPlaceHolderForArchivedItem(String projectKey, ScaleTestRun testRun, ScaleTestExecution item) {
    ItemTypeDto itemType = TmsCache.readType(new ItemTypeDto(EtlUtils.generateCode("Test"), "Test"));
    StatusDto status = TmsCache.readStatus(new StatusDto(EtlUtils.generateCode("Archived"), "Archived"));
    PriorityDto priority = TmsCache.readPriority(new PriorityDto(EtlUtils.generateCode("Normal"), "Normal"));
    UserDto user = CoreCache.readUser(new UserDto("robot"));
    ItemDto itemDto = new ItemDto().setType(itemType.getCode())
        .setCreatedBy(user.getUsername())
        .setCreatedOn(testRun.getCreatedOn().toInstant())
        .setCode(item.getTestCaseKey())
        .setProject(projectKey)
        .setStatus(status.getCode())
        .setPriority(priority.getCode())
        .setName("Place holder for archived test case " + item.getTestCaseKey());
    TmsClient.saveItem(itemDto);
  }

  private static TestExecutionDto translateExecution(ScaleTestRun testRun, ScaleTestExecution execution) {
    Objects.requireNonNull(execution);

    TestExecutionDto etlExecution = new TestExecutionDto();

    etlExecution.setItem(execution.getTestCaseKey());
    etlExecution.setStatus(getStatus(execution.getStatus() == null ? "CREATED" : execution.getStatus().name()));
    etlExecution.setExecutor(getUser(execution.getExecutedBy()));
    etlExecution.setCreatedOn(testRun.getCreatedOn() == null ? null : testRun.getCreatedOn().toInstant());
    etlExecution.setExecutedOn(execution.getExecutionDate() == null ? null : execution.getExecutionDate().toInstant());

    return etlExecution;
  }

}
