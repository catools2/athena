package org.catools.athena.tms.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.tms.model.SyncInfoDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SyncInfoControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    verifySyncInfo(Instant.now());
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    verifySyncInfo(Instant.now());
  }

  void verifySyncInfo(Instant endDate) {
    SyncInfoDto syncInfo = new SyncInfoDto(PROJECT.getCode(), "ACT1", "COMP1", Instant.now().minus(5, ChronoUnit.MINUTES), endDate);
    ResponseEntity<Void> savedResponse = syncInfoController.saveOrUpdate(syncInfo);
    assertThat(savedResponse.getStatusCode().value(), equalTo(201));
    assertThat(savedResponse.getBody(), nullValue());

    Long entityId = ResponseEntityUtils.getEntityId(savedResponse);
    final ResponseEntity<SyncInfoDto> response = syncInfoController.getById(entityId);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getAction(), equalTo(syncInfo.getAction()));
    assertThat(response.getBody().getComponent(), equalTo(syncInfo.getComponent()));
    assertThat(response.getBody().getProject(), equalTo(syncInfo.getProject()));
    assertThat(response.getBody().getStartTime().truncatedTo(ChronoUnit.MILLIS), equalTo(syncInfo.getStartTime().truncatedTo(ChronoUnit.MILLIS)));
    if (syncInfo.getEndTime() == null)
      assertThat(response.getBody().getEndTime(), equalTo(syncInfo.getEndTime()));
    else
      assertThat(response.getBody().getEndTime().truncatedTo(ChronoUnit.MILLIS), equalTo(syncInfo.getEndTime().truncatedTo(ChronoUnit.MILLIS)));
  }
}