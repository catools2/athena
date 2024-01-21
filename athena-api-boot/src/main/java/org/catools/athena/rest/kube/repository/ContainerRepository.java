package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface ContainerRepository extends JpaRepository<Container, Long> {
  Set<Container> findAllByPodId(Long podId);

  Optional<Container> findByNameAndPodId(String name, Long podId);
}
