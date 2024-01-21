package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.ContainerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface ContainerStateRepository extends JpaRepository<ContainerState, Long> {
    Set<ContainerState> findAllByContainerId(Long containerId);

    Optional<ContainerState> findBySyncTimeAndTypeAndMessageAndValueAndContainerId(Instant syncTime, String type, String message, String value, Long containerId);

}
