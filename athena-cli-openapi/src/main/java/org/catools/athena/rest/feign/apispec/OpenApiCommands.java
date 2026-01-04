package org.catools.athena.rest.feign.apispec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.rest.feign.apispec.configs.OpenApiConfigs;
import org.catools.athena.rest.feign.apispec.helpers.ApiSpecLoader;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.util.List;

@Slf4j
@ShellComponent
public class OpenApiCommands {

  @ShellMethod(value = "Load OpenAPI specification data", key = "load")
  public void load(
      @ShellOption(value = {"-ah", "--athena-host"}, help = "The Athena api endpoint to send information to", defaultValue = ShellOption.NULL) String athenaHost,
      @ShellOption(value = {"-n", "--names"}, help = "The Open Api Spec Names", defaultValue = ShellOption.NULL) List<String> specNames,
      @ShellOption(value = {"-l", "--urls"}, help = "The urls to the Open Api spec json file", defaultValue = ShellOption.NULL) List<String> specUrls,
      @ShellOption(value = {"-s", "--spec-info"}, help = "Set of Open Api Spec name and url in json format i.e. [{\"name\": \"...\",\"url\": \"...\"}]", defaultValue = ShellOption.NULL) String specInfoSet,
      @ShellOption(value = {"-pn", "--project-name"}, help = "The unique project name to use for project identification", defaultValue = ShellOption.NULL) String projectName,
      @ShellOption(value = {"-pc", "--project-code"}, help = "The unique project code to use for project identification", defaultValue = ShellOption.NULL) String projectCode
  ) throws IOException {
    // Load configuration
    if (StringUtils.isNoneBlank(athenaHost)) {
      CoreConfigs.setAthenaHost(athenaHost);
    }
    if (StringUtils.isNoneBlank(projectCode)) {
      CoreConfigs.setProjectCode(projectCode);
    }
    if (StringUtils.isNoneBlank(projectName)) {
      CoreConfigs.setProjectName(projectName);
    }
    if (specNames != null && !specNames.isEmpty()) {
      OpenApiConfigs.setSpecNames(specNames);
    }
    if (specUrls != null && !specUrls.isEmpty()) {
      OpenApiConfigs.setSpecUrls(specUrls);
    }
    if (StringUtils.isNoneBlank(specInfoSet)) {
      OpenApiConfigs.setSpecInfo(specInfoSet);
    }

    // Execute load
    ApiSpecLoader.saveOpenApi();
  }
}

