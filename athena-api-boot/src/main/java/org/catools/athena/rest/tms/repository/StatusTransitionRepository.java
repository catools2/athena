package org.catools.athena.rest.tms.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.tms.entity.StatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Transactional
@Hidden
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, Long> {

    Optional<StatusTransition> findByOccurredAndFromIdAndToIdAndItemId(LocalDateTime occurred, Long from, Long to, Long itemId);

    Set<StatusTransition> findAllByItemId(Long itemId);
}
