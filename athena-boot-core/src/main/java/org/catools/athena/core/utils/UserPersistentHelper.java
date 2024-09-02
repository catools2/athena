package org.catools.athena.core.utils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.UserAlias;
import org.catools.athena.core.common.repository.UserAliasRepository;
import org.catools.athena.core.common.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserPersistentHelper {

  private final UserRepository userRepository;

  private final UserAliasRepository userAliasRepository;

  public User save(final User entity) {
    Optional<User> userFromDB;
    if (entity.getId() != null) {
      userFromDB = userRepository.findById(entity.getId());
    } else {
      userFromDB = userRepository.findByUsernameIgnoreCase(entity.getUsername());
      if (userFromDB.isEmpty()) {
        Optional<UserAlias> byAlias = searchByAlias(entity.getAliases());
        if (byAlias.isPresent()) {
          userFromDB = Optional.of(byAlias.get().getUser());
        }
      }
    }

    // if user is in DB then update user alias with new user data
    User user = userFromDB.orElse(entity);
    if (userFromDB.isPresent()) {
      entity.getAliases().stream().filter(a1 -> user.getAliases().stream().noneMatch(a2 ->
          StringUtils.equalsAnyIgnoreCase(a1.getAlias(), a2.getAlias()))).forEach(a -> user.addAlias(a.getId(), a.getAlias()));
    }

    normalizeAliases(user);
    return userRepository.save(user);
  }

  private Optional<UserAlias> searchByAlias(Set<UserAlias> aliases) {
    Optional<UserAlias> output = Optional.empty();
    for (UserAlias alias : aliases) {
      Optional<UserAlias> byAlias = userAliasRepository.findByAliasIgnoreCase(alias.getAlias());
      if (byAlias.isPresent()) {
        output = byAlias;
        break;
      }
    }
    return output;
  }

  private void normalizeAliases(User user) {
    for (UserAlias alias : user.getAliases()) {
      alias.setUser(user);
      userAliasRepository.findByAliasIgnoreCase(alias.getAlias()).ifPresent(a -> alias.setId(a.getId()));
    }
  }
}
