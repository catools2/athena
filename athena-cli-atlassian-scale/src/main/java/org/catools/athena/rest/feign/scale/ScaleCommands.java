package org.catools.athena.rest.feign.scale;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.atlassian.etl.scale.ScaleSyncClient;
import org.catools.athena.atlassian.etl.scale.configs.ScaleConfigs;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class ScaleCommands {

  @ShellMethod(value = "Sync Scale test cases and/or test runs", key = "sync")
  public void sync(
      @ShellOption(value = {"-ah", "--athena-host"}, help = "The Athena api endpoint to send information to", defaultValue = ShellOption.NULL) String athenaHost,
      @ShellOption(value = {"-sh", "--scale-host"}, help = "The Scale api endpoint to read information from", defaultValue = ShellOption.NULL) String scaleHost,
      @ShellOption(value = {"-sat", "--scale-access-token"}, help = "The personal access token to be used for interaction with Scale api", defaultValue = ShellOption.NULL) String scaleAccessToken,
      @ShellOption(value = {"-su", "--scale-username"}, help = "The username to be used for interaction with Scale api", defaultValue = ShellOption.NULL) String scaleUsername,
      @ShellOption(value = {"-sp", "--scale-password"}, help = "The password to be used for interaction with Scale api", defaultValue = ShellOption.NULL) String scalePassword,
      @ShellOption(value = {"-pn", "--project-name"}, help = "The unique project name to use for project identification", defaultValue = ShellOption.NULL) String projectName,
      @ShellOption(value = {"-pc", "--project-code"}, help = "The unique project code to use for project identification", defaultValue = ShellOption.NULL) String projectCode,
      @ShellOption(value = {"-st", "--sync-tests"}, help = "Shall sync tests or not", defaultValue = "false") Boolean syncTests,
      @ShellOption(value = {"-sr", "--sync-runs"}, help = "Shall sync runs or not", defaultValue = "false") Boolean syncRuns,
      @ShellOption(value = {"-s", "--start-at"}, help = "The index to start query data from Scale", defaultValue = ShellOption.NULL) Integer startAt,
      @ShellOption(value = {"-b", "--buffer-size"}, help = "The buffer size to define maximum number of return value in each Scale call", defaultValue = ShellOption.NULL) Integer bufferSize,
      @ShellOption(value = {"-t", "--threads"}, help = "The number of total threads to use for parallel processing", defaultValue = ShellOption.NULL) Integer threadsCount,
      @ShellOption(value = {"-m", "--timeout-in-minutes"}, help = "The total amount of wait for sync to be finished", defaultValue = ShellOption.NULL) Long timeoutInMinutes,
      @ShellOption(value = {"-trf", "--test-run-folders-to-sync"}, help = "The test run folders to sync", defaultValue = ShellOption.NULL) List<String> testRunFoldersToSync,
      @ShellOption(value = {"-tcf", "--test-case-folders-to-sync"}, help = "The test case folders to sync", defaultValue = ShellOption.NULL) List<String> testCasesFoldersToSync
  ) {
    // Load configuration
    if (StringUtils.isNoneBlank(athenaHost)) {
      CoreConfigs.setAthenaHost(athenaHost);
    }

    if (StringUtils.isNoneBlank(scaleHost)) {
      ScaleConfigs.setScaleHost(scaleHost);
    }

    if (syncTests != null) {
      ScaleConfigs.setSyncTests(syncTests);
    }

    if (syncRuns != null) {
      ScaleConfigs.setSyncRuns(syncRuns);
    }

    if (StringUtils.isNoneBlank(scaleAccessToken)) {
      ScaleConfigs.setScaleAccessToken(scaleAccessToken);
    }

    if (StringUtils.isNoneBlank(scaleUsername)) {
      ScaleConfigs.setScaleUsername(scaleUsername);
    }

    if (StringUtils.isNoneBlank(scalePassword)) {
      ScaleConfigs.setScalePassword(scalePassword);
    }

    if (StringUtils.isNoneBlank(projectName)) {
      CoreConfigs.setProjectName(projectName);
    }

    if (StringUtils.isNoneBlank(projectCode)) {
      CoreConfigs.setProjectCode(projectCode);
    }

    if (startAt != null) {
      CoreConfigs.setStartAt(startAt);
    }

    if (bufferSize != null) {
      CoreConfigs.setBufferSize(bufferSize);
    }

    if (threadsCount != null) {
      CoreConfigs.setThreadsCount(threadsCount);
    }

    if (timeoutInMinutes != null) {
      CoreConfigs.setTimeoutInMinutes(timeoutInMinutes);
    }

    if (testCasesFoldersToSync != null) {
      ScaleConfigs.setTestCasesFoldersToSync(testCasesFoldersToSync);
    }

    if (testRunFoldersToSync != null) {
      ScaleConfigs.setTestRunFoldersToSync(testRunFoldersToSync);
    }

    // Execute sync
    if (ScaleConfigs.isSyncTests()) {
      ScaleSyncClient.syncTestCases();
    }

    if (ScaleConfigs.isSyncRuns()) {
      ScaleSyncClient.syncTestRuns();
    }
  }
}

