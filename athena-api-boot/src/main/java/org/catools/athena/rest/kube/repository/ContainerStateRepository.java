package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.ContainerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ContainerStateRepository extends JpaRepository<ContainerState, Long> {
}
