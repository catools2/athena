package org.catools.athena.rest.git.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.git.model.DiffEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface FileChangeRepository extends JpaRepository<DiffEntry, Long> {

  Optional<DiffEntry> findAllByCommitIdAndOldPath(Long commitId, String oldPath);

  Set<DiffEntry> findAllByCommitId(Long commitId);

  Set<DiffEntry> findAllByCommitIdAndNewPath(Long commitId, String path);

  Set<DiffEntry> findAllByOldPath(String path);

  Set<DiffEntry> findAllByNewPath(String path);

}
