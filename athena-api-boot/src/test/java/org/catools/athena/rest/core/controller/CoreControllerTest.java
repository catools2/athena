package org.catools.athena.rest.core.controller;

import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.catools.athena.rest.pipeline.controller.PipelineController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoreControllerTest extends AthenaBaseTest {

  @Autowired
  protected ProjectRepository projectRepository;

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
}