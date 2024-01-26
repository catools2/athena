package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.core.config.CorePathDefinitions.USER;
import static org.catools.athena.rest.core.config.CorePathDefinitions.USERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena User Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(USERS)
  @Operation(
      summary = "Retrieve users",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<UserDto>> getUsers() {
    return ResponseEntityUtils.okOrNoContent(userService.getAll());
  }

  @GetMapping(USER)
  @Operation(
      summary = "Retrieve user who's username or any alias match the provided keyword",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<UserDto> search(
      @Parameter(name = "keyword", description = "The keyword to search user by")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(userService.search(keyword));
  }

  @GetMapping(USER + "/{id}")
  @Operation(
      summary = "Retrieve user by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<UserDto> getUserById(
      @Parameter(name = "id", description = "The id of the user to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(userService.getById(id));
  }

  @PostMapping(USER)
  @Operation(
      summary = "Save user",
      responses = {
          @ApiResponse(responseCode = "201", description = "User is created"),
          @ApiResponse(responseCode = "208", description = "User is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to save")
      @Validated @RequestBody final UserDto user
  ) {
    final Optional<UserDto> userFromDb = userService.getUserByUsername(user.getUsername());
    if (userFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(USER, userFromDb.get().getId());
    }

    final UserDto savedUserDto = userService.save(user);
    return ResponseEntityUtils.created(USER, savedUserDto.getId());
  }
}
