package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.UserDto;

import java.util.Optional;
import java.util.Set;


public interface UserService {

    /**
     * Returns a list of all available users.
     */
    Set<UserDto> getUsers();

    /**
     * Get user by name
     */
    Optional<UserDto> getUserByName(String name);

    /**
     * Get user by id
     */
    Optional<UserDto> getUserById(Long id);

    /**
     * Save user and return user id
     */
    UserDto save(UserDto userDto);

}