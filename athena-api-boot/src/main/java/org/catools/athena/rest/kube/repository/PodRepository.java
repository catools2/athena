package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface PodRepository extends JpaRepository<Pod, Long> {

  Set<Pod> findByNamespace(String namespace);

  Optional<Pod> findByNameAndNamespace(String name, String namespace);
}
