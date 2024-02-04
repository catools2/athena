package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, Long> {

  Optional<StatusTransition> findByOccurredAndFromIdAndToIdAndItemId(Instant occurred, Long from, Long to, Long itemId);

  Set<StatusTransition> findAllByItemId(Long itemId);
}
