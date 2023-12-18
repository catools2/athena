package org.catools.athena.rest.repository.pipeline;

import org.catools.athena.rest.entity.pipeline.PipelineMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface PipelineMetaDataRepository extends JpaRepository<PipelineMetadata, Long> {
  Optional<PipelineMetadata> findByNameAndValue(String name, String value);
}
