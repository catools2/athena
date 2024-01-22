package org.catools.athena.rest.core.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.mapper.CoreMapper;
import org.catools.athena.rest.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public Set<UserDto> getAll() {
    final List<User> users = userRepository.findAll();
    return users.stream().map(coreMapper::userToUserDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<UserDto> getUserByName(final String name) {
    final Optional<User> user = userRepository.findByName(name);
    return user.map(coreMapper::userToUserDto);
  }

  @Override
  public Optional<UserDto> getById(final Long id) {
    final Optional<User> user = userRepository.findById(id);
    return user.map(coreMapper::userToUserDto);
  }

  @Override
  public UserDto save(final UserDto userDto) {
    final User userToSave = coreMapper.userDtoToUser(userDto);
    final User savedUser = userRepository.saveAndFlush(userToSave);
    return coreMapper.userToUserDto(savedUser);
  }
}