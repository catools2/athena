package org.catools.athena.pipeline.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineInventoryDto;
import org.catools.athena.model.pipeline.PipelineSummaryDto;
import org.catools.athena.model.pipeline.PipelineTrendPointDto;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.catools.athena.pipeline.common.exception.PipelineNotExistsException;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.mapper.PipelineMapperService;
import org.catools.athena.pipeline.common.repository.PipelineMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineRepository;
import org.catools.athena.pipeline.common.repository.builders.PipelineRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.micrometer.common.util.StringUtils.isBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
  private final PipelineMetaDataRepository pipelineMetaDataRepository;

  private final PipelineRepositoryCustom pipelineRepositoryCustom;
  private final PipelineRepository pipelineRepository;

  private final PipelineMapperService pipelineMapperService;

  // Mappers
  private final PipelineMapper pipelineMapper;

  @Override
  @Transactional
  public PipelineDto saveOrUpdate(final PipelineDto entity) {
    log.debug("Saving entity: {}", entity);

    final Pipeline pipeline = pipelineMapper.pipelineDtoToPipeline(entity);

    final Set<PipelineMetadata> normalizedMetadata = normalizeMetadata(pipeline.getMetadata());

    final Pipeline pipelineToSave = pipelineRepository.findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc(pipeline.getEnvironmentId(), entity.getName(), entity.getNumber())
        .map(p -> {
          p.setName(pipeline.getName());
          p.setDescription(pipeline.getDescription());
          p.setNumber(pipeline.getNumber());
          p.setStartDate(pipeline.getStartDate());
          p.setEndDate(pipeline.getEndDate());
          p.setEnvironmentId(pipeline.getEnvironmentId());
          p.setVersionId(pipeline.getVersionId());
          p.setName(pipeline.getName());

          // Clear and set with normalized metadata
          p.getMetadata().clear();
          p.getMetadata().addAll(normalizedMetadata);

          return p;
        }).orElseGet(() -> {
          // For new pipeline, set normalized metadata
          pipeline.setMetadata(normalizedMetadata);
          return pipeline;
        });

    final Pipeline savedPipeline = RetryUtils.retry(3, 1000, integer -> pipelineRepository.saveAndFlush(pipelineToSave));
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  @Transactional
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Instant enddate) {
    final Pipeline pipelineToPatch = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotExistsException::new);
    pipelineToPatch.setEndDate(enddate);
    final Pipeline savedPipeline = RetryUtils.retry(3, 1000, integer -> pipelineRepository.saveAndFlush(pipelineToPatch));
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  public Optional<PipelineDto> getPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String projectCode, @Nullable final String versionCode, @Nullable final String environmentCode) {
    final Long versionId = pipelineMapperService.getVersionId(projectCode, versionCode);
    final Long environmentId = pipelineMapperService.getEnvironmentId(projectCode, environmentCode);
    return getLastPipeline(pipelineName, pipelineNumber, versionId, environmentId).map(pipelineMapper::pipelineToPipelineDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PipelineDto> getById(final Long id) {
    return pipelineRepository.findById(id).map(pipelineMapper::pipelineToPipelineDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PipelineInventoryDto> getAll(
      Pageable pageable,
      @Nullable String project,
      @Nullable String version,
      @Nullable String environment,
      @Nullable String name,
      @Nullable String number,
      @Nullable String state
  ) {
    final List<PipelineDashboardRow> filteredRows = getFilteredRows(project, version, environment, name, number, state);
    final Comparator<PipelineDashboardRow> comparator = getDashboardComparator(pageable.getSort());
    final List<PipelineInventoryDto> sortedRows = filteredRows.stream()
        .sorted(comparator)
        .map(this::toInventoryDto)
        .toList();

    final int startIndex = Math.min((int) pageable.getOffset(), sortedRows.size());
    final int endIndex = Math.min(startIndex + pageable.getPageSize(), sortedRows.size());
    return new PageImpl<>(sortedRows.subList(startIndex, endIndex), pageable, sortedRows.size());
  }

  @Override
  @Transactional(readOnly = true)
  public PipelineSummaryDto getSummary(
      @Nullable String project,
      @Nullable String version,
      @Nullable String environment,
      @Nullable String name,
      @Nullable String number,
      @Nullable String state,
      @Nullable Integer windowDays
  ) {
    final List<PipelineDashboardRow> filteredRows = applyWindow(getFilteredRows(project, version, environment, name, number, state), PipelineDashboardRow::startDate, windowDays);
    final var averageDuration = filteredRows.stream()
        .map(PipelineDashboardRow::duration)
        .filter(Objects::nonNull)
        .mapToLong(Long::longValue)
        .average();

    return new PipelineSummaryDto()
        .setTotalCount((long) filteredRows.size())
        .setUniqueNameCount(filteredRows.stream().map(PipelineDashboardRow::name).filter(StringUtils::isNotBlank).distinct().count())
        .setCompletedCount(filteredRows.stream().filter(row -> row.endDate() != null).count())
        .setInProgressCount(filteredRows.stream().filter(row -> row.endDate() == null).count())
        .setAverageDuration(averageDuration.isPresent() ? averageDuration.getAsDouble() : null)
        .setLatestStartTime(filteredRows.stream().map(PipelineDashboardRow::startDate).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null));
  }

  @Override
  @Transactional(readOnly = true)
  public List<PipelineTrendPointDto> getTrend(
      @Nullable String project,
      @Nullable String version,
      @Nullable String environment,
      @Nullable String name,
      @Nullable String number,
      @Nullable String state,
      @Nullable Integer windowDays
  ) {
    final Map<Instant, List<PipelineDashboardRow>> buckets = getFilteredRows(project, version, environment, name, number, state).stream()
        .filter(row -> row.startDate() != null)
        .collect(Collectors.groupingBy(
            row -> row.startDate().truncatedTo(ChronoUnit.DAYS),
            TreeMap::new,
            Collectors.toList()
        ));

    final List<PipelineTrendPointDto> trendPoints = buckets.entrySet().stream()
        .map(entry -> {
          final var averageDuration = entry.getValue().stream()
              .map(PipelineDashboardRow::duration)
              .filter(Objects::nonNull)
              .mapToLong(Long::longValue)
              .average();
          return new PipelineTrendPointDto()
              .setBucketStart(entry.getKey())
              .setPipelineCount((long) entry.getValue().size())
              .setCompletedCount(entry.getValue().stream().filter(row -> row.endDate() != null).count())
              .setAverageDuration(averageDuration.isPresent() ? averageDuration.getAsDouble() : null);
        })
        .toList();

    return applyTrendWindow(trendPoints, windowDays);
  }

  private List<PipelineTrendPointDto> applyTrendWindow(List<PipelineTrendPointDto> trendPoints, Integer windowDays) {
    if (windowDays == null || windowDays < 1 || trendPoints.isEmpty()) {
      return trendPoints;
    }

    final Instant latestBucketStart = trendPoints.stream()
        .map(PipelineTrendPointDto::getBucketStart)
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

  private <T> List<T> applyWindow(List<T> rows, Function<T, Instant> timestampExtractor, Integer windowDays) {
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

  private Optional<Pipeline> getLastPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final Long versionId, @Nullable final Long environmentId) {
    if (isBlank(pipelineName)) {
      return Optional.empty();
    }
    return pipelineRepositoryCustom.findLastPipeline(pipelineName, pipelineNumber, versionId, environmentId);
  }

  private Set<PipelineMetadata> normalizeMetadata(Set<PipelineMetadata> metadataSet) {
    final Set<PipelineMetadata> metadata = new HashSet<>();

    for (PipelineMetadata md : metadataSet) {
      // Read md from DB and if MD does not exist we create one
      PipelineMetadata normalizedMd =
          pipelineMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> {
                // Let the database absorb the race. A plain insert here would abort the surrounding
                // transaction on conflict, and a recovery lookup issued afterwards could never run --
                // PostgreSQL refuses every statement in an aborted transaction, which surfaced as an
                // opaque Hibernate "null identifier" assertion rather than the 23505 that caused it.
                pipelineMetaDataRepository.insertIfAbsent(md.getName(), md.getValue());
                return pipelineMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
                    .orElseThrow(() -> new IllegalStateException(
                        "Metadata (" + md.getName() + ") could not be read back after insert"));
              });

      metadata.add(normalizedMd);
    }

    return metadata;
  }

  private List<PipelineDashboardRow> getFilteredRows(
      @Nullable String project,
      @Nullable String version,
      @Nullable String environment,
      @Nullable String name,
      @Nullable String number,
      @Nullable String state
  ) {
    return buildDashboardRows().stream()
        .filter(row -> matchesFilter(row.project(), project))
        .filter(row -> matchesFilter(row.version(), version))
        .filter(row -> matchesFilter(row.environment(), environment))
        .filter(row -> matchesFilter(row.name(), name))
        .filter(row -> matchesFilter(row.number(), number))
        .filter(row -> matchesFilter(row.state(), state))
        .toList();
  }

  private List<PipelineDashboardRow> buildDashboardRows() {
    return pipelineRepository.findAll().stream()
        .map(pipelineMapper::pipelineToPipelineDto)
        .map(pipeline -> new PipelineDashboardRow(
            pipeline.getId(),
            pipeline.getProject(),
            pipeline.getVersion(),
            pipeline.getEnvironment(),
            pipeline.getName(),
            pipeline.getDescription(),
            pipeline.getNumber(),
            pipeline.getStartDate(),
            pipeline.getEndDate(),
            getDuration(pipeline.getStartDate(), pipeline.getEndDate()),
            pipeline.getEndDate() == null ? "RUNNING" : "COMPLETED"
        ))
        .toList();
  }

  private Comparator<PipelineDashboardRow> getDashboardComparator(Sort sort) {
    if (sort == null || sort.isUnsorted()) {
      return compareRows(PipelineDashboardRow::startDate, true);
    }

    Comparator<PipelineDashboardRow> comparator = null;
    for (Sort.Order order : sort) {
      final Comparator<PipelineDashboardRow> orderComparator = switch (order.getProperty()) {
        case "project" -> compareRows(PipelineDashboardRow::project, order.isDescending());
        case "version" -> compareRows(PipelineDashboardRow::version, order.isDescending());
        case "environment" -> compareRows(PipelineDashboardRow::environment, order.isDescending());
        case "name" -> compareRows(PipelineDashboardRow::name, order.isDescending());
        case "description" -> compareRows(PipelineDashboardRow::description, order.isDescending());
        case "number" -> compareRows(PipelineDashboardRow::number, order.isDescending());
        case "endDate" -> compareRows(PipelineDashboardRow::endDate, order.isDescending());
        case "duration" -> compareRows(PipelineDashboardRow::duration, order.isDescending());
        case "state" -> compareRows(PipelineDashboardRow::state, order.isDescending());
        default -> compareRows(PipelineDashboardRow::startDate, order.isDescending());
      };
      comparator = comparator == null ? orderComparator : comparator.thenComparing(orderComparator);
    }

    return comparator == null ? compareRows(PipelineDashboardRow::startDate, true) : comparator;
  }

  private PipelineInventoryDto toInventoryDto(PipelineDashboardRow row) {
    return new PipelineInventoryDto()
        .setId(row.id())
        .setProject(row.project())
        .setVersion(row.version())
        .setEnvironment(row.environment())
        .setName(row.name())
        .setDescription(row.description())
        .setNumber(row.number())
        .setStartDate(row.startDate())
        .setEndDate(row.endDate())
        .setDuration(row.duration())
        .setState(row.state());
  }

  private static boolean matchesFilter(String value, String filter) {
    return StringUtils.isBlank(filter)
      || StringUtils.lowerCase(StringUtils.defaultString(value)).contains(StringUtils.lowerCase(filter.trim()));
  }

  private static Long getDuration(Instant startDate, Instant endDate) {
    if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
      return null;
    }

    return Duration.between(startDate, endDate).toMillis();
  }

  private static <T extends Comparable<? super T>> Comparator<PipelineDashboardRow> compareRows(
      Function<PipelineDashboardRow, T> extractor,
      boolean descending
  ) {
    final Comparator<PipelineDashboardRow> comparator = Comparator.comparing(extractor, Comparator.nullsLast(Comparator.naturalOrder()));
    return descending ? comparator.reversed() : comparator;
  }

  private record PipelineDashboardRow(
      Long id,
      String project,
      String version,
      String environment,
      String name,
      String description,
      String number,
      Instant startDate,
      Instant endDate,
      Long duration,
      String state
  ) {
  }
}