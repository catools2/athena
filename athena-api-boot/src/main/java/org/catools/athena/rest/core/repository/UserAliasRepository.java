package org.catools.athena.rest.core.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.entity.UserAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface UserAliasRepository extends JpaRepository<UserAlias, Long> {

  Optional<UserAlias> findByAlias(String alias);

  Set<UserAlias> findByUserId(Long userId);

}
