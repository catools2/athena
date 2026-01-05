package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface PipelineScenarioExecutionRepository extends JpaRepository<PipelineScenarioExecution, Long> {
}
