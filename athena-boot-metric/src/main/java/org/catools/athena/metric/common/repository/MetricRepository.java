package org.catools.athena.metric.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.metric.common.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface MetricRepository extends JpaRepository<Metric, Long> {
}
