package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Set;

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
        ResponseEntity<Void> responseEntity = athenaUserController.saveUser(USER_DTO);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        UserDto savedUser = athenaUserController.getUserById(id).getBody();
        assertThat(savedUser, notNullValue());
        assertThat(savedUser.getName(), equalTo(USER_DTO.getName()));
    }

    @Test
    @Order(1)
    void doNotSaveUserTwice() {
        ResponseEntity<Void> responseEntity = athenaUserController.saveUser(USER_DTO);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), equalTo(208));
        assertThat(responseEntity.getBody(), nullValue());
    }

    @Test
    @Order(10)
    void getUserByName() {
        ResponseEntity<UserDto> response = athenaUserController.getUserByName(USER_DTO.getName());
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getName(), equalTo(USER_DTO.getName()));
    }

    @Test
    @Order(10)
    void getUsers() {
        ResponseEntity<Set<UserDto>> response = athenaUserController.getUsers();
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        UserDto userDto = response.getBody().stream().filter(p -> p.getName().equals(USER_DTO.getName())).findFirst().orElse(null);
        assertThat(userDto, notNullValue());
        assertThat(userDto.getName(), equalTo(USER_DTO.getName()));
    }
}