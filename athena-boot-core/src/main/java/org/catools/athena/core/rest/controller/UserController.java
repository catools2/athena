package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.common.markers.IdRequired;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.service.UserService;
import org.catools.athena.core.entity.UserFilterDto;
import org.catools.athena.model.core.UserDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.catools.athena.core.rest.controller.UserController.USER;

@RestController
@Tag(name = "Athena User Rest API")
@RequestMapping(value = USER, produces = MediaType.APPLICATION_JSON_VALUE)
@Import(ControllerErrorHandler.class)
@RequiredArgsConstructor
public class UserController {

  public static final String USER = "/user";

  private final UserService userService;

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve all users with pagination and optional filters",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Page<UserDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "username") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "ASC") final String direction,
      @Parameter(name = "username", description = "Filter by username")
      @RequestParam(required = false) final String username,
      @Parameter(name = "alias", description = "Filter by alias")
      @RequestParam(required = false) final String alias
  ) {
    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    // Build UserFilterDto from individual parameters
    UserFilterDto filter = new UserFilterDto(username, alias);
    Page<UserDto> result = userService.getAll(pageable, filter);
    return ResponseEntity.ok(result);
  }

  @GetMapping
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

  @GetMapping("/{id}")
  @Operation(
      summary = "Retrieve user by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<UserDto> getById(
      @Parameter(name = "id", description = "The id of the user to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(userService.getById(id));
  }

  @PostMapping
  @Operation(
      summary = "Save user with all related alias",
      responses = {
          @ApiResponse(responseCode = "201", description = "User is created"),
          @ApiResponse(responseCode = "208", description = "User already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request"),
          @ApiResponse(responseCode = "409", description = "User already exists")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to save or update")
      @Validated @RequestBody final UserDto user
  ) {
    try {
      final UserDto savedUserDto = userService.save(user);
      return ResponseEntityUtils.created(USER, savedUserDto.getId());
    } catch (DataIntegrityViolationException ex) {
      if (ex.getCause() instanceof ConstraintViolationException) {
        Optional<User> dbRecord = userService.search(user);
        if (dbRecord.isPresent())
          return ResponseEntityUtils.alreadyReported(USER, dbRecord.get().getId());
      }
      return ResponseEntityUtils.conflicted();
    }
  }

  @PutMapping
  @Operation(
      summary = "update username or alias if one with the provided id exists",
      responses = {
          @ApiResponse(responseCode = "200", description = "User is updated"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to update")
      @Validated(IdRequired.class) @RequestBody final UserDto user
  ) {
    try {
      final UserDto savedUserDto = userService.update(user);
      return ResponseEntityUtils.updated(USER, savedUserDto.getId());
    } catch (DataIntegrityViolationException ex) {
      if (ex.getCause() instanceof ConstraintViolationException) {
        Optional<User> dbRecord = userService.search(user);
        if (dbRecord.isPresent())
          return ResponseEntityUtils.alreadyReported(USER, dbRecord.get().getId());
      }
      return ResponseEntityUtils.conflicted();
    }
  }
}
