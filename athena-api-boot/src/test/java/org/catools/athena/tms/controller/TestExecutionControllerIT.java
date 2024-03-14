package org.catools.athena.tms.controller;

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
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestExecutionControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(AppVERSION));
    itemController.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    final ResponseEntity<Void> response = testExecutionController.saveOrUpdate(cycle.getCode(), executionDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(AppVERSION));
    itemController.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto executionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), executionDto);

    // Repeat The same save to simulate case when the entity already exists
    final ResponseEntity<Void> savedResponse = testExecutionController.saveOrUpdate(cycle.getCode(), executionDto);
    assertThat(savedResponse.getStatusCode().value(), equalTo(201));
    assertThat(savedResponse.getHeaders().getLocation(), notNullValue());

    Long entityId = Long.valueOf(savedResponse.getHeaders().get("entity_id").get(0));

    final ResponseEntity<TestExecutionDto> getIdResponse = testExecutionController.getById(entityId);
    assertThat(getIdResponse.getStatusCode().value(), equalTo(200));
    assertThat(getIdResponse.getBody(), notNullValue());
    assertThat(getIdResponse.getBody().getId(), equalTo(entityId));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidItemCodeAndCycleCodeProvided() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(AppVERSION));
    itemController.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));

    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(2), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(3), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e3);

    final ResponseEntity<Set<TestExecutionDto>> response = testExecutionController.getAll(item.getCode(), cycle.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), greaterThanOrEqualTo(3));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidItemCodeProvidedWithNoCycleCode() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(AppVERSION));
    itemController.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(2), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(3), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e3);

    final ResponseEntity<Set<TestExecutionDto>> response = testExecutionController.getAll(item.getCode(), null);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), greaterThanOrEqualTo(3));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCycleCodeProvidedWithNoItemCode() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(AppVERSION));
    itemController.saveOrUpdate(tmsMapper.itemToItemDto(item));

    final TestCycle cycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    testCycleController.save(tmsMapper.testCycleToTestCycleDto(cycle));


    final TestExecutionDto e1 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(1), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e1);

    final TestExecutionDto e2 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(2), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e2);

    final TestExecutionDto e3 = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(cycle, item, STATUSES.get(3), USER));
    testExecutionController.saveOrUpdate(cycle.getCode(), e3);

    final ResponseEntity<Set<TestExecutionDto>> response = testExecutionController.getAll(null, cycle.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), greaterThanOrEqualTo(3));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareExecutions(e3)), equalTo(true));
  }

  private static Predicate<TestExecutionDto> compareExecutions(TestExecutionDto e) {
    return st -> st.getItem().equals(e.getItem()) &&
        st.getExecutor().equals(e.getExecutor()) &&
        st.getStatus().equals(e.getStatus()) &&
        st.getExecutedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getExecutedOn().truncatedTo(ChronoUnit.MILLIS)) == 0 &&
        st.getCreatedOn().truncatedTo(ChronoUnit.MILLIS).compareTo(e.getCreatedOn().truncatedTo(ChronoUnit.MILLIS)) == 0;
  }
}