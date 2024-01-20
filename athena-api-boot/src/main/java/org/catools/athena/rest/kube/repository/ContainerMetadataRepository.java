package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.ContainerMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface ContainerMetadataRepository extends JpaRepository<ContainerMetadata, Long> {
    Optional<ContainerMetadata> findByNameAndValue(String name, String value);
}
