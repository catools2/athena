package org.catools.athena.rest.feign.common.model;

import feign.Request.HttpMethod;
import feign.RetryableException;
import feign.Retryer;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CustomRetryer extends Retryer.Default {
  private static final List<HttpMethod> nonIdempotentMethods =
      List.of(HttpMethod.POST, HttpMethod.PATCH);

  @Override
  public void continueOrPropagate(RetryableException e) {
    if (nonIdempotentMethods.contains(e.method())) throw e;

    super.continueOrPropagate(e);
  }

  public Retryer clone() {
    return new CustomRetryer();
  }
}
