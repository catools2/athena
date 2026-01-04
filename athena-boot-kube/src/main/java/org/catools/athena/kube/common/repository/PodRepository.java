package org.catools.athena.kube.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.kube.common.model.Pod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

@Hidden
public interface PodRepository extends JpaRepository<Pod, Long> {

  Set<Pod> findByNamespace(String namespace);

  Set<Pod> findByProjectIdAndNamespace(Long projectId, String namespace);

  Optional<Pod> findByNameAndNamespace(String name, String namespace);

  Optional<Pod> findByUid(String podUID);
}
