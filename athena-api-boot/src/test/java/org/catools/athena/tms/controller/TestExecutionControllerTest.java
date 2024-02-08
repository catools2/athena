package org.catools.athena.tms.controller;

import com.google.common.collect.Sets;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.model.TestExecutionDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestExecutionControllerTest extends BaseTmsControllerTest {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES.get(0), USER, Sets.newHashSet(VERSION));
    itemController.save(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    final ResponseEntity<Void> response = testExecutionController.save(executionDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallNotSaveRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES.get(0), USER, Sets.newHashSet(VERSION));
    itemController.save(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.save(executionDto);

    // Repeat The same save to simulate case when the entity already exists
    final ResponseEntity<Void> response = testExecutionController.save(executionDto);
    assertThat(response.getStatusCode().value(), equalTo(208));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final ResponseEntity<TestExecutionDto> response = testExecutionController.getById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), equalTo(1L));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidItemCodeAndCycleCodeProvided() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES.get(0), USER, Sets.newHashSet(VERSION));
    itemController.save(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.save(e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(2), USER));
    testExecutionController.save(e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(3), USER));
    testExecutionController.save(e3);

    final ResponseEntity<Set<TestExecutionDto>> response = testExecutionController.getAll(item.getCode(), cycle.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), equalTo(3));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidItemCodeProvidedWithNoCycleCode() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES.get(0), USER, Sets.newHashSet(VERSION));
    itemController.save(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.save(e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(2), USER));
    testExecutionController.save(e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(3), USER));
    testExecutionController.save(e3);

    final ResponseEntity<Set<TestExecutionDto>> response = testExecutionController.getAll(item.getCode(), null);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), equalTo(3));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCycleCodeProvidedWithNoItemCode() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES.get(0), USER, Sets.newHashSet(VERSION));
    itemController.save(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.save(e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(2), USER));
    testExecutionController.save(e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(3), USER));
    testExecutionController.save(e3);

    final ResponseEntity<Set<TestExecutionDto>> response = testExecutionController.getAll(null, cycle.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), equalTo(3));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  private static Predicate<TestExecutionDto> compareExecutions(TestExecutionDto e) {
    return st -> st.getItem().equals(e.getItem()) &&
        st.getExecutor().equals(e.getExecutor()) &&
        st.getCycle().equals(e.getCycle()) &&
        st.getStatus().equals(e.getStatus()) &&
        st.getExecutedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getExecutedOn().truncatedTo(ChronoUnit.MILLIS)) == 0 &&
        st.getCreatedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getCreatedOn().truncatedTo(ChronoUnit.MILLIS)) == 0;
  }
}