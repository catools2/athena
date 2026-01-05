package org.catools.athena.tms.controller;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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

  private static Predicate<TestExecutionDto> compareExecutions(TestExecutionDto e) {
    return st -> st.getItem().equals(e.getItem()) &&
        st.getExecutor().equals(e.getExecutor()) &&
        st.getStatus().equals(e.getStatus()) &&
        st.getExecutedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getExecutedOn().truncatedTo(ChronoUnit.MILLIS)) == 0 &&
        st.getCreatedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getCreatedOn().truncatedTo(ChronoUnit.MILLIS)) == 0;
  }
}