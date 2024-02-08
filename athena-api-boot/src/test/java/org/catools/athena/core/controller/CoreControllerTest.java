package org.catools.athena.core.controller;

import org.catools.athena.AthenaBaseTest;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.Version;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.core.rest.controller.EnvironmentController;
import org.catools.athena.core.rest.controller.ProjectController;
import org.catools.athena.core.rest.controller.UserController;
import org.catools.athena.core.rest.controller.VersionController;
import org.catools.athena.core.utils.UserPersistentHelper;
import org.catools.athena.pipeline.rest.controller.PipelineController;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

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
      USER = userPersistentHelper.save(CoreBuilder.buildUser(USER_DTO)).orElse(new User());
      USER_DTO.setId(USER.getId());
    }

    if (PROJECT_DTO != null && VERSION == null) {
      VERSION_DTO = CoreBuilder.buildVersionDto(PROJECT_DTO);
      VERSION = versionRepository.saveAndFlush(CoreBuilder.buildVersion(VERSION_DTO, PROJECT));
      VERSION_DTO.setId(VERSION.getId());
    }
  }

  @NotNull
  protected WebTestClient.ResponseSpec post(Object controller, String uri, ApiSpecDto apiSpecDto) {
    return getWebTestClient(controller)
        .post()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(apiSpecDto))
        .exchange();
  }

  @NotNull
  protected FluxExchangeResult<Void> postCreate(Object controller, String uri, ApiSpecDto apiSpecDto) {
    return post(controller, uri, apiSpecDto)
        .expectAll(
            spec -> spec.expectStatus().isCreated(),
            spec -> spec.expectHeader().exists("Location")
        )
        .returnResult(Void.class);
  }

}