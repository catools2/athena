package org.catools.athena.tms.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.model.tms.TestExecutionInventoryDto;
import org.catools.athena.model.tms.TestExecutionStatusCountDto;
import org.catools.athena.model.tms.TestExecutionSummaryDto;
import org.catools.athena.model.tms.TestExecutionTrendPointDto;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.common.mapper.TmsMapperService;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.TestCycleRepository;
import org.catools.athena.tms.common.repository.TestExecutionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestExecutionServiceImpl implements TestExecutionService {
  private static final String ITEM_CODE = "item code";
  private static final String CYCLE_CODE = "cycle code";

  private final TestExecutionRepository testExecutionRepository;
  private final TestCycleRepository testCycleRepository;
  private final ItemRepository itemRepository;
  private final TmsMapper tmsMapper;
  private final TmsMapperService tmsMapperService;

  @Override
  @Transactional
  @SuppressWarnings("java:S2201")
  public TestExecutionDto save(String cycleCode, TestExecutionDto entity) {
    TestCycle testCycle = testCycleRepository.findByCode(cycleCode).orElseThrow(() -> new RecordNotFoundException("cycle", "code", cycleCode));
    final TestExecution entityToSave = tmsMapper.testExecutionDtoToTestExecution(testCycle, entity);
    final TestExecution savedRecord = RetryUtils.retry(3, 1000, integer -> testExecutionRepository.saveAndFlush(entityToSave));
    return tmsMapper.testExecutionToTestExecutionDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TestExecutionDto> getById(Long id) {
    return testExecutionRepository.findById(id).map(tmsMapper::testExecutionToTestExecutionDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Set<TestExecutionDto> getAll(@Nullable String itemCode, @Nullable String cycleCode) {
    Optional<Item> itemByCode = itemRepository.findByCode(itemCode);
    Optional<TestCycle> cycleByCode = testCycleRepository.findByCode(cycleCode);

    if (itemCode != null && cycleCode != null) {
      Long itemId = itemByCode.orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();
      Long cycleId = cycleByCode.orElseThrow(() -> new EntityNotFoundException(CYCLE_CODE, cycleCode)).getId();
      return testExecutionRepository.findByCycleIdAndItemId(cycleId, itemId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    } else if (itemCode != null) {
      Long itemId = itemByCode.orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();
      return testExecutionRepository.findByItemId(itemId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    } else if (cycleCode != null) {
      Long cycleId = cycleByCode.orElseThrow(() -> new EntityNotFoundException(CYCLE_CODE, cycleCode)).getId();
      return testExecutionRepository.findByCycleId(cycleId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    } else {
      return testExecutionRepository.findAll().stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    }

  }

  @Override
  @Transactional(readOnly = true)
  public Page<TestExecutionInventoryDto> getAll(
      Pageable pageable,
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress
  ) {
    final List<ExecutionDashboardRow> filteredRows = getFilteredRows(project, version, cycleCode, itemCode, status, progress);
    final Comparator<ExecutionDashboardRow> comparator = getDashboardComparator(pageable.getSort());
    final List<TestExecutionInventoryDto> sortedRows = filteredRows.stream()
        .sorted(comparator)
        .map(this::toInventoryDto)
        .toList();

    final int startIndex = Math.min((int) pageable.getOffset(), sortedRows.size());
    final int endIndex = Math.min(startIndex + pageable.getPageSize(), sortedRows.size());
    return new PageImpl<>(sortedRows.subList(startIndex, endIndex), pageable, sortedRows.size());
  }

  @Override
  @Transactional(readOnly = true)
  public TestExecutionSummaryDto getSummary(
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress,
      @Nullable Integer windowDays
  ) {
    final List<ExecutionDashboardRow> filteredRows = applyWindow(getFilteredRows(project, version, cycleCode, itemCode, status, progress), ExecutionDashboardRow::activityTime, windowDays);
    final Map<String, Long> statusCounts = filteredRows.stream()
        .collect(Collectors.groupingBy(
            row -> StringUtils.defaultIfBlank(row.status(), "UNKNOWN"),
            LinkedHashMap::new,
            Collectors.counting()
        ));

    final List<TestExecutionStatusCountDto> statusBreakdown = statusCounts.entrySet().stream()
        .map(entry -> new TestExecutionStatusCountDto()
            .setStatus(entry.getKey())
            .setCount(entry.getValue()))
        .sorted(Comparator
            .comparing(TestExecutionStatusCountDto::getCount, Comparator.nullsLast(Comparator.reverseOrder()))
            .thenComparing(TestExecutionStatusCountDto::getStatus, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
        .toList();

    final long executedCount = filteredRows.stream().filter(row -> row.executedOn() != null).count();
    final Instant latestActivityTime = filteredRows.stream()
        .map(ExecutionDashboardRow::activityTime)
        .filter(java.util.Objects::nonNull)
        .max(Comparator.naturalOrder())
        .orElse(null);

    return new TestExecutionSummaryDto()
        .setTotalCount((long) filteredRows.size())
        .setCycleCount(filteredRows.stream().map(ExecutionDashboardRow::cycleCode).filter(StringUtils::isNotBlank).distinct().count())
        .setItemCount(filteredRows.stream().map(ExecutionDashboardRow::item).filter(StringUtils::isNotBlank).distinct().count())
        .setExecutedCount(executedCount)
        .setPendingCount((long) filteredRows.size() - executedCount)
        .setLatestActivityTime(latestActivityTime)
        .setStatusBreakdown(statusBreakdown);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TestExecutionTrendPointDto> getTrend(
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress,
      @Nullable Integer windowDays
  ) {
    final Map<Instant, List<ExecutionDashboardRow>> buckets = getFilteredRows(project, version, cycleCode, itemCode, status, progress).stream()
        .filter(row -> row.activityTime() != null)
        .collect(Collectors.groupingBy(
            row -> row.activityTime().truncatedTo(ChronoUnit.DAYS),
            java.util.TreeMap::new,
            Collectors.toList()
        ));

    final List<TestExecutionTrendPointDto> trendPoints = buckets.entrySet().stream()
        .map(entry -> new TestExecutionTrendPointDto()
            .setBucketStart(entry.getKey())
            .setExecutionCount((long) entry.getValue().size())
            .setExecutedCount(entry.getValue().stream().filter(row -> row.executedOn() != null).count())
            .setUniqueItemCount(entry.getValue().stream().map(ExecutionDashboardRow::item).filter(StringUtils::isNotBlank).distinct().count())
            .setUniqueCycleCount(entry.getValue().stream().map(ExecutionDashboardRow::cycleCode).filter(StringUtils::isNotBlank).distinct().count()))
        .toList();

    return applyTrendWindow(trendPoints, windowDays);
  }

  private List<TestExecutionTrendPointDto> applyTrendWindow(List<TestExecutionTrendPointDto> trendPoints, Integer windowDays) {
    if (windowDays == null || windowDays < 1 || trendPoints.isEmpty()) {
      return trendPoints;
    }

    final Instant latestBucketStart = trendPoints.stream()
        .map(TestExecutionTrendPointDto::getBucketStart)
        .filter(java.util.Objects::nonNull)
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

  private List<ExecutionDashboardRow> getFilteredRows(
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress
  ) {
    return buildDashboardRows().stream()
        .filter(row -> matchesFilter(row.project(), project))
        .filter(row -> matchesFilter(row.version(), version))
        .filter(row -> matchesFilter(row.cycleCode(), cycleCode))
        .filter(row -> matchesFilter(row.item(), itemCode))
        .filter(row -> matchesFilter(row.status(), status))
        .filter(row -> matchesFilter(resolveProgress(row.executedOn()), progress))
        .toList();
  }

  private String resolveProgress(@Nullable Instant executedOn) {
    return executedOn == null ? "PENDING" : "EXECUTED";
  }

  private List<ExecutionDashboardRow> buildDashboardRows() {
    final List<TestExecution> executions = testExecutionRepository.findAll();
    final Map<Long, VersionDto> versions = tmsMapperService.getVersions(executions.stream()
        .map(TestExecution::getCycle)
        .map(TestCycle::getVersionId)
        .filter(java.util.Objects::nonNull)
        .collect(Collectors.toSet()));
    final Map<Long, UserDto> users = tmsMapperService.getUsers(executions.stream()
        .map(TestExecution::getExecutorId)
        .filter(java.util.Objects::nonNull)
        .collect(Collectors.toSet()));
    final Map<Long, String> projects = executions.stream()
        .map(TestExecution::getItem)
        .map(Item::getProjectId)
        .filter(java.util.Objects::nonNull)
        .distinct()
        .collect(Collectors.toMap(Function.identity(), tmsMapperService::getProjectCode));

    return executions.stream()
        .map(execution -> {
          final TestCycle cycle = execution.getCycle();
          final Item item = execution.getItem();
          final VersionDto version = cycle == null || cycle.getVersionId() == null ? null : versions.get(cycle.getVersionId());
          final UserDto user = execution.getExecutorId() == null ? null : users.get(execution.getExecutorId());
          return new ExecutionDashboardRow(
              execution.getId(),
              cycle == null ? null : cycle.getCode(),
              cycle == null ? null : cycle.getName(),
              item == null || item.getProjectId() == null ? null : projects.get(item.getProjectId()),
              version == null ? null : version.getCode(),
              item == null ? null : item.getCode(),
              execution.getStatus() == null ? null : execution.getStatus().getCode(),
              user == null ? null : user.getUsername(),
              execution.getCreatedOn(),
              execution.getExecutedOn()
          );
        })
        .toList();
  }

  private Comparator<ExecutionDashboardRow> getDashboardComparator(Sort sort) {
    if (sort == null || sort.isUnsorted()) {
      return compareRows(ExecutionDashboardRow::activityTime, true);
    }

    Comparator<ExecutionDashboardRow> comparator = null;
    for (Sort.Order order : sort) {
      final Comparator<ExecutionDashboardRow> orderComparator = switch (order.getProperty()) {
        case "cycleCode" -> compareRows(ExecutionDashboardRow::cycleCode, order.isDescending());
        case "cycleName" -> compareRows(ExecutionDashboardRow::cycleName, order.isDescending());
        case "project" -> compareRows(ExecutionDashboardRow::project, order.isDescending());
        case "version" -> compareRows(ExecutionDashboardRow::version, order.isDescending());
        case "item" -> compareRows(ExecutionDashboardRow::item, order.isDescending());
        case "status" -> compareRows(ExecutionDashboardRow::status, order.isDescending());
        case "executor" -> compareRows(ExecutionDashboardRow::executor, order.isDescending());
        case "createdOn" -> compareRows(ExecutionDashboardRow::createdOn, order.isDescending());
        case "executedOn" -> compareRows(ExecutionDashboardRow::executedOn, order.isDescending());
        default -> compareRows(ExecutionDashboardRow::activityTime, order.isDescending());
      };
      comparator = comparator == null ? orderComparator : comparator.thenComparing(orderComparator);
    }

    return comparator == null ? compareRows(ExecutionDashboardRow::activityTime, true) : comparator;
  }

  private TestExecutionInventoryDto toInventoryDto(ExecutionDashboardRow row) {
    return new TestExecutionInventoryDto()
        .setId(row.id())
        .setCycleCode(row.cycleCode())
        .setCycleName(row.cycleName())
        .setProject(row.project())
        .setVersion(row.version())
        .setItem(row.item())
        .setStatus(row.status())
        .setExecutor(row.executor())
        .setCreatedOn(row.createdOn())
        .setExecutedOn(row.executedOn());
  }

  private static boolean matchesFilter(String value, String filter) {
    return StringUtils.isBlank(filter)
      || StringUtils.lowerCase(StringUtils.defaultString(value)).contains(StringUtils.lowerCase(filter.trim()));
  }

  private static <T extends Comparable<? super T>> Comparator<ExecutionDashboardRow> compareRows(
      Function<ExecutionDashboardRow, T> extractor,
      boolean descending
  ) {
    final Comparator<ExecutionDashboardRow> comparator = Comparator.comparing(extractor, Comparator.nullsLast(Comparator.naturalOrder()));
    return descending ? comparator.reversed() : comparator;
  }

  private record ExecutionDashboardRow(
      Long id,
      String cycleCode,
      String cycleName,
      String project,
      String version,
      String item,
      String status,
      String executor,
      Instant createdOn,
      Instant executedOn
  ) {
    private Instant activityTime() {
      return executedOn != null ? executedOn : createdOn;
    }
  }
}
