package org.catools.athena.core.controller;

import org.catools.athena.core.rest.controller.QueryController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QueryControllerIT extends CoreControllerIT {

  @Autowired
  QueryController queryController;

  @Test
  void querySingleResult() {
    ResponseEntity<Object> objectResponseEntity = queryController.querySingleResult("select name from athena_core.project where id = 1");

    assertThat(objectResponseEntity.getStatusCode(), notNullValue());
    assertThat(objectResponseEntity.getStatusCode().value(), equalTo(200));
    assertThat(objectResponseEntity.getBody(), notNullValue());
  }

  @Test
  void queryCollection() {
    ResponseEntity<Set<Object>> objectResponseEntity = queryController.queryCollection("select id from athena_core.project");

    assertThat(objectResponseEntity.getStatusCode(), notNullValue());
    assertThat(objectResponseEntity.getStatusCode().value(), equalTo(200));
    assertThat(objectResponseEntity.getBody(), notNullValue());
    assertThat(objectResponseEntity.getBody(), hasItems(1L));

  }
}