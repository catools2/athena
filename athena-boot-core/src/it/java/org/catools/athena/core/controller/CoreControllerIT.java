package org.catools.athena.core.controller;

import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.feign.VersionFeignClient;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.junit.jupiter.api.BeforeAll;

public class CoreControllerIT extends AthenaSpringBootIT {
  protected Project project;
  protected ProjectDto projectDto;
  protected Environment environment;
  protected EnvironmentDto environmentDto;
  protected Project project2;
  protected ProjectDto project2Dto;
  protected User user;
  protected UserDto userDto;
  protected AppVersion version;
  protected VersionDto versionDto;
  protected EnvironmentFeignClient environmentFeignClient;
  protected ProjectFeignClient projectFeignClient;
  protected UserFeignClient userFeignClient;
  protected VersionFeignClient versionFeignClient;

  @BeforeAll
  public void beforeAllPackages() {
    if (environmentFeignClient == null) {
      environmentFeignClient = testFeignBuilder.getClient(EnvironmentFeignClient.class);
    }

    if (projectFeignClient == null) {
      projectFeignClient = testFeignBuilder.getClient(ProjectFeignClient.class);
    }

    if (userFeignClient == null) {
      userFeignClient = testFeignBuilder.getClient(UserFeignClient.class);
    }

    if (versionFeignClient == null) {
      versionFeignClient = testFeignBuilder.getClient(VersionFeignClient.class);
    }

    if (project == null) {
      projectDto = StagedTestData.getProject(1);
      project = CoreBuilder.buildProject(projectDto);
    }

    if (environment == null) {
      environmentDto = StagedTestData.getEnvironment(1);
      environment = CoreBuilder.buildEnvironment(environmentDto, project);
    }

    if (project2 == null) {
      project2Dto = StagedTestData.getProject(2);
      project2 = CoreBuilder.buildProject(project2Dto);
    }

    if (user == null) {
      userDto = StagedTestData.getUser(1);
      user = CoreBuilder.buildUser(userDto);
    }

    if (projectDto != null && version == null) {
      versionDto = StagedTestData.getVersion(1);
      version = CoreBuilder.buildVersion(versionDto, project);
    }
  }
}