package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.git.common.model.DiffEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface DiffEntryRepository extends JpaRepository<DiffEntry, Long> {

  Optional<DiffEntry> findAllByCommitIdAndOldPathAndNewPathAndChangeTypeAndInsertedAndDeleted(
      Long commitId,
      String oldPath,
      String newPath,
      String changeType,
      int inserted,
      int deleted);

}
