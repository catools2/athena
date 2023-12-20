package org.catools.athena.rest.repository.core;

import org.catools.athena.rest.entity.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String name);
}
