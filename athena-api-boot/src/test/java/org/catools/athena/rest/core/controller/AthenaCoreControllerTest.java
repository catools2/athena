package org.catools.athena.rest.core.controller;

import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.pipeline.controller.AthenaPipelineController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AthenaCoreControllerTest extends AthenaBaseTest {

  @Autowired
  protected AthenaProjectController athenaProjectController;

  @Autowired
  protected AthenaEnvironmentController athenaEnvironmentController;

  @Autowired
  protected AthenaUserController athenaUserController;

  @Autowired
  protected AthenaPipelineController athenaPipelineController;
}