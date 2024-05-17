package org.catools.athena.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.core.common.service.EnvironmentService;
import org.catools.athena.core.configs.StagedTestData;
import org.catools.athena.core.rest.controller.EnvironmentController;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnvironmentController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnvironmentControllerTest {

  @Autowired
  MockMvc mvc;

  @SuppressWarnings("unused")
  @MockBean
  EnvironmentService environmentService;

  @Test
  @Order(1)
  void saveEndPoint_ShouldReturnBadRequestIfProjectNotProvided() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders
            .post(CorePathDefinitions.ROOT_API + EnvironmentController.ENVIRONMENT)
            .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildEnvironmentDto(StagedTestData.getProject(1).getCode()).setProject(null)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isBadRequest(),
        jsonPath("$.[0].object").value(IsEqual.equalTo("environmentDto")),
        jsonPath("$.[0].field").value(IsEqual.equalTo("project")),
        jsonPath("$.[0].violations").value(IsEqual.equalTo("The environment project must be provided."))
    );
  }

  @Test
  @Order(1)
  void saveEndPoint_ShouldReturnBadRequestIfCodeNotProvided() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders
            .post(CorePathDefinitions.ROOT_API + EnvironmentController.ENVIRONMENT)
            .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildEnvironmentDto(StagedTestData.getProject(1).getCode()).setCode(null)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isBadRequest(),
        jsonPath("$.[0].object").value(IsEqual.equalTo("environmentDto")),
        jsonPath("$.[0].field").value(IsEqual.equalTo("code")),
        jsonPath("$.[0].violations").value(IsEqual.equalTo("The environment code must be provided."))
    );
  }
}