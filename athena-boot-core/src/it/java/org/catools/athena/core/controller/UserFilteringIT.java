package org.catools.athena.core.controller;

import feign.TypedResponse;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.feign.PageResponse;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Integration tests for User entity filtering and pagination.
 * Tests the getAll() method with various filter combinations.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserFilteringIT extends CoreControllerIT {

  @Test
  @Order(1)
  void setupTestData() {
    // Create multiple users for filtering tests
    for (int i = 0; i < 5; i++) {
      UserDto userDto = CoreBuilder.buildUserDto();
      userFeignClient.save(userDto);
    }
  }

  @Test
  @Order(2)
  void getAllUsersShouldReturnAllUsersWhenNoFiltersProvided() {
    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 20, "username", "ASC", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());

    assertThat(page.getContent().size(), greaterThanOrEqualTo(5));
    assertThat(page.getTotalElements(), greaterThanOrEqualTo(5L));
  }

  @Test
  @Order(3)
  void getAllUsersShouldFilterByUsername() {
    // Get all users first to find a username
    TypedResponse<PageResponse<UserDto>> allResponse = userFeignClient.getAllUsers(0, 20, "username", "ASC", null, null);
    String targetUsername = allResponse.body().getContent().get(0).getUsername();

    // Filter by that username
    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 20, "username", "ASC", targetUsername, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getContent().get(0).getUsername(), equalTo(targetUsername));
  }

  @Test
  @Order(4)
  void getAllUsersShouldFilterByAlias() {
    // Create user with known alias
    UserDto userWithAlias = CoreBuilder.buildUserDto();
    UserAliasDto alias = new UserAliasDto(null, "TestAlias");
    userWithAlias.getAliases().add(alias);
    userFeignClient.save(userWithAlias);

    // Filter by alias
    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 20, "username", "ASC", null, "TestAlias");

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    boolean hasAlias = page.getContent().stream()
        .anyMatch(u -> u.getAliases().stream().anyMatch(a -> a.getAlias().equals("TestAlias")));
    assertThat(hasAlias, equalTo(true));
  }

  @Test
  @Order(5)
  void getAllUsersShouldFilterByBothUsernameAndAlias() {
    TypedResponse<PageResponse<UserDto>> allResponse = userFeignClient.getAllUsers(0, 20, "username", "ASC", null, null);
    UserDto targetUser = allResponse.body().getContent().stream()
        .filter(u -> u.getAliases().size() > 0)
        .findFirst()
        .orElseGet(() -> {
          UserDto newUser = CoreBuilder.buildUserDto();
          UserAliasDto alias = new UserAliasDto(null, "ComboAlias");
          newUser.getAliases().add(alias);
          userFeignClient.save(newUser);
          return newUser;
        });

    String username = targetUser.getUsername();
    String aliasName = targetUser.getAliases().stream().findFirst().orElseThrow().getAlias();

    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 20, "username", "ASC", username, aliasName);

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());
  }

  @Test
  @Order(6)
  void getAllUsersShouldSupportPaginationWithDefaultPageSize() {
    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 10, "username", "ASC", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(1));
    assertThat(page.getPageNumber(), equalTo(0));
    assertThat(page.getSize(), equalTo(10));
  }

  @Test
  @Order(7)
  void getAllUsersShouldSupportPaginationWithSecondPage() {
    TypedResponse<PageResponse<UserDto>> firstPage = userFeignClient.getAllUsers(0, 3, "username", "ASC", null, null);
    assertThat(firstPage.body().getContent().size(), greaterThanOrEqualTo(1));

    if (firstPage.body().getTotalElements() > 3) {
      TypedResponse<PageResponse<UserDto>> secondPage = userFeignClient.getAllUsers(1, 3, "username", "ASC", null, null);

      assertThat(secondPage.status(), equalTo(200));
      PageResponse<UserDto> page = secondPage.body();
      assertThat(page, notNullValue());
      assertThat(page.getPageNumber(), equalTo(1));
    }
  }

  @Test
  @Order(8)
  void getAllUsersShouldSortByUsername() {
    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 20, "username", "ASC", null, null);

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), greaterThanOrEqualTo(2));

    // Verify sorting is applied
    for (int i = 0; i < page.getContent().size() - 1; i++) {
      String current = page.getContent().get(i).getUsername();
      String next = page.getContent().get(i + 1).getUsername();
      assertThat(current.compareToIgnoreCase(next) <= 0, equalTo(true));
    }
  }

  @Test
  @Order(9)
  void getAllUsersShouldReturnEmptyWhenFilterMatchesNoUsers() {
    TypedResponse<PageResponse<UserDto>> response = userFeignClient.getAllUsers(0, 20, "username", "ASC", "NonExistentUser123456", null);

    assertThat(response.status(), equalTo(200));
    PageResponse<UserDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getContent().size(), equalTo(0));
    assertThat(page.getTotalElements(), equalTo(0L));
  }
}

