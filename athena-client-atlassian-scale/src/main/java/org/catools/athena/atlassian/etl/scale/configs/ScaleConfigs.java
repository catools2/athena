package org.catools.athena.atlassian.etl.scale.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;

import java.util.List;

@UtilityClass
public class ScaleConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static String scaleHost;

  @Setter
  @Getter
  private static String scaleAccessToken;

  @Setter
  @Getter
  private static String scaleUsername;

  @Setter
  @Getter
  private static String scalePassword;

  @Setter
  @Getter
  private static Long delayBetweenCallsInMilliseconds;

  @Setter
  @Getter
  private static List<String> testRunFoldersToSync;

  @Setter
  @Getter
  private static List<String> testCasesFoldersToSync;

  @Setter
  @Getter
  private static boolean syncTests;

  @Setter
  @Getter
  private static boolean syncRuns;

  public static String getAtmUri() {
    return scaleHost + "/rest/atm/1.0/";
  }

  public static String getTestsUri() {
    return scaleHost + "/rest/tests/1.0/";
  }

  public static void reload() {
    scaleHost = ConfigUtils.getString("athena.scale.host");
    scaleAccessToken = ConfigUtils.getString("athena.scale.access_token");
    scaleUsername = ConfigUtils.getString("athena.scale.username");
    scalePassword = ConfigUtils.getString("athena.scale.password");
    delayBetweenCallsInMilliseconds = ConfigUtils.getLong("athena.scale.delay_between_calls_in_milliseconds", 1000L);
    testRunFoldersToSync = ConfigUtils.getStrings("athena.scale.test_run_folders_to_sync", List.of("/"));
    testCasesFoldersToSync = ConfigUtils.getStrings("athena.scale.test_cases_folders_to_sync", List.of(""));
    syncTests = ConfigUtils.getBoolean("athena.scale.sync_tests", true);
    syncRuns = ConfigUtils.getBoolean("athena.scale.sync_runs", true);
  }
}
