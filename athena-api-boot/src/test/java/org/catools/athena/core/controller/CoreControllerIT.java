package org.catools.athena.core.controller;

import org.catools.athena.AthenaBaseTest;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.configs.StagedTestData;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoreControllerIT implements AthenaBaseTest {

  @LocalServerPort
  protected int port;

  protected Project PROJECT;
  protected ProjectDto PROJECT_DTO;
  protected Environment ENVIRONMENT;
  protected EnvironmentDto ENVIRONMENT_DTO;
  protected Project PROJECT2;
  protected ProjectDto PROJECT2_DTO;
  protected User USER;
  protected UserDto USER_DTO;
  protected AppVersion VERSION;
  protected VersionDto VERSION_DTO;

  @BeforeAll
  public void beforeAllPackages() {
    if (PROJECT == null) {
      PROJECT = StagedTestData.getProject(1);
      PROJECT_DTO = CoreBuilder.buildProjectDto(PROJECT);
    }

    if (ENVIRONMENT == null) {
      ENVIRONMENT = StagedTestData.getEnvironment(1);
      ENVIRONMENT_DTO = CoreBuilder.buildEnvironmentDto(ENVIRONMENT);
    }

    if (PROJECT2 == null) {
      PROJECT2 = StagedTestData.getProject(2);
      PROJECT2_DTO = CoreBuilder.buildProjectDto(PROJECT2);
    }

    if (USER == null) {
      USER = StagedTestData.getUser(1);
      USER_DTO = CoreBuilder.buildUserDto(USER);
    }

    if (PROJECT_DTO != null && VERSION == null) {
      VERSION = StagedTestData.getVersion(1);
      VERSION_DTO = CoreBuilder.buildVersion(VERSION);
    }
  }
}