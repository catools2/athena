package org.catools.athena.core.common.service;

import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.entity.UserFilterDto;
import org.catools.athena.model.core.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
  Page<UserDto> getAll(Pageable pageable);

  Page<UserDto> getAll(Pageable pageable, UserFilterDto filter);

  Optional<UserDto> getById(Long id);

  Optional<UserDto> getByUsername(String username);

  Optional<UserDto> search(String keyword);

  Optional<User> search(UserDto entity);

  UserDto save(UserDto entity);

  UserDto update(UserDto entity);
}
