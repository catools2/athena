package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.UserAlias;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.UserAliasRepository;
import org.catools.athena.core.common.repository.UserRepository;
import org.catools.athena.core.model.UserAliasDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.utils.UserPersistentHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
    return userRepository.findByUsername(username).map(coreMapper::userToUserDto);
  }

  @Override
  public Optional<UserDto> search(String keyword) {
    Optional<UserDto> user = getByUsername(keyword);

    if (user.isEmpty()) {
      user = userAliasRepository.findByAlias(keyword).map(ua -> coreMapper.userToUserDto(ua.getUser()));
    }

    return user;
  }

  @Override
  public UserDto saveOrUpdate(final UserDto userDto) {
    User userToSave = userRepository.findByUsername(userDto.getUsername())
        .or(() -> searchByAlias(userDto.getAliases()).map(UserAlias::getUser)).map(user -> {
          for (UserAliasDto alias : userDto.getAliases()) {
            if (user.getAliases().stream().noneMatch(a -> a.getAlias().equals(alias.getAlias())))
              user.addAlias(alias.getId(), alias.getAlias());
          }
          return user;
        }).orElseGet(() -> coreMapper.userDtoToUser(userDto));

    final User savedUser = userPersistentHelper.save(userToSave).orElse(null);
    return coreMapper.userToUserDto(savedUser);
  }

  private Optional<UserAlias> searchByAlias(Set<UserAliasDto> aliases) {
    for (UserAliasDto alias : aliases) {
      Optional<UserAlias> byAlias = userAliasRepository.findByAlias(alias.getAlias());
      if (byAlias.isPresent()) {
        return byAlias;
      }
    }
    return Optional.empty();
  }
}