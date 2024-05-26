package org.catools.athena.tms.controller;

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
import org.springframework.http.ResponseEntity;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCycleControllerIT extends BaseTmsControllerIT {

  String CYCLE_CODE = "ABCD1234";

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemController.saveOrUpdate(itemDto);

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION, item, STATUSES.get(0), USER);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    cycleDto.setCode(CYCLE_CODE);
    final ResponseEntity<Void> response = testCycleController.save(cycleDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());

    final ResponseEntity<TestCycleDto> response2 = testCycleController.search(CYCLE_CODE);

    TestCycleDto savedCycle = response2.getBody();
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
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemController.saveOrUpdate(itemDto);

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION, item, STATUSES.get(0), USER);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    cycleDto.setCode(CYCLE_CODE);

    final ResponseEntity<Void> response1 = testCycleController.save(cycleDto);
    assertThat(response1.getStatusCode().value(), equalTo(201));
    assertThat(response1.getHeaders().getLocation(), notNullValue());

    final ResponseEntity<TestCycleDto> response2 = testCycleController.search(CYCLE_CODE);

    TestCycleDto savedCycle = response2.getBody();
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
    final ResponseEntity<TestCycleDto> response = testCycleController.getById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnUniqueHashWhenValidCodeProvided() {
    final ResponseEntity<Integer> response = testCycleController.getUniqueHashByCode(CYCLE_CODE);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody(), greaterThan(1));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final ResponseEntity<TestCycleDto> response = testCycleController.search(CYCLE_CODE);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidVersionCodeProvided() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemController.saveOrUpdate(itemDto);

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION, item, STATUSES.get(0), USER);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    testCycleController.save(cycleDto);

    final ResponseEntity<TestCycleDto> response = testCycleController.findLastByPattern(cycleDto.getName(), VERSION.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(cycleDto.getCode()));
    assertThat(response.getBody().getName(), equalTo(cycleDto.getName()));
  }
}