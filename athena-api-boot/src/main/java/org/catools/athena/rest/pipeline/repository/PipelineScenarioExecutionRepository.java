package org.catools.athena.rest.pipeline.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.pipeline.entity.PipelineScenarioExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PipelineScenarioExecutionRepository extends JpaRepository<PipelineScenarioExecution, Long> {
}
