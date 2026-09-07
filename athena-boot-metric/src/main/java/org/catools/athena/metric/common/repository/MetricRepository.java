package org.catools.athena.metric.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.metric.common.entity.Metric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Hidden
public interface MetricRepository extends JpaRepository<Metric, Long> {

	@Query("""
			select
				m.id as id,
				m.projectId as projectId,
				m.environmentId as environmentId,
				m.duration as duration,
				m.actionTime as actionTime,
				a.category as actionCategory,
				a.name as actionName,
				a.type as actionType,
				a.target as actionTarget
			from Metric m
			join m.action a
			where (:projectId is null or m.projectId = :projectId)
				and (:environmentId is null or m.environmentId = :environmentId)
				and (:actionName = '' or lower(a.name) like concat('%', :actionName, '%'))
				and (:actionType = '' or lower(a.type) like concat('%', :actionType, '%'))
				and (:actionTarget = '' or lower(a.target) like concat('%', :actionTarget, '%'))
			""")
	Page<MetricInventoryProjection> findInventory(
			@Param("projectId") Long projectId,
			@Param("environmentId") Long environmentId,
			@Param("actionName") String actionName,
			@Param("actionType") String actionType,
			@Param("actionTarget") String actionTarget,
			Pageable pageable
	);
}
