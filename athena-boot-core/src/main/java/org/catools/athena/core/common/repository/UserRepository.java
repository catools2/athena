package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

@Hidden
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByUsernameIgnoreCase(String username);

  @Query("SELECT u FROM User u LEFT JOIN FETCH u.aliases ua WHERE lower(u.username) in :keywords or lower(ua.alias) in :keywords")
  Optional<User> findByKeywords(@Param("keywords") Set<String> keywords);

  @Override
  <S extends User> S save(S entity);

}
