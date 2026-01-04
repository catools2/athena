package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface PipelineExecutionStatusRepository extends JpaRepository<PipelineExecutionStatus, Long> {
  Optional<PipelineExecutionStatus> findByName(String name);
}
