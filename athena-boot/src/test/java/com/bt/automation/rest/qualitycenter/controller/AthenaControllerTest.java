package com.bt.automation.rest.qualitycenter.controller;

import com.bt.automation.rest.qualitycenter.AthenaBaseTest;
import org.catools.athena.rest.controller.AthenaEnvironmentController;
import org.catools.athena.rest.controller.AthenaPipelineController;
import org.catools.athena.rest.controller.AthenaProjectController;
import org.catools.athena.rest.controller.AthenaUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AthenaControllerTest extends AthenaBaseTest {

  @Autowired
  protected AthenaProjectController athenaProjectController;

  @Autowired
  protected AthenaEnvironmentController athenaEnvironmentController;

  @Autowired
  protected AthenaUserController athenaUserController;

  @Autowired
  protected AthenaPipelineController athenaPipelineController;

}