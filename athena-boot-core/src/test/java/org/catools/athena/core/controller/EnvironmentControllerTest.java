package org.catools.athena.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.service.EnvironmentService;
import org.catools.athena.core.rest.controller.EnvironmentController;
import org.catools.athena.core.test.TestCacheConfig;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnvironmentController.class)
@Import({TestCacheConfig.class, ControllerErrorHandler.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnvironmentControllerTest {

  @Autowired
  MockMvc mvc;

  @SuppressWarnings("unused")
  @MockitoBean
  EnvironmentService environmentService;

  @Test
  @Order(1)
  void saveEndPoint_ShouldReturnBadRequestIfProjectNotProvided() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders
            .post(EnvironmentController.ENVIRONMENT)
            .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildEnvironmentDto("P1").setProject(null)))
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
            .post(EnvironmentController.ENVIRONMENT)
            .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildEnvironmentDto("P1").setCode(null)))
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