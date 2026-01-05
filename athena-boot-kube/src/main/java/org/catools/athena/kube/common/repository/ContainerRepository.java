package org.catools.athena.kube.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.kube.common.model.Container;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

@Hidden
public interface ContainerRepository extends JpaRepository<Container, Long> {
  Set<Container> findAllByPodId(Long podId);

  Optional<Container> findByNameAndPodId(String name, Long podId);
}
