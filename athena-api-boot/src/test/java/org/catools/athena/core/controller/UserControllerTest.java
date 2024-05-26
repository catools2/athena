package org.catools.athena.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.core.common.service.UserService;
import org.catools.athena.core.rest.controller.UserController;
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

@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

  @Autowired
  MockMvc mvc;

  @SuppressWarnings("unused")
  @MockBean
  UserService userService;

  @Test
  @Order(1)
  void saveEndPoint_ShouldReturnBadRequestIfUsernameNotProvided() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders
            .post(CorePathDefinitions.ROOT_API + UserController.USER)
            .content(new ObjectMapper().writeValueAsString(CoreBuilder.buildUserDto().setUsername(null)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isBadRequest(),
        jsonPath("$.[0].object").value(IsEqual.equalTo("userDto")),
        jsonPath("$.[0].field").value(IsEqual.equalTo("username")),
        jsonPath("$.[0].violations").value(IsEqual.equalTo("The username must be provided."))
    );
  }
}