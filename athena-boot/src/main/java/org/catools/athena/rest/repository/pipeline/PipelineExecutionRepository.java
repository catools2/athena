package org.catools.athena.rest.repository.pipeline;

import org.catools.athena.rest.entity.pipeline.PipelineExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PipelineExecutionRepository extends JpaRepository<PipelineExecution, Long> {
}
