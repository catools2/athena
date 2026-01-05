package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.PipelineExecution;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface PipelineExecutionRepository extends JpaRepository<PipelineExecution, Long> {
}
