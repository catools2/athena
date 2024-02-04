package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.git.common.model.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface GitRepositoryRepository extends JpaRepository<GitRepository, Long> {

  Optional<GitRepository> findByNameOrUrl(String name, String url);
}
