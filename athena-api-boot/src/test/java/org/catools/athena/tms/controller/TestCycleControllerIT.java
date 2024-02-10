package org.catools.athena.tms.controller;

import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.model.TestCycleDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCycleControllerIT extends BaseTmsControllerIT {

  String CYCLE_CODE = "ABCD1234";

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    cycleDto.setCode(CYCLE_CODE);
    final ResponseEntity<Void> response = testCycleController.saveOrUpdate(cycleDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    final TestCycle cycle = TmsBuilder.buildTestCycle(VERSION);
    final TestCycleDto cycleDto = tmsMapper.testCycleToTestCycleDto(cycle);
    cycleDto.setCode(CYCLE_CODE);
    final ResponseEntity<Void> response = testCycleController.saveOrUpdate(cycleDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
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
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final ResponseEntity<TestCycleDto> response = testCycleController.getByCode(CYCLE_CODE);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidVersionCodeProvided() {
    final ResponseEntity<Set<TestCycleDto>> response = testCycleController.getByVersionCode(VERSION.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().isEmpty(), equalTo(false));
  }
}