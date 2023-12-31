package org.catools.athena.rest.core.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
