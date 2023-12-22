package org.catools.athena.rest.pipeline.repository;

import org.catools.athena.rest.pipeline.entity.PipelineScenarioExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PipelineScenarioExecutionRepository extends JpaRepository<PipelineScenarioExecution, Long> {
}
