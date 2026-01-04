package org.catools.athena.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.service.ProjectService;
import org.catools.athena.core.rest.controller.ProjectController;
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

@WebMvcTest(ProjectController.class)
@Import({TestCacheConfig.class, ControllerErrorHandler.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerTest {

  @Autowired
  MockMvc mvc;

  @SuppressWarnings("unused")
  @MockitoBean
  ProjectService projectService;

  @Test
  @Order(1)
  void saveEndPoint_ShouldReturnBadRequestIfCodeNotProvided() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders
                .post(ProjectController.PROJECT)
                .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildProjectDto().setCode(null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
        .andExpectAll(
            jsonPath("$.[0].object").value(IsEqual.equalTo("projectDto")),
            jsonPath("$.[0].field").value(IsEqual.equalTo("code")),
            jsonPath("$.[0].violations").value(IsEqual.equalTo("The project code must be provided."))
        );
  }

  @Test
  @Order(1)
  void saveEndPoint_ShouldReturnBadRequestIfNameNotProvided() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders
                .post(ProjectController.PROJECT)
                .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildProjectDto().setName(null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
        .andExpectAll(
            jsonPath("$.[0].object").value(IsEqual.equalTo("projectDto")),
            jsonPath("$.[0].field").value(IsEqual.equalTo("name")),
            jsonPath("$.[0].violations").value(IsEqual.equalTo("The project name must be provided."))
        );
  }

}