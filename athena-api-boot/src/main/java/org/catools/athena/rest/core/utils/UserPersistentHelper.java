package org.catools.athena.rest.core.utils;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.UserAlias;
import org.catools.athena.rest.core.repository.UserAliasRepository;
import org.catools.athena.rest.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserPersistentHelper {

  private final UserRepository userRepository;

  private final UserAliasRepository userAliasRepository;

  public Optional<User> save(final User user) {
    User savedUser = userRepository.saveAndFlush(user);
    normalizeAliases(user);
    return userRepository.findById(savedUser.getId());
  }

  private void normalizeAliases(User user) {
    Set<UserAlias> aliases = new HashSet<>(user.getAliases());
    user.getAliases().clear();
    for (UserAlias alias : aliases) {
      Optional<UserAlias> aliasFromDB = userAliasRepository.findByAlias(alias.getAlias());
      if (aliasFromDB.isPresent()) alias.setUser(user);
      else userAliasRepository.save(alias.setUser(user));
    }
  }
}
