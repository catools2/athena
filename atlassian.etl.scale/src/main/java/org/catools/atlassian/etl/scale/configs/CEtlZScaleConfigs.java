package org.catools.atlassian.etl.scale.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

import java.util.List;

@UtilityClass
public class CEtlZScaleConfigs {
  @UtilityClass
  public static class Scale {
    public static List<String> getSyncTestCasesFolders() {
      return CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_ETL_SCALE_TEST_CASE_FOLDERS_TO_SYNC);
    }

    public static List<String> getSyncTestRunsFolders() {
      return CHocon.asStrings(Configs.CATOOLS_ATLASSIAN_ETL_SCALE_TEST_RUN_FOLDERS_TO_SYNC);
    }

    @Getter
    @AllArgsConstructor
    private enum Configs implements CHoconPath {
      CATOOLS_ATLASSIAN_ETL_SCALE_TEST_CASE_FOLDERS_TO_SYNC("catools.atlassian.etl.scale.test_case_folders_to_sync"),
      CATOOLS_ATLASSIAN_ETL_SCALE_TEST_RUN_FOLDERS_TO_SYNC("catools.atlassian.etl.scale.test_run_folders_to_sync");

      private final String path;
    }
  }
}
