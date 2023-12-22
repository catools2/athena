package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaUserControllerTest extends AthenaCoreControllerTest {

  private static UserDto USER_DTO;

  @BeforeAll
  public void beforeAll() {
    USER_DTO = AthenaCoreBuilder.buildUserDto();
  }

  @Test
  @Order(1)
  void saveUser() {
    ResponseEntity<UserDto> response = athenaUserController.saveUser(USER_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
  }

  @Test
  @Order(10)
  void getUser() {
    ResponseEntity<UserDto> response = athenaUserController.getUser(USER_DTO.getName());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(USER_DTO.getName()));
  }

  @Test
  @Order(10)
  void getUsers() {
    ResponseEntity<List<UserDto>> response = athenaUserController.getUsers();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    UserDto userDto = response.getBody().stream().filter(p -> p.getName().equals(USER_DTO.getName())).findFirst().orElse(null);
    assertThat(userDto, notNullValue());
    assertThat(userDto.getName(), equalTo(USER_DTO.getName()));
  }
}