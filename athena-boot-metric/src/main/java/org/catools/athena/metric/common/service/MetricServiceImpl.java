package org.catools.athena.metric.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metric.common.mapper.MetricMapper;
import org.catools.athena.metric.common.mapper.MetricMapperService;
import org.catools.athena.metric.common.repository.ActionRepository;
import org.catools.athena.metric.common.repository.MetricInventoryProjection;
import org.catools.athena.metric.common.repository.MetricRepository;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.model.metrics.MetricInventoryDto;
import org.catools.athena.model.metrics.MetricSummaryDto;
import org.catools.athena.model.metrics.MetricTrendPointDto;
import org.catools.athena.common.utils.RetryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricServiceImpl implements MetricService {

  private final ActionRepository actionRepository;
  private final MetricRepository metricRepository;
  private final MetricMapper metricMapper;
  private final MetricMapperService metricMapperService;

  @Override
  @Transactional
  public MetricDto save(MetricDto entity) {
    Metric metric = metricMapper.metricDtoToMetric(entity);

    Action action = metric.getAction();
    Action savedAction = normalizeAction(action);

    metric.setAction(savedAction);

    Metric savedMetric = RetryUtils.retry(3, 1000, integer -> metricRepository.saveAndFlush(metric));
    return metricMapper.metricToMetricDto(savedMetric);
  }

  private Action normalizeAction(Action action) {
    try {
      return actionRepository.findByNameAndTypeAndTargetAndCommand(
              action.getName(), action.getType(), action.getTarget(), action.getCommand())
          .orElseGet(() -> actionRepository.saveAndFlush(action));
    } catch (DataIntegrityViolationException e) {
      // Concurrent insert hit the unique constraint — re-fetch the existing record
      return actionRepository.findByNameAndTypeAndTargetAndCommand(
              action.getName(), action.getType(), action.getTarget(), action.getCommand())
          .orElseThrow(() -> e);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MetricDto> getById(Long id) {
    return metricRepository.findById(id).map(metricMapper::metricToMetricDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MetricInventoryDto> getAll(
      final Pageable pageable,
      final String project,
      final String environment,
      final String actionName,
      final String actionType,
      final String actionTarget
  ) {
      final ResolvedMetricFilters filters = resolveFilters(project, environment, actionName, actionType, actionTarget);
      if (filters.isEmptyResult()) {
        return Page.empty(pageable);
      }

      final Page<MetricInventoryProjection> result = metricRepository.findInventory(
        filters.projectId(),
        filters.environmentId(),
        filters.actionName(),
        filters.actionType(),
        filters.actionTarget(),
        pageable
      );

      return new PageImpl<>(
        mapInventory(result.getContent()),
        pageable,
        result.getTotalElements()
      );
      }

      @Override
      @Transactional(readOnly = true)
      public MetricSummaryDto getSummary(
        final String project,
        final String environment,
        final String actionName,
        final String actionType,
        final String actionTarget,
        final Integer windowDays
      ) {
      final List<MetricInventoryDto> metrics = applyWindow(findMatchingMetrics(project, environment, actionName, actionType, actionTarget), MetricInventoryDto::getActionTime, windowDays);
      if (metrics.isEmpty()) {
        return new MetricSummaryDto()
          .setTotalCount(0L)
          .setUniqueActionCount(0L)
          .setAverageDuration(0.0)
          .setSlowestDuration(0L);
      }

      return new MetricSummaryDto()
        .setTotalCount((long) metrics.size())
        .setUniqueActionCount(metrics.stream().map(this::actionIdentity).distinct().count())
        .setAverageDuration(metrics.stream()
          .map(MetricInventoryDto::getDuration)
          .filter(duration -> duration != null)
          .mapToLong(Long::longValue)
          .average()
          .orElse(0.0))
        .setSlowestDuration(metrics.stream()
          .map(MetricInventoryDto::getDuration)
          .filter(duration -> duration != null)
          .max(Long::compareTo)
          .orElse(0L))
        .setLatestActionTime(metrics.stream()
          .map(MetricInventoryDto::getActionTime)
          .filter(actionTime -> actionTime != null)
          .max(Comparator.naturalOrder())
          .orElse(null));
      }

      @Override
      @Transactional(readOnly = true)
      public List<MetricTrendPointDto> getTrend(
        final String project,
        final String environment,
        final String actionName,
        final String actionType,
        final String actionTarget,
        final Integer windowDays
      ) {
      final List<MetricInventoryDto> metrics = findMatchingMetrics(project, environment, actionName, actionType, actionTarget);

      final List<MetricTrendPointDto> trendPoints = metrics.stream()
        .filter(metric -> metric.getActionTime() != null)
        .collect(Collectors.groupingBy(
          metric -> metric.getActionTime().atZone(ZoneOffset.UTC).toLocalDate(),
          TreeMap::new,
          Collectors.toList()))
        .entrySet()
        .stream()
        .map(entry -> toTrendPoint(entry.getKey(), entry.getValue()))
        .toList();

      return applyTrendWindow(trendPoints, windowDays);
      }

      private List<MetricTrendPointDto> applyTrendWindow(final List<MetricTrendPointDto> trendPoints, final Integer windowDays) {
      if (windowDays == null || windowDays < 1 || trendPoints.isEmpty()) {
        return trendPoints;
      }

      final Instant latestBucketStart = trendPoints.stream()
        .map(MetricTrendPointDto::getBucketStart)
        .filter(Objects::nonNull)
        .max(Comparator.naturalOrder())
        .orElse(null);

      if (latestBucketStart == null) {
        return trendPoints;
      }

      final Instant threshold = latestBucketStart.minus(Math.max(windowDays - 1L, 0L), ChronoUnit.DAYS);
      return trendPoints.stream()
        .filter(point -> point.getBucketStart() != null && !point.getBucketStart().isBefore(threshold))
        .toList();
      }

      private <T> List<T> applyWindow(final List<T> rows, final Function<T, Instant> timestampExtractor, final Integer windowDays) {
      if (windowDays == null || windowDays < 1 || rows.isEmpty()) {
        return rows;
      }

      final Instant latestTimestamp = rows.stream()
        .map(timestampExtractor)
        .filter(Objects::nonNull)
        .max(Comparator.naturalOrder())
        .orElse(null);

      if (latestTimestamp == null) {
        return List.of();
      }

      final Instant threshold = latestTimestamp.truncatedTo(ChronoUnit.DAYS).minus(Math.max(windowDays - 1L, 0L), ChronoUnit.DAYS);
      return rows.stream()
        .filter(row -> {
          final Instant timestamp = timestampExtractor.apply(row);
          return timestamp != null && !timestamp.isBefore(threshold);
        })
        .toList();
      }

      private MetricTrendPointDto toTrendPoint(final LocalDate bucketDate, final List<MetricInventoryDto> bucketMetrics) {
      return new MetricTrendPointDto()
        .setBucketStart(bucketDate.atStartOfDay().toInstant(ZoneOffset.UTC))
        .setMetricCount((long) bucketMetrics.size())
        .setAverageDuration(bucketMetrics.stream()
          .map(MetricInventoryDto::getDuration)
          .filter(duration -> duration != null)
          .mapToLong(Long::longValue)
          .average()
          .orElse(0.0))
        .setMaxDuration(bucketMetrics.stream()
          .map(MetricInventoryDto::getDuration)
          .filter(duration -> duration != null)
          .max(Long::compareTo)
          .orElse(0L));
      }

      private List<MetricInventoryDto> findMatchingMetrics(
        final String project,
        final String environment,
        final String actionName,
        final String actionType,
        final String actionTarget
      ) {
      final ResolvedMetricFilters filters = resolveFilters(project, environment, actionName, actionType, actionTarget);
      if (filters.isEmptyResult()) {
        return List.of();
      }

      return mapInventory(metricRepository.findInventory(
        filters.projectId(),
        filters.environmentId(),
        filters.actionName(),
        filters.actionType(),
        filters.actionTarget(),
        Pageable.unpaged()
      ).getContent());
      }

      private List<MetricInventoryDto> mapInventory(final List<MetricInventoryProjection> metrics) {
      final Map<Long, String> projectCodes = new HashMap<>();
      final Map<Long, String> environmentCodes = new HashMap<>();

      return metrics.stream()
        .map(metric -> new MetricInventoryDto()
          .setId(metric.getId())
          .setProject(projectCodes.computeIfAbsent(metric.getProjectId(), metricMapperService::getProjectCode))
          .setEnvironment(environmentCodes.computeIfAbsent(metric.getEnvironmentId(), metricMapperService::getEnvironmentCode))
          .setDuration(metric.getDuration())
          .setActionTime(metric.getActionTime())
          .setActionCategory(metric.getActionCategory())
          .setActionName(metric.getActionName())
          .setActionType(metric.getActionType())
          .setActionTarget(metric.getActionTarget()))
        .toList();
      }

      private ResolvedMetricFilters resolveFilters(
        final String project,
        final String environment,
        final String actionName,
        final String actionType,
        final String actionTarget
      ) {
    final Long projectId = resolveProjectId(project);
    if (project != null && !project.isBlank() && projectId == null) {
        return ResolvedMetricFilters.empty();
    }

    final Long environmentId = resolveEnvironmentId(project, environment);
    if (environment != null && !environment.isBlank() && environmentId == null) {
        return ResolvedMetricFilters.empty();
    }

      return new ResolvedMetricFilters(
        projectId,
        environmentId,
        normalizeFilter(actionName),
        normalizeFilter(actionType),
        normalizeFilter(actionTarget),
        false
      );
  }

  private Long resolveProjectId(final String project) {
    if (project == null || project.isBlank()) {
      return null;
    }

    try {
      return metricMapperService.getProjectId(project.trim());
    } catch (RecordNotFoundException exception) {
      return null;
    }
  }

  private Long resolveEnvironmentId(final String project, final String environment) {
    if (environment == null || environment.isBlank()) {
      return null;
    }

    if (project == null || project.isBlank()) {
      return null;
    }

    try {
      return metricMapperService.getEnvironmentId(project.trim(), environment.trim());
    } catch (RecordNotFoundException exception) {
      return null;
    }
  }

  private String normalizeFilter(final String value) {
    if (value == null) {
      return "";
    }

    final String trimmed = value.trim();
    return trimmed.isEmpty() ? "" : trimmed.toLowerCase();
  }

  private String actionIdentity(final MetricInventoryDto metric) {
    return String.join("|",
        metric.getActionName() == null ? "" : metric.getActionName(),
        metric.getActionType() == null ? "" : metric.getActionType(),
        metric.getActionTarget() == null ? "" : metric.getActionTarget());
  }

  private record ResolvedMetricFilters(
      Long projectId,
      Long environmentId,
      String actionName,
      String actionType,
      String actionTarget,
      boolean emptyResult
  ) {
    private static ResolvedMetricFilters empty() {
      return new ResolvedMetricFilters(null, null, "", "", "", true);
    }

    private boolean isEmptyResult() {
      return emptyResult;
    }
  }
}
