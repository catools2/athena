package org.catools.athena.rest.pipeline.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.pipeline.entity.PipelineExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PipelineExecutionStatusRepository extends JpaRepository<PipelineExecutionStatus, Long> {
  Optional<PipelineExecutionStatus> findByName(String name);
}
