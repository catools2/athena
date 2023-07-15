package org.catools.atlassian.scale.rest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.catools.atlassian.scale.configs.CZScaleConfigs;
import org.catools.atlassian.scale.exception.CZScaleClientException;
import org.catools.common.concurrent.CParallelCollectionIO;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.utils.CRetry;
import org.catools.common.utils.CSleeper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class CZScaleRestClient {
  public CZScaleRestClient() {
  }

  protected Response getWithoutVerification(RequestSpecification request) {
    return CRetry.retry(integer -> decorate(request).get(), 5, 1000);
  }

  protected Response get(RequestSpecification request) {
    return CRetry.retry(integer -> verifyResponse(decorate(request).get()), 5, 1000);
  }

  protected Response delete(RequestSpecification request) {
    return CRetry.retry(integer -> verifyResponse(decorate(request).delete()), 5, 1000);
  }

  protected Response post(RequestSpecification request) {
    return CRetry.retry(integer -> verifyResponse(decorate(request).post()), 5, 1000);
  }

  protected Response put(RequestSpecification request) {
    return CRetry.retry(integer -> verifyResponse(decorate(request).put()), 5, 1000);
  }

  private Response verifyResponse(Response response) {
    int statusCode = response.statusCode();
    if (statusCode < 200 || statusCode > 204) {
      CVerify.Int.betweenInclusive(
          statusCode, 200, 204, "Request processed successfully. response " + response.print());
    }
    return response;
  }

  protected RequestSpecification decorate(RequestSpecification request) {
    CSleeper.sleepTight(CZScaleConfigs.Scale.getDelayBetweenCallsInMilliseconds());
    return request
        .auth()
        .preemptive()
        .basic(CZScaleConfigs.Scale.getUserName(), CZScaleConfigs.Scale.getPassword())
        .contentType(ContentType.JSON);
  }

  protected static <T> Set<T> readAllInParallel(
      String actionName,
      int parallelInputCount,
      int parallelOutputCount,
      BiFunction<Integer, Integer, Set<T>> requestProcessor,
      Consumer<T> onAction) {

    Set<T> output = Collections.synchronizedSet(new HashSet<>());

    CParallelCollectionIO<T> parallelIO = new CParallelCollectionIO<>(actionName, parallelInputCount, parallelOutputCount);

    int maxResult = CZScaleConfigs.Scale.getSearchBufferSize();
    AtomicInteger counter = new AtomicInteger(0);

    parallelIO.setInputExecutor(eof -> {
      int startAt = counter.getAndIncrement() * maxResult;
      Set<T> result = CRetry.retry(integer -> requestProcessor.apply(startAt, maxResult), 3, 5000);
      if (result == null || result.isEmpty()) {
        eof.set(true);
        CSleeper.sleepTight(500);
      } else {
        output.addAll(result);
        eof.set(false);
      }
      return result;
    });

    parallelIO.setOutputExecutor((eof, issue) -> {
      if (onAction != null && issue != null) {
        onAction.accept(issue);
      }
    });

    try {
      parallelIO.run();
    } catch (Throwable t) {
      throw new CZScaleClientException("Could not finish search.", t);
    }

    return output;
  }
}
