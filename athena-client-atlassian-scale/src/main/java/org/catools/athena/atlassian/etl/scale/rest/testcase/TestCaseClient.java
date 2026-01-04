package org.catools.athena.atlassian.etl.scale.rest.testcase;

import com.jayway.jsonpath.JsonPath;
import feign.Response;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.client.ScaleAtmClient;
import org.catools.athena.atlassian.etl.scale.client.ScaleTestClient;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
import org.catools.athena.atlassian.etl.scale.model.ScaleChangeHistory;
import org.catools.athena.atlassian.etl.scale.model.ScaleTestCase;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.catools.athena.atlassian.etl.scale.client.ScaleClient.getScaleAtmClient;
import static org.catools.athena.atlassian.etl.scale.client.ScaleClient.getScaleTestsClient;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;
import static org.catools.athena.rest.feign.common.utils.ThreadUtils.sleep;

@Slf4j
@UtilityClass
public class TestCaseClient {

  private static final ScaleTestClient SCALE_TEST_CLIENT = getScaleTestsClient(ScaleTestClient.class);

  private final ScaleAtmClient SCALE_ATM_CLIENT = getScaleAtmClient(ScaleAtmClient.class);

  public static void processProjectTestCases(final int threadsCount,
                                             final long timeoutInMinutes,
                                             final String testCasesFoldersToSync,
                                             String fields,
                                             Consumer<ScaleTestCase> onAction) {
    String query = String.format("projectKey = \"%s\"", CoreConfigs.getProjectCode());

    if (StringUtils.isNotBlank(testCasesFoldersToSync)) {
      query += String.format(" AND folder = \"%s\"", testCasesFoldersToSync);
    }

    processAllTestCases(threadsCount, timeoutInMinutes, query, fields, onAction);
  }

  public static void processAllTestCases(final int threadsCount,
                                         final long timeoutInMinutes,
                                         final String query,
                                         String fields,
                                         Consumer<ScaleTestCase> onAction) {
    AtomicInteger counter = new AtomicInteger(0);

    executeInParallel(threadsCount, timeoutInMinutes, () -> {
      while (true) {
        int startFrom = counter.getAndIncrement() * CoreConfigs.getBufferSize() + CoreConfigs.getStartAt();
        log.info("Process TestCases from {} to {} for query {}", startFrom, startFrom + CoreConfigs.getBufferSize(), query);
        Set<ScaleTestCase> scaleTestCases = readSectionTestCases(query, fields, startFrom, CoreConfigs.getBufferSize());
        if (scaleTestCases == null || scaleTestCases.isEmpty()) {
          return true;
        }
        scaleTestCases.forEach(onAction);
      }
    });
  }

  public static ScaleTestCase getTestCase(final String testCaseKey) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    String homeUri = ScaleConfigs.getAtmUri();
    log.debug("Get TestCase from {}, projectKey: {}", homeUri, testCaseKey);
    ScaleTestCase testCase = SCALE_ATM_CLIENT.getTestCase(testCaseKey);
    testCase.setHistories(_getTestCaseHistory(testCaseKey));
    return testCase;
  }

  private static Set<ScaleTestCase> readSectionTestCases(final String query, String fields, int startAt, int maxResults) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    String homeUri = ScaleConfigs.getAtmUri();
    log.trace("Send Request to {}, query: {}, fields: {}, startAT: {}, maxResult: {}", homeUri, query, fields, startAt, maxResults);
    if (StringUtils.isEmpty(fields)) {
      return SCALE_ATM_CLIENT.searchTestCase(startAt, maxResults, query);
    }

    return SCALE_ATM_CLIENT.searchTestCase(startAt, maxResults, fields, query);
  }

  private static HashSet<ScaleChangeHistory> _getTestCaseHistory(final String testCaseKey) {
    sleep(ScaleConfigs.getDelayBetweenCallsInMilliseconds());
    String homeUri = ScaleConfigs.getTestsUri();
    log.trace("Send history for {} from {}", testCaseKey, homeUri);
    return SCALE_TEST_CLIENT.getTestCaseHistory(_getTestId(testCaseKey));
  }

  private static Integer _getTestId(final String testCaseKey) {
    String homeUri = ScaleConfigs.getTestsUri();
    log.trace("Get {} id from {}", testCaseKey, homeUri);
    Response response = SCALE_TEST_CLIENT.getTestCaseAllVersions(testCaseKey, "id");
    try {
      return JsonPath.read(IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8), "$.[0].id");
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse response from client for testcase key " + testCaseKey, e);
    }
  }
}
