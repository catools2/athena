package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.git.common.entity.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface GitRepositoryRepository extends JpaRepository<GitRepository, Long> {

  Optional<GitRepository> findByName(String name);

  Optional<GitRepository> findByNameOrUrl(String name, String url);
}
