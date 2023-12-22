package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.config.AthenaCoreConstant;
import org.catools.athena.rest.core.exception.GeneralBadRequestException;
import org.catools.athena.rest.core.service.AthenaCoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.catools.athena.rest.core.config.AthenaCoreConstant.CACHE_MAX_AGE_ONE_HOUR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = AthenaCoreConstant.ROOT_API, produces = APPLICATION_JSON_VALUE)
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
    @ApiResponse(responseCode = "403", description = "Unauthorized to perform function")
})
public class AthenaUserController {
  public static final String USER = "/user";
  public static final String USERS = "/users";
  private final AthenaCoreService athenaCoreService;
  public AthenaUserController(AthenaCoreService athenaCoreService) {
    this.athenaCoreService = athenaCoreService;
  }

  @GetMapping(USERS)
  @Operation(
      summary = "Retrieve users",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<UserDto>> getUsers() {
    final List<UserDto> users = athenaCoreService.getUsers();
    return users.isEmpty() ?
        ResponseEntity.noContent().cacheControl(CACHE_MAX_AGE_ONE_HOUR).build() :
        ResponseEntity.ok().cacheControl(CACHE_MAX_AGE_ONE_HOUR).body(users);
  }

  @GetMapping(USER)
  @Operation(
      summary = "Retrieve user by name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<UserDto> getUser(
      @Parameter(name = "username")
      @RequestParam final String username
  ) {
    final Optional<UserDto> user = athenaCoreService.getUserByName(username);
    return user.map(userDto -> ResponseEntity.ok().cacheControl(CACHE_MAX_AGE_ONE_HOUR).body(userDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(CACHE_MAX_AGE_ONE_HOUR).build());
  }

  @PostMapping(USER)
  @Operation(
      summary = "Save user",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "208", description = "User is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<UserDto> saveUser(
      @Parameter(description = "User to save", name = "user", required = true)
      @Validated @RequestBody final UserDto user
  ) {
    try {
      final Optional<UserDto> userFromDb = athenaCoreService.getUserByName(user.getName());
      if (userFromDb.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final UserDto savedUserDto = athenaCoreService.saveUser(user);
      return ResponseEntity.ok().body(savedUserDto);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }
}
