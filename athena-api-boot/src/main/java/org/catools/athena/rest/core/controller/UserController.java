package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.core.controller.CoreDefinitions.USERS_PATH;
import static org.catools.athena.rest.core.controller.CoreDefinitions.USER_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena User Rest API")
@RequestMapping(value = CoreDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(USERS_PATH)
    @Operation(
            summary = "Retrieve users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<Set<UserDto>> getUsers() {
        return ResponseEntityUtils.okOrNoContent(userService.getUsers());
    }

    @GetMapping(USER_PATH)
    @Operation(
            summary = "Retrieve user by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<UserDto> getUserByName(
            @Parameter(name = "name", description = "The name of the user to retrieve")
            @RequestParam final String name
    ) {
        return ResponseEntityUtils.okOrNoContent(userService.getUserByName(name));
    }

    @GetMapping(USER_PATH + "/{id}")
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
        return ResponseEntityUtils.okOrNoContent(userService.getUserById(id));
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
        final Optional<UserDto> userFromDb = userService.getUserByName(user.getName());
        if (userFromDb.isPresent()) {
            return ResponseEntityUtils.alreadyReported(USER_PATH, userFromDb.get().getId());
        }

        final UserDto savedUserDto = userService.save(user);
        return ResponseEntityUtils.created(USER_PATH, savedUserDto.getId());
    }
}