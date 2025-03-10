package org.catools.athena.tms.controller;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.model.ItemDto;
import org.catools.athena.tms.model.TestCycleDto;
import org.catools.athena.tms.model.TestExecutionDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCycleControllerIT extends BaseTmsControllerIT {

  private static final String CYCLE_CODE = "ABCD1234";

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    cycleDto.setCode(CYCLE_CODE);
    final TypedResponse<Void> response = testCycleFeignClient.save(cycleDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());

    final TypedResponse<TestCycleDto> response2 = testCycleFeignClient.search(CYCLE_CODE);

    TestCycleDto savedCycle = response2.body();
    assertThat(savedCycle, notNullValue());
    assertThat(savedCycle.getName(), equalTo(cycleDto.getName()));
    assertThat(savedCycle.getCode(), equalTo(cycleDto.getCode()));
    assertThat(savedCycle.getVersion(), equalTo(cycleDto.getVersion()));
    assertThat(savedCycle.getEndDate().truncatedTo(ChronoUnit.MILLIS), equalTo(cycleDto.getEndDate().truncatedTo(ChronoUnit.MILLIS)));
    assertThat(savedCycle.getStartDate().truncatedTo(ChronoUnit.MILLIS), equalTo(cycleDto.getStartDate().truncatedTo(ChronoUnit.MILLIS)));

    assertThat(savedCycle.getTestExecutions().isEmpty(), equalTo(false));
    for (TestExecutionDto exec : savedCycle.getTestExecutions()) {
      Optional<TestExecutionDto> first = cycleDto.getTestExecutions().stream().filter(e1 -> e1.getExecutedOn().truncatedTo(ChronoUnit.MILLIS).equals(exec.getExecutedOn().truncatedTo(ChronoUnit.MILLIS))).findFirst();
      assertThat(first.isPresent(), equalTo(true));

      TestExecutionDto e2 = first.get();
      assertThat(exec.getItem(), equalTo(e2.getItem()));
      assertThat(exec.getExecutedOn().truncatedTo(ChronoUnit.MILLIS), equalTo(e2.getExecutedOn().truncatedTo(ChronoUnit.MILLIS)));
      assertThat(exec.getCreatedOn().truncatedTo(ChronoUnit.MILLIS), equalTo(e2.getCreatedOn().truncatedTo(ChronoUnit.MILLIS)));
      assertThat(exec.getExecutor(), equalTo(e2.getExecutor()));
      assertThat(exec.getStatus(), equalTo(e2.getStatus()));
    }
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    cycleDto.setCode(CYCLE_CODE);

    final TypedResponse<Void> response1 = testCycleFeignClient.save(cycleDto);
    assertThat(response1.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response1);
    assertThat(entityId, notNullValue());

    final TypedResponse<TestCycleDto> response2 = testCycleFeignClient.search(CYCLE_CODE);

    TestCycleDto savedCycle = response2.body();
    assertThat(savedCycle, notNullValue());
    assertThat(savedCycle.getName(), equalTo(cycleDto.getName()));
    assertThat(savedCycle.getCode(), equalTo(cycleDto.getCode()));
    assertThat(savedCycle.getVersion(), equalTo(cycleDto.getVersion()));
    assertThat(savedCycle.getEndDate().truncatedTo(ChronoUnit.MILLIS), equalTo(cycleDto.getEndDate().truncatedTo(ChronoUnit.MILLIS)));
    assertThat(savedCycle.getStartDate().truncatedTo(ChronoUnit.MILLIS), equalTo(cycleDto.getStartDate().truncatedTo(ChronoUnit.MILLIS)));

    assertThat(savedCycle.getTestExecutions().isEmpty(), equalTo(false));
    for (TestExecutionDto exec : cycleDto.getTestExecutions()) {
      Optional<TestExecutionDto> first = savedCycle.getTestExecutions().stream().filter(e1 -> e1.getExecutedOn().truncatedTo(ChronoUnit.MILLIS).equals(exec.getExecutedOn().truncatedTo(ChronoUnit.MILLIS))).findFirst();
      assertThat(first.isPresent(), equalTo(true));

      TestExecutionDto e2 = first.get();
      assertThat(exec.getItem(), equalTo(e2.getItem()));
      assertThat(exec.getExecutedOn().truncatedTo(ChronoUnit.MILLIS), equalTo(e2.getExecutedOn().truncatedTo(ChronoUnit.MILLIS)));
      assertThat(exec.getCreatedOn().truncatedTo(ChronoUnit.MILLIS), equalTo(e2.getCreatedOn().truncatedTo(ChronoUnit.MILLIS)));
      assertThat(exec.getExecutor(), equalTo(e2.getExecutor()));
      assertThat(exec.getStatus(), equalTo(e2.getStatus()));
    }
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final TypedResponse<TestCycleDto> response = testCycleFeignClient.getById(1L);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final TypedResponse<TestCycleDto> response = testCycleFeignClient.search(CYCLE_CODE);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidVersionCodeProvided() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final TestCycle cycle = TmsBuilder.buildTestCycle(versionDto, item, statuses.get(0), userDto);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    testCycleFeignClient.save(cycleDto);

    final TypedResponse<TestCycleDto> response = testCycleFeignClient.findLastByPattern(cycleDto.getName(), versionDto.getCode());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getCode(), equalTo(cycleDto.getCode()));
    assertThat(response.body().getName(), equalTo(cycleDto.getName()));
  }
}