package org.catools.athena.tms.controller;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.model.tms.TestExecutionInventoryDto;
import org.catools.athena.model.tms.TestExecutionSummaryDto;
import org.catools.athena.model.tms.TestExecutionTrendPointDto;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.feign.PageResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestExecutionControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    final TypedResponse<Void> response = testExecutionFeignClient.saveOrUpdate(cycle.getCode(), executionDto);
    assertThat(response.status(), equalTo(201));
    String location = response.headers().get("location").stream().findFirst().orElseThrow();
    assertThat(location, notNullValue());
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), executionDto);

    // Repeat The same save to simulate case when the entity already exists
    final TypedResponse<Void> savedResponse = testExecutionFeignClient.saveOrUpdate(cycle.getCode(), executionDto);
    assertThat(savedResponse.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(savedResponse);
    assertThat(entityId, notNullValue());

    final TypedResponse<TestExecutionDto> getIdResponse = testExecutionFeignClient.getById(entityId);
    assertThat(getIdResponse.status(), equalTo(200));
    assertThat(getIdResponse.body(), notNullValue());
    assertThat(getIdResponse.body().getId(), equalTo(entityId));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidItemCodeAndCycleCodeProvided() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(3), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e3);

    final TypedResponse<Set<TestExecutionDto>> response = testExecutionFeignClient.getAll(item.getCode(), cycle.getCode());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), greaterThanOrEqualTo(3));
    assertThat(response.body().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidItemCodeProvidedWithNoCycleCode() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(3), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e3);

    final TypedResponse<Set<TestExecutionDto>> response = testExecutionFeignClient.getAll(item.getCode(), null);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), greaterThanOrEqualTo(3));
    assertThat(response.body().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCycleCodeProvidedWithNoItemCode() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(3), userDto));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), e3);

    final TypedResponse<Set<TestExecutionDto>> response = testExecutionFeignClient.getAll(null, cycle.getCode());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), greaterThanOrEqualTo(3));
    assertThat(response.body().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(5)
  void shallReturnSummaryForQualityWorkspaceFilters() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    cycle.setTestExecutions(Set.of());
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto completedOne = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    completedOne.setCreatedOn(Instant.parse("2026-05-11T09:00:00.000Z"));
    completedOne.setExecutedOn(Instant.parse("2026-05-11T10:15:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), completedOne);

    final TestExecutionDto pending = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    pending.setCreatedOn(Instant.parse("2026-05-12T09:30:00.000Z"));
    pending.setExecutedOn(null);
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), pending);

    final TestExecutionDto completedTwo = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(3), userDto));
    completedTwo.setCreatedOn(Instant.parse("2026-05-12T11:00:00.000Z"));
    completedTwo.setExecutedOn(Instant.parse("2026-05-12T12:45:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), completedTwo);

    final TypedResponse<TestExecutionSummaryDto> response = testExecutionFeignClient.getSummary(
        projectDto.getCode(),
        versionDto.getCode(),
        cycle.getCode(),
        item.getCode(),
        null,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    final TestExecutionSummaryDto summary = response.body();
    assertThat(summary, notNullValue());
    assertThat(summary.getTotalCount(), equalTo(3L));
    assertThat(summary.getCycleCount(), equalTo(1L));
    assertThat(summary.getItemCount(), equalTo(1L));
    assertThat(summary.getExecutedCount(), equalTo(2L));
    assertThat(summary.getPendingCount(), equalTo(1L));
    assertThat(summary.getLatestActivityTime(), equalTo(Instant.parse("2026-05-12T12:45:00.000Z")));
    assertThat(summary.getStatusBreakdown().size(), equalTo(3));
    assertThat(summary.getStatusBreakdown().stream().anyMatch(entry -> statuses.get(1).getCode().equals(entry.getStatus()) && entry.getCount() == 1L), equalTo(true));
    assertThat(summary.getStatusBreakdown().stream().anyMatch(entry -> statuses.get(2).getCode().equals(entry.getStatus()) && entry.getCount() == 1L), equalTo(true));
    assertThat(summary.getStatusBreakdown().stream().anyMatch(entry -> statuses.get(3).getCode().equals(entry.getStatus()) && entry.getCount() == 1L), equalTo(true));
  }

  @Test
  @Order(5)
  void shallReturnSummaryForQualityWorkspaceFiltersWithinWindow() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    cycle.setTestExecutions(Set.of());
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto completedOne = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    completedOne.setCreatedOn(Instant.parse("2026-05-11T09:00:00.000Z"));
    completedOne.setExecutedOn(Instant.parse("2026-05-11T10:15:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), completedOne);

    final TestExecutionDto pending = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    pending.setCreatedOn(Instant.parse("2026-05-12T09:30:00.000Z"));
    pending.setExecutedOn(null);
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), pending);

    final TestExecutionDto completedTwo = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(3), userDto));
    completedTwo.setCreatedOn(Instant.parse("2026-05-12T11:00:00.000Z"));
    completedTwo.setExecutedOn(Instant.parse("2026-05-12T12:45:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), completedTwo);

    final TypedResponse<TestExecutionSummaryDto> response = testExecutionFeignClient.getSummary(
        projectDto.getCode(),
        versionDto.getCode(),
        cycle.getCode(),
        item.getCode(),
        null,
      null,
        1);

    assertThat(response.status(), equalTo(200));
    final TestExecutionSummaryDto summary = response.body();
    assertThat(summary, notNullValue());
    assertThat(summary.getTotalCount(), equalTo(2L));
    assertThat(summary.getCycleCount(), equalTo(1L));
    assertThat(summary.getItemCount(), equalTo(1L));
    assertThat(summary.getExecutedCount(), equalTo(1L));
    assertThat(summary.getPendingCount(), equalTo(1L));
    assertThat(summary.getLatestActivityTime(), equalTo(Instant.parse("2026-05-12T12:45:00.000Z")));
    assertThat(summary.getStatusBreakdown().size(), equalTo(2));
    assertThat(summary.getStatusBreakdown().stream().anyMatch(entry -> statuses.get(2).getCode().equals(entry.getStatus()) && entry.getCount() == 1L), equalTo(true));
    assertThat(summary.getStatusBreakdown().stream().anyMatch(entry -> statuses.get(3).getCode().equals(entry.getStatus()) && entry.getCount() == 1L), equalTo(true));
  }

  @Test
  @Order(6)
  void shallReturnTrendBucketsForQualityWorkspaceFilters() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    cycle.setTestExecutions(Set.of());
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto firstBucketExecution = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    firstBucketExecution.setCreatedOn(Instant.parse("2026-05-11T08:00:00.000Z"));
    firstBucketExecution.setExecutedOn(Instant.parse("2026-05-11T09:00:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), firstBucketExecution);

    final TestExecutionDto secondBucketPending = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    secondBucketPending.setCreatedOn(Instant.parse("2026-05-12T08:30:00.000Z"));
    secondBucketPending.setExecutedOn(null);
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), secondBucketPending);

    final TestExecutionDto secondBucketCompleted = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(3), userDto));
    secondBucketCompleted.setCreatedOn(Instant.parse("2026-05-12T11:30:00.000Z"));
    secondBucketCompleted.setExecutedOn(Instant.parse("2026-05-12T11:45:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), secondBucketCompleted);

    final TypedResponse<List<TestExecutionTrendPointDto>> response = testExecutionFeignClient.getTrend(
        projectDto.getCode(),
        versionDto.getCode(),
        cycle.getCode(),
        item.getCode(),
        null,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    final List<TestExecutionTrendPointDto> trend = response.body();
    assertThat(trend, notNullValue());
    assertThat(trend.size(), equalTo(2));
    assertThat(trend.get(0).getBucketStart(), equalTo(Instant.parse("2026-05-11T00:00:00.000Z")));
    assertThat(trend.get(0).getExecutionCount(), equalTo(1L));
    assertThat(trend.get(0).getExecutedCount(), equalTo(1L));
    assertThat(trend.get(0).getUniqueItemCount(), equalTo(1L));
    assertThat(trend.get(0).getUniqueCycleCount(), equalTo(1L));
    assertThat(trend.get(1).getBucketStart(), equalTo(Instant.parse("2026-05-12T00:00:00.000Z")));
    assertThat(trend.get(1).getExecutionCount(), equalTo(2L));
    assertThat(trend.get(1).getExecutedCount(), equalTo(1L));
    assertThat(trend.get(1).getUniqueItemCount(), equalTo(1L));
    assertThat(trend.get(1).getUniqueCycleCount(), equalTo(1L));
  }

  @Test
  @Order(6)
  void shallReturnTrendBucketsForQualityWorkspaceWithinWindow() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    cycle.setTestExecutions(Set.of());
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto firstBucketExecution = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    firstBucketExecution.setCreatedOn(Instant.parse("2026-05-11T08:00:00.000Z"));
    firstBucketExecution.setExecutedOn(Instant.parse("2026-05-11T09:00:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), firstBucketExecution);

    final TestExecutionDto secondBucketPending = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    secondBucketPending.setCreatedOn(Instant.parse("2026-05-12T08:30:00.000Z"));
    secondBucketPending.setExecutedOn(null);
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), secondBucketPending);

    final TypedResponse<List<TestExecutionTrendPointDto>> response = testExecutionFeignClient.getTrend(
        projectDto.getCode(),
        versionDto.getCode(),
        cycle.getCode(),
        item.getCode(),
        null,
      null,
        1);

    assertThat(response.status(), equalTo(200));
    final List<TestExecutionTrendPointDto> trend = response.body();
    assertThat(trend, notNullValue());
    assertThat(trend.size(), equalTo(1));
    assertThat(trend.getFirst().getBucketStart(), equalTo(Instant.parse("2026-05-12T00:00:00.000Z")));
    assertThat(trend.getFirst().getExecutionCount(), equalTo(1L));
    assertThat(trend.getFirst().getExecutedCount(), equalTo(0L));
  }

  @Test
  @Order(7)
  void shallReturnPagedInventoryForQualityWorkspaceFilters() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    cycle.setTestExecutions(Set.of());
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto firstExecution = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    firstExecution.setCreatedOn(Instant.parse("2026-05-11T08:00:00.000Z"));
    firstExecution.setExecutedOn(Instant.parse("2026-05-11T09:00:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), firstExecution);

    final TestExecutionDto secondExecution = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    secondExecution.setCreatedOn(Instant.parse("2026-05-12T08:30:00.000Z"));
    secondExecution.setExecutedOn(Instant.parse("2026-05-12T08:45:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), secondExecution);

    final TypedResponse<PageResponse<TestExecutionInventoryDto>> response = testExecutionFeignClient.getAllPaged(
        0,
        10,
        "executedOn",
        "DESC",
        projectDto.getCode(),
        versionDto.getCode(),
        cycle.getCode(),
        item.getCode(),
        null,
        null);

    assertThat(response.status(), equalTo(200));
    final PageResponse<TestExecutionInventoryDto> page = response.body();
    assertThat(page, notNullValue());
    assertThat(page.getPageNumber(), equalTo(0));
    assertThat(page.getSize(), equalTo(10));
    assertThat(page.getTotalElements(), equalTo(2L));
    assertThat(page.getContent().size(), equalTo(2));

    final TestExecutionInventoryDto firstRow = page.getContent().get(0);
    assertThat(firstRow.getProject(), equalTo(projectDto.getCode()));
    assertThat(firstRow.getVersion(), equalTo(versionDto.getCode()));
    assertThat(firstRow.getCycleCode(), equalTo(cycle.getCode()));
    assertThat(firstRow.getItem(), equalTo(item.getCode()));
    assertThat(firstRow.getStatus(), equalTo(statuses.get(2).getCode()));
    assertThat(firstRow.getExecutor(), equalTo(userDto.getUsername()));
    assertThat(firstRow.getExecutedOn(), equalTo(Instant.parse("2026-05-12T08:45:00.000Z")));
  }

  @Test
  @Order(7)
  void shallApplyProgressFilterToQualityWorkspaceInventory() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    itemFeignClient.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    cycle.setTestExecutions(Set.of());
    testCycleFeignClient.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executed = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(1), userDto));
    executed.setCreatedOn(Instant.parse("2026-05-11T08:00:00.000Z"));
    executed.setExecutedOn(Instant.parse("2026-05-11T09:00:00.000Z"));
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), executed);

    final TestExecutionDto pending = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, statuses.get(2), userDto));
    pending.setCreatedOn(Instant.parse("2026-05-12T08:30:00.000Z"));
    pending.setExecutedOn(null);
    testExecutionFeignClient.saveOrUpdate(cycle.getCode(), pending);

    final TypedResponse<PageResponse<TestExecutionInventoryDto>> response = testExecutionFeignClient.getAllPaged(
        0,
        10,
        "executedOn",
        "DESC",
        projectDto.getCode(),
        versionDto.getCode(),
        cycle.getCode(),
        item.getCode(),
        null,
        "PENDING");

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalElements(), equalTo(1L));
    assertThat(response.body().getContent().size(), equalTo(1));
    assertThat(response.body().getContent().getFirst().getExecutedOn(), equalTo(null));
  }

  private static Predicate<TestExecutionDto> compareExecutions(TestExecutionDto e) {
    return st -> st.getItem().equals(e.getItem()) &&
        st.getExecutor().equals(e.getExecutor()) &&
        st.getStatus().equals(e.getStatus()) &&
        st.getExecutedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getExecutedOn().truncatedTo(ChronoUnit.MILLIS)) == 0 &&
        st.getCreatedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getCreatedOn().truncatedTo(ChronoUnit.MILLIS)) == 0;
  }
}