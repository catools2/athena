package org.catools.athena.atlassian.etl.scale.rest.cycle;

import com.jayway.jsonpath.JsonPath;
import feign.Response;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.client.ScaleAtmClient;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
import org.catools.athena.atlassian.etl.scale.model.ScalePlanTestRun;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestRun;
import org.catools.athena.atlassian.etl.scale.model.ScaleUpdateTestResultRequest;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.catools.athena.atlassian.etl.scale.client.ScaleClient.getScaleAtmClient;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.sleep;

@Slf4j
@UtilityClass
public class TestRunClient {

  private final ScaleAtmClient SCALE_ATM_CLIENT = getScaleAtmClient(ScaleAtmClient.class);

  public void processTestRuns(final int threadsCount,
                              final long timeoutInMinutes,
                              final String fieldsToRead,
                              final String folder,
                              Consumer<ScaleTestRun> onAction) {
    AtomicInteger counter = new AtomicInteger();
    executeInParallel(threadsCount, timeoutInMinutes, () -> {
      while (true) {
        int startFrom = counter.getAndIncrement() * CoreConfigs.getBufferSize() + CoreConfigs.getStartAt();
        log.info("Process test runs from {} to {}", startFrom, startFrom + CoreConfigs.getBufferSize());
        Set<ScaleTestRun> testRuns = _getAllTestRuns(startFrom, fieldsToRead, folder);
        if (testRuns == null || testRuns.isEmpty()) {
          return true;
        }
        testRuns.forEach(onAction);
      }
    });
  }

  public void updateTestResult(String testRunKey, String testCaseKey, ScaleUpdateTestResultRequest testResult) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    SCALE_ATM_CLIENT.updateTestResult(testRunKey, testCaseKey, testResult);
  }

  public String createTestRun(ScalePlanTestRun planTestRun) {
    Response response = SCALE_ATM_CLIENT.saveTestRun(planTestRun);

    try {
      return JsonPath.read(IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8), "$.key");
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse response from test run " + planTestRun, e);
    }
  }

  public ScaleTestRun getTestRun(final String testRunKey) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    return SCALE_ATM_CLIENT.getTestRun(testRunKey);
  }

  private Set<ScaleTestRun> _getAllTestRuns(final int startAt, final String fieldsToRead, final String folder) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    log.debug("All Test Runs, projectKey: {}, fields: {}, startAT: {}, maxResult: {}",
        CoreConfigs.getProjectCode(),
        fieldsToRead,
        startAt,
        CoreConfigs.getBufferSize());

    if (StringUtils.isEmpty(fieldsToRead)) {
      return SCALE_ATM_CLIENT.searchTestRun(startAt,
          CoreConfigs.getBufferSize(),
          String.format("projectKey = \"%s\" AND folder = \"%s\"", CoreConfigs.getProjectCode(), folder));
    }

    return SCALE_ATM_CLIENT.searchTestRun(startAt,
        CoreConfigs.getBufferSize(),
        fieldsToRead,
        String.format("projectKey = \"%s\" AND folder = \"%s\"", CoreConfigs.getProjectCode(), folder));
  }
}
