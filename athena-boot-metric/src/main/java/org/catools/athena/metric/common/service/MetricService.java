package org.catools.athena.metric.common.service;


import org.catools.athena.common.service.SaveService;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.model.metrics.MetricInventoryDto;
import org.catools.athena.model.metrics.MetricSummaryDto;
import org.catools.athena.model.metrics.MetricTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MetricService extends SaveService<MetricDto> {

	Page<MetricInventoryDto> getAll(
			Pageable pageable,
			String project,
			String environment,
			String actionName,
			String actionType,
			String actionTarget
	);

	MetricSummaryDto getSummary(
			String project,
			String environment,
			String actionName,
			String actionType,
			String actionTarget,
			Integer windowDays
	);

	List<MetricTrendPointDto> getTrend(
			String project,
			String environment,
			String actionName,
			String actionType,
			String actionTarget,
			Integer windowDays
	);

}