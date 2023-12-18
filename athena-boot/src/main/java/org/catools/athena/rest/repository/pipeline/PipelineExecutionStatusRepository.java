package org.catools.athena.rest.repository.pipeline;

import org.catools.athena.rest.entity.pipeline.PipelineExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface PipelineExecutionStatusRepository extends JpaRepository<PipelineExecutionStatus, Long> {
  Optional<PipelineExecutionStatus> findByName(String name);
}
