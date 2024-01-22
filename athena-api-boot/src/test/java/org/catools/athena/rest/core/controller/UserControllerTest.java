package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Set;

import static org.catools.athena.utils.ConstraintViolationUtil.assertThrowsConstraintViolation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testcontainers.utility.Base58.randomString;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends CoreControllerTest {

  @Test
  @Order(1)
  void saveUserShouldSaveUserIfAllFieldsAreProvided() {
    UserDto userDto = CoreBuilder.buildUserDto();
    ResponseEntity<Void> responseEntity = userController.saveUser(userDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    UserDto savedUser = userController.getUserById(id).getBody();
    assertThat(savedUser, notNullValue());
    assertThat(savedUser.getName(), equalTo(userDto.getName()));
  }


  @Test
  @Order(1)
  void saveUserShouldNotSaveUserIfUserNameIsNull() {
    UserDto userDto = CoreBuilder.buildUserDto();
    userDto.setName(null);
    assertThrowsConstraintViolation(() -> userController.saveUser(userDto),
        "name",
        "The user name must be provided.");
  }

  @Test
  @Order(2)
  void saveUserShallNotSaveSameUserTwice() {
    ResponseEntity<Void> responseEntity = userController.saveUser(USER_DTO);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));
    assertThat(responseEntity.getBody(), nullValue());
  }


  @Test
  @Order(2)
  void getUserShallReturnEmptyBodyIfInvalidCodeProvided() {
    ResponseEntity<UserDto> response = userController.getUserByName(randomString(10));
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getUserShallReturnEmptyBodyIfProvidedCodeIsNull() {
    ResponseEntity<UserDto> response = userController.getUserByName(null);
    assertThat(response.getStatusCode().value(), equalTo(204));
    assertThat(response.getBody(), nullValue());
  }

  @Test
  @Order(2)
  void getUserShallReturnUserIfValidNameProvided() {
    ResponseEntity<UserDto> response = userController.getUserByName(USER_DTO.getName());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(USER_DTO.getName()));
  }

  @Test
  @Order(10)
  void getUsersShallReturnUsersIfValidProjectCodeProvided() {
    ResponseEntity<Set<UserDto>> response = userController.getUsers();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    UserDto userDto = response.getBody().stream().filter(p -> p.getName().equals(USER_DTO.getName())).findFirst().orElse(null);
    assertThat(userDto, notNullValue());
    assertThat(userDto.getName(), equalTo(USER_DTO.getName()));
  }
}