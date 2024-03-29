package org.catools.athena.kube.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.kube.common.model.PodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PodStatusRepository extends JpaRepository<PodStatus, Long> {
  Optional<PodStatus> findByNameAndPhaseAndMessageAndReason(String name, String phase, String message, String reason);
}
