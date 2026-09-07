package org.catools.athena.atlassian.etl.scale.rest.cycle;

import static org.catools.athena.atlassian.etl.scale.client.ScaleClient.getScaleAtmClient;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.sleep;

import com.jayway.jsonpath.JsonPath;
import feign.FeignException;
import feign.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
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

@Slf4j
@UtilityClass
public class TestRunClient {

  private final ScaleAtmClient SCALE_ATM_CLIENT = getScaleAtmClient(ScaleAtmClient.class);

  public void processTestRuns(
      final int threadsCount,
      final long timeoutInMinutes,
      final String fieldsToRead,
      final String folder,
      Consumer<ScaleTestRun> onAction) {
    final String targetFolder = _normalizeFolder(folder);
    AtomicInteger counter = new AtomicInteger();
    AtomicBoolean aborted = new AtomicBoolean();
    executeInParallel(
        threadsCount,
        timeoutInMinutes,
        () -> {
          while (true) {
            if (aborted.get()) {
              return true;
            }
            int startFrom =
                counter.getAndIncrement() * CoreConfigs.getBufferSize() + CoreConfigs.getStartAt();
            log.info(
                "Process test runs from {} to {}",
                startFrom,
                startFrom + CoreConfigs.getBufferSize());

            Set<ScaleTestRun> testRuns;
            try {
              testRuns = _getAllTestRuns(startFrom, fieldsToRead, targetFolder);
            } catch (FeignException.BadRequest e) {
              // A 400 rejects the query itself (unresolvable folder, malformed predicate), so it
              // will fail identically for every page. Stop the whole fan-out instead of letting
              // each worker walk its remaining offsets into the same error.
              aborted.set(true);
              throw new IllegalArgumentException(
                  String.format(
                      "Scale rejected the test run search for projectKey \"%s\" and folder \"%s\"."
                          + " Verify that the folder exists under Test Runs in this project and is"
                          + " spelled exactly, without a trailing slash.",
                      CoreConfigs.getProjectCode(), targetFolder),
                  e);
            }

            if (testRuns == null || testRuns.isEmpty()) {
              return true;
            }
            testRuns.forEach(onAction);
          }
        });
  }

  /**
   * Scale matches {@code folder} against stored folder paths verbatim, and stored paths carry no
   * trailing slash. Strip one so a value such as {@code /4.16.1/} resolves instead of returning a
   * 400. The root folder {@code /} is passed through untouched.
   */
  private String _normalizeFolder(final String folder) {
    if (StringUtils.isBlank(folder) || "/".equals(folder)) {
      return folder;
    }

    String normalized = StringUtils.stripEnd(folder.trim(), "/");
    if (StringUtils.isEmpty(normalized)) {
      return folder;
    }

    if (!StringUtils.equals(normalized, folder)) {
      log.warn("Normalized test run folder \"{}\" to \"{}\".", folder, normalized);
    }
    return normalized;
  }

  public void updateTestResult(
      String testRunKey, String testCaseKey, ScaleUpdateTestResultRequest testResult) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    SCALE_ATM_CLIENT.updateTestResult(testRunKey, testCaseKey, testResult);
  }

  public String createTestRun(ScalePlanTestRun planTestRun) {
    Response response = SCALE_ATM_CLIENT.saveTestRun(planTestRun);

    try {
      return JsonPath.read(
          IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8), "$.key");
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse response from test run " + planTestRun, e);
    }
  }

  public ScaleTestRun getTestRun(final String testRunKey) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    return SCALE_ATM_CLIENT.getTestRun(testRunKey);
  }

  private Set<ScaleTestRun> _getAllTestRuns(
      final int startAt, final String fieldsToRead, final String folder) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    log.debug(
        "All Test Runs, projectKey: {}, fields: {}, startAT: {}, maxResult: {}",
        CoreConfigs.getProjectCode(),
        fieldsToRead,
        startAt,
        CoreConfigs.getBufferSize());

    if (StringUtils.isEmpty(fieldsToRead)) {
      return SCALE_ATM_CLIENT.searchTestRun(
          startAt,
          CoreConfigs.getBufferSize(),
          String.format(
              "projectKey = \"%s\" AND folder = \"%s\"", CoreConfigs.getProjectCode(), folder));
    }

    return SCALE_ATM_CLIENT.searchTestRun(
        startAt,
        CoreConfigs.getBufferSize(),
        fieldsToRead,
        String.format(
            "projectKey = \"%s\" AND folder = \"%s\"", CoreConfigs.getProjectCode(), folder));
  }
}
