package org.catools.athena.rest.feign.kube;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.kube.configs.KubeConfigs;
import org.catools.athena.rest.feign.kube.utils.KubeLoader;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class KubeCommands {

  @ShellMethod(value = "Load Kubernetes namespace data", key = "load")
  public void load(
      @ShellOption(value = {"-ah", "--athena-host"}, help = "The Athena api endpoint to send information to", defaultValue = ShellOption.NULL) String athenaHost,
      @ShellOption(value = {"-pn", "--project-name"}, help = "The unique project name to use for project identification", defaultValue = ShellOption.NULL) String projectName,
      @ShellOption(value = {"-pc", "--project-code"}, help = "The unique project code to use for project identification", defaultValue = ShellOption.NULL) String projectCode,
      @ShellOption(value = {"-t", "--threads"}, help = "The number of total threads to use for parallel processing", defaultValue = ShellOption.NULL) Integer threadsCount,
      @ShellOption(value = {"-m", "--timeout-in-minutes"}, help = "The total amount of wait for sync to be finished", defaultValue = ShellOption.NULL) Long timeoutInMinutes,
      @ShellOption(value = {"-ns", "--namespaces"}, help = "The namespaces to read data from", defaultValue = ShellOption.NULL) List<String> namespaces,
      @ShellOption(value = {"-ct", "--connection-type"}, help = "The connection type to be used for kubernetes interaction. [DEFAULT, URL, CREDENTIAL, TOKEN, CONFIG]", defaultValue = ShellOption.NULL) String connectionType,
      @ShellOption(value = {"-s", "--ssl"}, help = "If connection should use SSL validation", defaultValue = ShellOption.NULL) Boolean shouldValidateSSL,
      @ShellOption(value = {"-l", "--connection-url"}, help = "The connection url", defaultValue = ShellOption.NULL) String connectionUrl,
      @ShellOption(value = {"-u", "--username"}, help = "The username to be used for connection", defaultValue = ShellOption.NULL) String connectionUsername,
      @ShellOption(value = {"-p", "--password"}, help = "The password to be used for connection", defaultValue = ShellOption.NULL) String connectionPassword,
      @ShellOption(value = {"-tk", "--connection-token"}, help = "The token to be used for connection", defaultValue = ShellOption.NULL) String connectionToken,
      @ShellOption(value = {"-f", "--config-file"}, help = "The path to the config file location to be used for connection", defaultValue = ShellOption.NULL) String kubeConfigPath
  ) {
    // Load configuration
    if (StringUtils.isNoneBlank(athenaHost)) {
      CoreConfigs.setAthenaHost(athenaHost);
    }

    if (StringUtils.isNoneBlank(projectName)) {
      CoreConfigs.setProjectName(projectName);
    }

    if (StringUtils.isNoneBlank(projectCode)) {
      CoreConfigs.setProjectCode(projectCode);
    }

    if (threadsCount != null) {
      CoreConfigs.setThreadsCount(threadsCount);
    }

    if (timeoutInMinutes != null) {
      CoreConfigs.setTimeoutInMinutes(timeoutInMinutes);
    }

    if (namespaces != null && !namespaces.isEmpty()) {
      KubeConfigs.setNamespaces(namespaces);
    }

    if (StringUtils.isNoneBlank(connectionType)) {
      KubeConfigs.setConnectionType(connectionType);
    }

    if (shouldValidateSSL != null) {
      KubeConfigs.setShouldValidateSSL(shouldValidateSSL);
    }

    if (StringUtils.isNoneBlank(connectionUrl)) {
      KubeConfigs.setConnectionUrl(connectionUrl);
    }

    if (StringUtils.isNoneBlank(connectionUsername)) {
      KubeConfigs.setConnectionUsername(connectionUsername);
    }

    if (StringUtils.isNoneBlank(connectionPassword)) {
      KubeConfigs.setConnectionPassword(connectionPassword);
    }

    if (StringUtils.isNoneBlank(connectionToken)) {
      KubeConfigs.setConnectionToken(connectionToken);
    }

    if (StringUtils.isNoneBlank(kubeConfigPath)) {
      KubeConfigs.setKubeConfigPath(kubeConfigPath);
    }

    // Execute load
    KubeLoader.loadNamespaces(KubeConfigs.getNamespaces(), CoreConfigs.getThreadsCount(), CoreConfigs.getTimeoutInMinutes());
  }
}

