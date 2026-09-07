package org.catools.athena.spec.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.apispec.ApiSpecDriftDto;
import org.catools.athena.model.apispec.ApiSpecFreshnessDto;
import org.catools.athena.model.apispec.ApiSpecInventoryDto;
import org.catools.athena.model.apispec.ApiSpecSummaryDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.catools.athena.spec.common.mapper.ApiSpecMapper;
import org.catools.athena.spec.common.repository.ApiPathMetadataRepository;
import org.catools.athena.spec.common.repository.ApiSpecInventoryProjection;
import org.catools.athena.spec.common.repository.ApiSpecMetadataRepository;
import org.catools.athena.spec.common.repository.ApiSpecRepository;
import org.catools.athena.spec.utils.ApiSpecUtils;
import org.catools.athena.spec.utils.MetadataPersistentHelper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiSpecServiceImpl implements ApiSpecService {

  private static final int WARNING_SYNC_WINDOW_DAYS = 7;
  private static final int STALE_SYNC_WINDOW_DAYS = 30;

  private final ApiSpecMetadataRepository apiSpecMetadataRepository;
  private final ApiPathMetadataRepository apiPathMetadataRepository;
  private final ApiSpecRepository apiSpecRepository;
  private final ApiSpecMapper apiSpecMapper;
  private final ApiSpecUtils apiSpecUtils;

  private final ProjectFeignClient projectFeignClient;

  @Override
  @Transactional
  public ApiSpecDto saveOrUpdate(final ApiSpecDto entity) {
    log.debug("Saving entity: {}", entity);
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(entity);

    ProjectDto projectByCode = Optional.ofNullable(projectFeignClient.search(entity.getProject()).body()).orElseThrow();

    final Set<ApiSpecMetadata> normalizedMetadata = MetadataPersistentHelper.normalizeMetadata(apiSpec.getMetadata(), apiSpecMetadataRepository);

    apiSpec.getPaths().forEach(p -> p.setMetadata(MetadataPersistentHelper.normalizeMetadata(p.getMetadata(), apiPathMetadataRepository)));

    final ApiSpec specToSave = apiSpecRepository.findByProjectIdAndName(projectByCode.getId(), entity.getName())
        .map(spec -> {
          spec.setTitle(apiSpec.getTitle());
          spec.setVersion(apiSpec.getVersion());
          spec.setLastSyncTime(apiSpec.getLastSyncTime());

          Set<ApiSpecMetadata> currentMetadata = spec.getMetadata();
          // Update metadata set by removing entries not present in the new normalized set
          currentMetadata.removeIf(metadata -> !normalizedMetadata.contains(metadata));
          // And adding any new entries that are not already present
          normalizedMetadata.stream()
              .filter(metadata -> !currentMetadata.contains(metadata))
              .forEach(currentMetadata::add);

          // We should not remove paths if they are not in the new spec to not loss tracking when building statistics
          apiSpec.getPaths()
              .stream()
              .filter(p1 -> apiSpecUtils.notContains(spec.getPaths(), p1))
              .forEach(spec::addPath);

          return spec;
        }).orElseGet(() -> {
          // For new pipeline, set normalized metadata
          apiSpec.setMetadata(normalizedMetadata);
          return apiSpec;
        });

    specToSave.getPaths().forEach(p -> p.setSpec(specToSave));
    ApiSpec savedApiSpec = apiSpecRepository.saveAndFlush(specToSave);

    return apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ApiSpecInventoryDto> getAll(
      final Pageable pageable,
      final String project,
      final String name,
      final String title,
      final String version,
      final String freshness
  ) {
    final List<ApiSpecInventoryDto> specs = findMatchingInventory(project, name, title, version, freshness).stream()
        .sorted(buildInventoryComparator(pageable.getSort()))
        .toList();

    return toPage(specs, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public ApiSpecSummaryDto getSummary(
      final String project,
      final String name,
      final String title,
      final String version,
      final String freshness,
      final Integer windowDays
  ) {
    if (windowDays != null && windowDays > 0) {
      return toWindowedSummary(applyWindow(findMatchingInventory(project, name, title, version, freshness), ApiSpecInventoryDto::getLastSyncTime, windowDays));
    }

    return toWindowedSummary(findMatchingInventory(project, name, title, version, freshness));
  }

  @Override
  @Transactional(readOnly = true)
  public ApiSpecFreshnessDto getFreshness(
      final String project,
      final String name,
      final String title,
      final String version,
      final String freshness,
      final Integer windowDays
  ) {
    final List<ApiSpecInventoryDto> specs = applyWindow(findMatchingInventory(project, name, title, version, freshness), ApiSpecInventoryDto::getLastSyncTime, windowDays);
    long freshCount = 0L;
    long agingCount = 0L;
    long staleCount = 0L;
    long unknownSyncCount = 0L;
    Instant latestSyncTime = null;

    for (ApiSpecInventoryDto spec : specs) {
      final DriftStatus driftStatus = toDriftStatus(spec.getLastSyncTime());
      switch (driftStatus) {
        case FRESH -> freshCount++;
        case AGING -> agingCount++;
        case STALE -> staleCount++;
        case UNKNOWN -> unknownSyncCount++;
      }

      if (spec.getLastSyncTime() != null && (latestSyncTime == null || spec.getLastSyncTime().isAfter(latestSyncTime))) {
        latestSyncTime = spec.getLastSyncTime();
      }
    }

    return new ApiSpecFreshnessDto()
        .setFreshCount(freshCount)
        .setAgingCount(agingCount)
        .setStaleCount(staleCount)
        .setUnknownSyncCount(unknownSyncCount)
        .setWarningWindowDays(WARNING_SYNC_WINDOW_DAYS)
        .setFreshnessWindowDays(STALE_SYNC_WINDOW_DAYS)
        .setLatestSyncTime(latestSyncTime);
  }

  private ApiSpecSummaryDto toWindowedSummary(final List<ApiSpecInventoryDto> specs) {
    Instant latestSyncTime = null;
    for (ApiSpecInventoryDto spec : specs) {
      if (spec.getLastSyncTime() != null && (latestSyncTime == null || spec.getLastSyncTime().isAfter(latestSyncTime))) {
        latestSyncTime = spec.getLastSyncTime();
      }
    }

    return new ApiSpecSummaryDto()
        .setSpecCount((long) specs.size())
        .setProjectCount(specs.stream()
            .map(ApiSpecInventoryDto::getProject)
            .filter(projectCode -> projectCode != null && !projectCode.isBlank())
            .distinct()
            .count())
        .setPathCount(specs.stream()
            .map(ApiSpecInventoryDto::getPathCount)
            .filter(pathCount -> pathCount != null)
            .mapToLong(Integer::longValue)
            .sum())
        .setStaleSpecCount(specs.stream().filter(spec -> toDriftStatus(spec.getLastSyncTime()) == DriftStatus.STALE).count())
        .setFreshnessWindowDays(STALE_SYNC_WINDOW_DAYS)
        .setLatestSyncTime(latestSyncTime);
  }

  private <T> List<T> applyWindow(final List<T> rows, final Function<T, Instant> timestampExtractor, final Integer windowDays) {
    if (windowDays == null || windowDays < 1 || rows.isEmpty()) {
      return rows;
    }

    final Instant latestTimestamp = rows.stream()
        .map(timestampExtractor)
        .filter(timestamp -> timestamp != null)
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

  @Override
  @Transactional(readOnly = true)
  public Page<ApiSpecDriftDto> getDrift(
      final Pageable pageable,
      final String project,
      final String name,
      final String title,
      final String version,
      final String freshness
  ) {
    final List<ApiSpecDriftDto> driftCandidates = findMatchingInventory(project, name, title, version, freshness)
        .stream()
        .map(this::toDriftDto)
        .filter(drift -> !DriftStatus.FRESH.name().equals(drift.getDriftStatus()))
        .sorted(buildDriftComparator(pageable.getSort()))
        .toList();

    return toPage(driftCandidates, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ApiSpecDto> getById(final Long id) {
    return apiSpecRepository.findById(id).map(getApiSpecToApiSpecDto());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ApiSpecDto> getByProjectCodeAndName(final String projectCode, final String name) {
    ProjectDto projectByCode = Optional.ofNullable(projectFeignClient.search(projectCode).body()).orElseThrow();
    return apiSpecRepository.findByProjectIdAndName(projectByCode.getId(), name).map(getApiSpecToApiSpecDto());
  }

  private Function<ApiSpec, ApiSpecDto> getApiSpecToApiSpecDto() {
    return apiSpecMapper::apiSpecToApiSpecDto;
  }

  private List<ApiSpecInventoryDto> findMatchingInventory(
      final String project,
      final String name,
      final String title,
      final String version,
      final String freshness
  ) {
    final Long projectId = resolveProjectId(project);
    if (project != null && !project.isBlank() && projectId == null) {
      return List.of();
    }

    final List<ApiSpecInventoryProjection> result = apiSpecRepository.findInventory(
        projectId,
        normalizeFilter(name),
        normalizeFilter(title),
        normalizeFilter(version),
        Pageable.unpaged()
    ).getContent();
    final Map<Long, String> projectCodes = new HashMap<>();

    return result.stream()
        .map(spec -> new ApiSpecInventoryDto()
            .setId(spec.getId())
            .setProject(projectCodes.computeIfAbsent(spec.getProjectId(), this::resolveProjectCode))
            .setName(spec.getName())
            .setTitle(spec.getTitle())
            .setVersion(spec.getVersion())
            .setFirstTimeSeen(spec.getFirstTimeSeen())
            .setLastSyncTime(spec.getLastSyncTime())
            .setPathCount(spec.getPathCount()))
        .filter(spec -> matchesFreshness(spec.getLastSyncTime(), freshness))
        .toList();
  }

  private boolean matchesFreshness(final Instant lastSyncTime, final String freshness) {
    if (freshness == null || freshness.isBlank()) {
      return true;
    }

    return toDriftStatus(lastSyncTime).name().equalsIgnoreCase(freshness.trim());
  }

  private Comparator<ApiSpecInventoryDto> buildInventoryComparator(final Sort sort) {
    Comparator<ApiSpecInventoryDto> comparator = null;

    for (Sort.Order order : sort) {
      Comparator<ApiSpecInventoryDto> nextComparator = switch (order.getProperty()) {
        case "project" -> Comparator.comparing(ApiSpecInventoryDto::getProject, Comparator.nullsLast(String::compareToIgnoreCase));
        case "title" -> Comparator.comparing(ApiSpecInventoryDto::getTitle, Comparator.nullsLast(String::compareToIgnoreCase));
        case "version" -> Comparator.comparing(ApiSpecInventoryDto::getVersion, Comparator.nullsLast(String::compareToIgnoreCase));
        case "firstTimeSeen" -> Comparator.comparing(ApiSpecInventoryDto::getFirstTimeSeen, Comparator.nullsLast(Comparator.naturalOrder()));
        case "lastSyncTime" -> Comparator.comparing(ApiSpecInventoryDto::getLastSyncTime, Comparator.nullsLast(Comparator.naturalOrder()));
        case "pathCount" -> Comparator.comparing(ApiSpecInventoryDto::getPathCount, Comparator.nullsLast(Comparator.naturalOrder()));
        default -> Comparator.comparing(ApiSpecInventoryDto::getName, Comparator.nullsLast(String::compareToIgnoreCase));
      };

      if (order.isDescending()) {
        nextComparator = nextComparator.reversed();
      }

      comparator = comparator == null ? nextComparator : comparator.thenComparing(nextComparator);
    }

    return comparator == null
        ? Comparator.comparing(ApiSpecInventoryDto::getName, Comparator.nullsLast(String::compareToIgnoreCase))
        : comparator;
  }

  private <T> Page<T> toPage(final List<T> rows, final Pageable pageable) {
    final int start = (int) pageable.getOffset();
    if (start >= rows.size()) {
      return new PageImpl<>(List.of(), pageable, rows.size());
    }

    final int end = Math.min(start + pageable.getPageSize(), rows.size());
    return new PageImpl<>(rows.subList(start, end), pageable, rows.size());
  }

  private Long resolveProjectId(final String projectCode) {
    if (projectCode == null || projectCode.isBlank()) {
      return null;
    }

    return Optional.ofNullable(projectFeignClient.search(projectCode.trim()).body())
        .map(ProjectDto::getId)
        .orElse(null);
  }

  private String resolveProjectCode(final Long projectId) {
    return Optional.ofNullable(projectFeignClient.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElse(String.valueOf(projectId));
  }

  private String normalizeFilter(final String value) {
    if (value == null) {
      return "";
    }

    final String trimmed = value.trim();
    return trimmed.isEmpty() ? "" : trimmed.toLowerCase();
  }

  private ApiSpecDriftDto toDriftDto(final ApiSpecInventoryDto spec) {
    return new ApiSpecDriftDto()
        .setId(spec.getId())
        .setProject(spec.getProject())
        .setName(spec.getName())
        .setTitle(spec.getTitle())
        .setVersion(spec.getVersion())
      .setPathCount(spec.getPathCount() == null ? null : Long.valueOf(spec.getPathCount()))
        .setLastSyncTime(spec.getLastSyncTime())
        .setSyncAgeDays(getSyncAgeDays(spec.getLastSyncTime()))
        .setDriftStatus(toDriftStatus(spec.getLastSyncTime()).name());
  }

  private DriftStatus toDriftStatus(final Instant lastSyncTime) {
    if (lastSyncTime == null) {
      return DriftStatus.UNKNOWN;
    }

    final long syncAgeDays = getSyncAgeDays(lastSyncTime);
    if (syncAgeDays > STALE_SYNC_WINDOW_DAYS) {
      return DriftStatus.STALE;
    }
    if (syncAgeDays > WARNING_SYNC_WINDOW_DAYS) {
      return DriftStatus.AGING;
    }
    return DriftStatus.FRESH;
  }

  private long getSyncAgeDays(final Instant lastSyncTime) {
    if (lastSyncTime == null) {
      return 0L;
    }

    return Math.max(0L, Duration.between(lastSyncTime, Instant.now()).toDays());
  }

  private Comparator<ApiSpecDriftDto> buildDriftComparator(final Sort sort) {
    Comparator<ApiSpecDriftDto> comparator = null;

    for (Sort.Order order : sort) {
      Comparator<ApiSpecDriftDto> nextComparator = switch (order.getProperty()) {
        case "lastSyncTime" -> Comparator.comparing(
            ApiSpecDriftDto::getLastSyncTime,
            Comparator.nullsLast(Comparator.naturalOrder()));
        case "pathCount" -> Comparator.comparing(
            ApiSpecDriftDto::getPathCount,
            Comparator.nullsLast(Comparator.naturalOrder()));
        case "name" -> Comparator.comparing(
            ApiSpecDriftDto::getName,
            Comparator.nullsLast(String::compareToIgnoreCase));
        case "project" -> Comparator.comparing(
            ApiSpecDriftDto::getProject,
            Comparator.nullsLast(String::compareToIgnoreCase));
        case "syncAgeDays", "ageInDays" -> Comparator.comparing(
            ApiSpecDriftDto::getSyncAgeDays,
            Comparator.nullsLast(Comparator.naturalOrder()));
        default -> Comparator.comparing(ApiSpecDriftDto::getId, Comparator.nullsLast(Comparator.naturalOrder()));
      };

      if (order.isDescending()) {
        nextComparator = nextComparator.reversed();
      }

      comparator = comparator == null ? nextComparator : comparator.thenComparing(nextComparator);
    }

    return comparator == null
        ? Comparator.comparing(ApiSpecDriftDto::getSyncAgeDays, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
        : comparator;
  }

  private enum DriftStatus {
    FRESH,
    AGING,
    STALE,
    UNKNOWN
  }
}
