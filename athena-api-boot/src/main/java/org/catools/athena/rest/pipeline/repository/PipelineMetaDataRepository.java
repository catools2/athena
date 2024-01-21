package org.catools.athena.rest.pipeline.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.pipeline.entity.PipelineMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PipelineMetaDataRepository extends JpaRepository<PipelineMetadata, Long> {
    Optional<PipelineMetadata> findByNameAndValue(String name, String value);
}
