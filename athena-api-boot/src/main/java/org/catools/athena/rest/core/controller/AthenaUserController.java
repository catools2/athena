package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.AthenaCoreConstant;
import org.catools.athena.rest.core.exception.GeneralBadRequestException;
import org.catools.athena.rest.core.service.AthenaCoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena User API")
@RequestMapping(value = AthenaCoreConstant.ROOT_API, produces = APPLICATION_JSON_VALUE)
public class AthenaUserController {
    public static final String USER_PATH = "/user";
    public static final String USERS_PATH = "/users";
    private final AthenaCoreService athenaCoreService;

    public AthenaUserController(AthenaCoreService athenaCoreService) {
        this.athenaCoreService = athenaCoreService;
    }

    @GetMapping(USERS_PATH)
    @Operation(
            summary = "Retrieve users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<Set<UserDto>> getUsers() {
        final Set<UserDto> users = athenaCoreService.getUsers();
        return users.isEmpty() ?
                ResponseEntityUtils.noContent() :
                ResponseEntityUtils.ok(users);
    }

    @GetMapping(USER_PATH)
    @Operation(
            summary = "Retrieve user by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<UserDto> getUserByName(
            @Parameter(name = "name", description = "The name of the user to return")
            @RequestParam final String name
    ) {
        final Optional<UserDto> user = athenaCoreService.getUserByName(name);
        return user.map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @GetMapping(USER_PATH + "/{id}")
    @Operation(
            summary = "Retrieve user by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<UserDto> getUserById(
            @Parameter(name = "id", description = "The id of the user to return")
            @PathVariable final Long id
    ) {
        final Optional<UserDto> user = athenaCoreService.getUserById(id);
        return user.map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @PostMapping(USER_PATH)
    @Operation(
            summary = "Save user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User is created"),
                    @ApiResponse(responseCode = "208", description = "User is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to save")
            @Validated @RequestBody final UserDto user
    ) {
        try {
            final Optional<UserDto> userFromDb = athenaCoreService.getUserByName(user.getName());
            if (userFromDb.isPresent()) {
                return ResponseEntityUtils.alreadyReported(userFromDb.get().getId());
            }

            final UserDto savedUserDto = athenaCoreService.saveUser(user);
            return ResponseEntityUtils.created(savedUserDto.getId());
        } catch (Throwable generalEx) {
            // let GeneralExceptionHandler to take care of it
            throw new GeneralBadRequestException(generalEx);
        }
    }
}
