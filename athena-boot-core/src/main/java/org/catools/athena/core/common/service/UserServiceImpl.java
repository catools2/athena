package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.UserAliasRepository;
import org.catools.athena.core.common.repository.UserRepository;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.utils.UserPersistentHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserPersistentHelper userPersistentHelper;

  private final UserRepository userRepository;

  private final UserAliasRepository userAliasRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public Optional<UserDto> getById(final Long id) {
    final Optional<User> user = userRepository.findById(id);
    return user.map(coreMapper::userToUserDto);
  }

  @Override
  public Optional<UserDto> getByUsername(String username) {
    return userRepository.findByUsernameIgnoreCase(username).map(coreMapper::userToUserDto);
  }

  @Override
  public Optional<UserDto> search(final String keyword) {
    Optional<UserDto> user = getByUsername(keyword);

    if (user.isEmpty()) {
      user = userAliasRepository.findByAliasIgnoreCase(keyword).map(ua -> coreMapper.userToUserDto(ua.getUser()));
    }

    return user;
  }

  @Override
  public UserDto saveOrUpdate(final UserDto entity) {
    log.debug("Saving entity: {}", entity);
    User userToSave = coreMapper.userDtoToUser(entity);
    final User savedUser = userPersistentHelper.save(userToSave);
    return coreMapper.userToUserDto(savedUser);
  }
}