package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.git.common.entity.GitRepository;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.GitRepositoryInventoryDto;
import org.catools.athena.model.git.GitRepositorySummaryDto;
import org.catools.athena.model.git.GitRepositoryTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
public class GitRepositoryServiceImpl implements GitRepositoryService {
  private static final int WARNING_WINDOW_DAYS = 7;
  private static final int FRESHNESS_WINDOW_DAYS = 30;

  private final GitRepositoryRepository gitRepositoryRepository;
  private final GitMapper gitMapper;

  @Override
  @Transactional(readOnly = true)
  public Optional<GitRepositoryDto> getById(Long id) {
    return gitRepositoryRepository.findById(id).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<GitRepositoryDto> findByNameOrUrl(final String keyword) {
    return gitRepositoryRepository.findByNameOrUrl(keyword, keyword).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  @Transactional
  public GitRepositoryDto saveOrUpdate(GitRepositoryDto entity) {
    log.debug("Saving entity: {}", entity);
    final GitRepository entityToSave = gitRepositoryRepository.findByNameOrUrl(entity.getName(), entity.getUrl()).map(repo -> {
      repo.setName(entity.getName());
      repo.setUrl(entity.getUrl());
      repo.setLastSync(entity.getLastSync());
      return repo;
    }).orElseGet(() -> gitMapper.gitRepositoryDtoToGitRepository(entity));

    final GitRepository savedEntity = RetryUtils.retry(3, 1000, integer -> gitRepositoryRepository.saveAndFlush(entityToSave));
    return gitMapper.gitRepositoryToGitRepositoryDto(savedEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<GitRepositoryInventoryDto> getAll(Pageable pageable, String keyword, String host, String freshness) {
    final List<GitRepositoryDashboardRow> filteredRows = getFilteredRows(keyword, host, freshness);
    final Comparator<GitRepositoryDashboardRow> comparator = getDashboardComparator(pageable.getSort());
    final List<GitRepositoryInventoryDto> sortedRows = filteredRows.stream()
        .sorted(comparator)
        .map(this::toInventoryDto)
        .toList();

    final int startIndex = Math.min((int) pageable.getOffset(), sortedRows.size());
    final int endIndex = Math.min(startIndex + pageable.getPageSize(), sortedRows.size());
    return new PageImpl<>(sortedRows.subList(startIndex, endIndex), pageable, sortedRows.size());
  }

  @Override
  @Transactional(readOnly = true)
  public GitRepositorySummaryDto getSummary(String keyword, String host, String freshness, Integer windowDays) {
    final List<GitRepositoryDashboardRow> filteredRows = applyWindow(getFilteredRows(keyword, host, freshness), GitRepositoryDashboardRow::lastSync, windowDays);
    return new GitRepositorySummaryDto()
        .setTotalCount((long) filteredRows.size())
        .setHostCount(filteredRows.stream().map(GitRepositoryDashboardRow::host).filter(StringUtils::isNotBlank).distinct().count())
        .setFreshCount(filteredRows.stream().filter(row -> "FRESH".equals(row.freshnessStatus())).count())
        .setAgingCount(filteredRows.stream().filter(row -> "AGING".equals(row.freshnessStatus())).count())
        .setStaleCount(filteredRows.stream().filter(row -> "STALE".equals(row.freshnessStatus())).count())
        .setUnknownSyncCount(filteredRows.stream().filter(row -> "UNKNOWN".equals(row.freshnessStatus())).count())
        .setWarningWindowDays(WARNING_WINDOW_DAYS)
        .setFreshnessWindowDays(FRESHNESS_WINDOW_DAYS)
        .setLatestSyncTime(filteredRows.stream().map(GitRepositoryDashboardRow::lastSync).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null));
  }

  @Override
  @Transactional(readOnly = true)
  public List<GitRepositoryTrendPointDto> getTrend(String keyword, String host, String freshness, Integer windowDays) {
    final Map<Instant, List<GitRepositoryDashboardRow>> buckets = getFilteredRows(keyword, host, freshness).stream()
        .filter(row -> row.lastSync() != null)
        .collect(Collectors.groupingBy(
            row -> row.lastSync().truncatedTo(ChronoUnit.DAYS),
            TreeMap::new,
            Collectors.toList()
        ));

    final List<GitRepositoryTrendPointDto> trendPoints = buckets.entrySet().stream()
        .map(entry -> new GitRepositoryTrendPointDto()
            .setBucketStart(entry.getKey())
            .setRepositoryCount((long) entry.getValue().size())
            .setUniqueHostCount(entry.getValue().stream().map(GitRepositoryDashboardRow::host).filter(StringUtils::isNotBlank).distinct().count()))
        .toList();

    return applyTrendWindow(trendPoints, windowDays);
  }

  private List<GitRepositoryTrendPointDto> applyTrendWindow(List<GitRepositoryTrendPointDto> trendPoints, Integer windowDays) {
    if (windowDays == null || windowDays < 1 || trendPoints.isEmpty()) {
      return trendPoints;
    }

    final Instant latestBucketStart = trendPoints.stream()
        .map(GitRepositoryTrendPointDto::getBucketStart)
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

  private List<GitRepositoryDashboardRow> getFilteredRows(String keyword, String host, String freshness) {
    return gitRepositoryRepository.findAll().stream()
        .map(gitMapper::gitRepositoryToGitRepositoryDto)
        .map(this::toDashboardRow)
        .filter(row -> matchesKeyword(row, keyword))
        .filter(row -> matchesFilter(row.host(), host))
        .filter(row -> matchesFilter(row.freshnessStatus(), freshness))
        .toList();
  }

  private GitRepositoryDashboardRow toDashboardRow(GitRepositoryDto repository) {
    final String freshnessStatus = resolveFreshnessStatus(repository.getLastSync());
    return new GitRepositoryDashboardRow(
        repository.getId(),
        repository.getName(),
        repository.getUrl(),
        resolveHost(repository.getUrl()),
        repository.getLastSync(),
        getSyncAgeDays(repository.getLastSync()),
        freshnessStatus);
  }

  private Comparator<GitRepositoryDashboardRow> getDashboardComparator(Sort sort) {
    if (sort == null || sort.isUnsorted()) {
      return compareRows(GitRepositoryDashboardRow::lastSync, true);
    }

    Comparator<GitRepositoryDashboardRow> comparator = null;
    for (Sort.Order order : sort) {
      final Comparator<GitRepositoryDashboardRow> orderComparator = switch (order.getProperty()) {
        case "name" -> compareRows(GitRepositoryDashboardRow::name, order.isDescending());
        case "url" -> compareRows(GitRepositoryDashboardRow::url, order.isDescending());
        case "host" -> compareRows(GitRepositoryDashboardRow::host, order.isDescending());
        case "syncAgeDays" -> compareRows(GitRepositoryDashboardRow::syncAgeDays, order.isDescending());
        case "freshnessStatus" -> compareRows(GitRepositoryDashboardRow::freshnessStatus, order.isDescending());
        default -> compareRows(GitRepositoryDashboardRow::lastSync, order.isDescending());
      };
      comparator = comparator == null ? orderComparator : comparator.thenComparing(orderComparator);
    }

    return comparator == null ? compareRows(GitRepositoryDashboardRow::lastSync, true) : comparator;
  }

  private GitRepositoryInventoryDto toInventoryDto(GitRepositoryDashboardRow row) {
    return new GitRepositoryInventoryDto()
        .setId(row.id())
        .setName(row.name())
        .setUrl(row.url())
        .setHost(row.host())
        .setLastSync(row.lastSync())
        .setSyncAgeDays(row.syncAgeDays())
        .setFreshnessStatus(row.freshnessStatus());
  }

  private static boolean matchesKeyword(GitRepositoryDashboardRow row, String keyword) {
    if (StringUtils.isBlank(keyword)) {
      return true;
    }

    final String normalizedKeyword = keyword.trim();
    return StringUtils.containsIgnoreCase(StringUtils.defaultString(row.name()), normalizedKeyword)
        || StringUtils.containsIgnoreCase(StringUtils.defaultString(row.url()), normalizedKeyword)
        || StringUtils.containsIgnoreCase(StringUtils.defaultString(row.host()), normalizedKeyword);
  }

  private static boolean matchesFilter(String value, String filter) {
    return StringUtils.isBlank(filter) || StringUtils.containsIgnoreCase(StringUtils.defaultString(value), filter.trim());
  }

  private static String resolveFreshnessStatus(Instant lastSync) {
    if (lastSync == null) {
      return "UNKNOWN";
    }

    final long syncAgeDays = ChronoUnit.DAYS.between(lastSync.truncatedTo(ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.DAYS));
    if (syncAgeDays > FRESHNESS_WINDOW_DAYS) {
      return "STALE";
    }
    if (syncAgeDays > WARNING_WINDOW_DAYS) {
      return "AGING";
    }
    return "FRESH";
  }

  private static Long getSyncAgeDays(Instant lastSync) {
    if (lastSync == null) {
      return null;
    }
    return ChronoUnit.DAYS.between(lastSync.truncatedTo(ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.DAYS));
  }

  private static String resolveHost(String url) {
    if (StringUtils.isBlank(url)) {
      return null;
    }

    try {
      final URI uri = URI.create(url);
      final String host = uri.getHost();
      return host == null ? null : StringUtils.removeStartIgnoreCase(host, "www.");
    } catch (Exception ignored) {
      if (url.contains("@") && url.contains(":")) {
        final int atIndex = url.indexOf('@');
        final int colonIndex = url.indexOf(':', atIndex + 1);
        if (atIndex >= 0 && colonIndex > atIndex) {
          return url.substring(atIndex + 1, colonIndex);
        }
      }
      return null;
    }
  }

  private static <T extends Comparable<? super T>> Comparator<GitRepositoryDashboardRow> compareRows(
      Function<GitRepositoryDashboardRow, T> extractor,
      boolean descending
  ) {
    final Comparator<GitRepositoryDashboardRow> comparator = Comparator.comparing(extractor, Comparator.nullsLast(Comparator.naturalOrder()));
    return descending ? comparator.reversed() : comparator;
  }

  private record GitRepositoryDashboardRow(
      Long id,
      String name,
      String url,
      String host,
      Instant lastSync,
      Long syncAgeDays,
      String freshnessStatus
  ) {
  }
}
