package org.catools.athena.feign;

import com.fasterxml.jackson.databind.JsonNode;
import feign.TypedResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.JacksonUtil;
import org.testcontainers.shaded.com.google.common.collect.MapDifference;
import org.testcontainers.shaded.com.google.common.collect.Maps;

import static org.catools.athena.common.utils.JacksonUtil.toJsonNode;
import static org.catools.athena.common.utils.JacksonUtil.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@Slf4j
@UtilityClass
public class FeignAssertHelper {

  public static void assertBodyEquals(String reason, TypedResponse<?> response, String expectedBody) {
    assertThat(response.body(), notNullValue());

    JsonNode actual = toJsonNode(JacksonUtil.toJsonString(response.body()));
    JsonNode expected = toJsonNode(expectedBody);

    try {
      assertThat(reason, actual, is(expected));
    } catch (AssertionError e) {
      MapDifference<String, Object> difference = Maps.difference(toMap(actual), toMap(expected));
      log.warn("only in actual:\n{}", difference.entriesOnlyOnLeft());
      log.warn("only in expected:\n{}", difference.entriesOnlyOnRight());
      log.warn("differing:\n{}", difference.entriesDiffering());
      throw e;
    }
  }

}
