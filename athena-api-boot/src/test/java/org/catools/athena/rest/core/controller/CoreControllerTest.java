package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.catools.athena.rest.core.repository.UserRepository;
import org.catools.athena.rest.core.repository.VersionRepository;
import org.catools.athena.rest.core.utils.UserPersistentHelper;
import org.catools.athena.rest.pipeline.controller.PipelineController;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoreControllerTest extends AthenaBaseTest {
  protected Project PROJECT;
  protected ProjectDto PROJECT_DTO;

  protected Project PROJECT2;
  protected ProjectDto PROJECT2_DTO;

  protected User USER;
  protected UserDto USER_DTO;

  protected Version VERSION;
  protected VersionDto VERSION_DTO;

  @Autowired
  protected ProjectRepository projectRepository;

  @Autowired
  protected VersionRepository versionRepository;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  private UserPersistentHelper userPersistentHelper;

  @Autowired
  protected ProjectController projectController;

  @Autowired
  protected EnvironmentController environmentController;

  @Autowired
  protected UserController userController;

  @Autowired
  protected PipelineController pipelineController;

  @Autowired
  protected VersionController versionController;


  @BeforeAll
  public void beforeAllPackages() {
    if (PROJECT == null) {
      PROJECT_DTO = CoreBuilder.buildProjectDto();
      PROJECT = projectRepository.saveAndFlush(CoreBuilder.buildProject(PROJECT_DTO));
      PROJECT_DTO.setId(PROJECT.getId());
    }

    if (PROJECT2 == null) {
      PROJECT2_DTO = CoreBuilder.buildProjectDto();
      PROJECT2 = projectRepository.saveAndFlush(CoreBuilder.buildProject(PROJECT2_DTO));
      PROJECT2_DTO.setId(PROJECT2.getId());
    }

    if (USER == null) {
      USER_DTO = CoreBuilder.buildUserDto();
      USER = userPersistentHelper.save(CoreBuilder.buildUser(USER_DTO)).orElse(null);
      USER_DTO.setId(USER.getId());
    }

    if (PROJECT_DTO != null && VERSION == null) {
      VERSION_DTO = CoreBuilder.buildVersionDto(PROJECT_DTO);
      VERSION = versionRepository.saveAndFlush(CoreBuilder.buildVersion(VERSION_DTO, PROJECT));
      VERSION_DTO.setId(VERSION.getId());
    }
  }
}