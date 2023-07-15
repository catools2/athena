package org.catools.atlassian.zapi.rest.zql;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.zapi.configs.CZApiConfigs;
import org.catools.atlassian.zapi.exception.CZApiClientException;
import org.catools.atlassian.zapi.model.CZApiExecutions;
import org.catools.atlassian.zapi.parser.CZApiExecutionsParser;
import org.catools.atlassian.zapi.rest.CZApiRestClient;
import org.catools.common.concurrent.CParallelIO;
import org.catools.common.date.CDate;
import org.catools.common.utils.CRetry;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class CZApiSearchClient extends CZApiRestClient {
  public CZApiSearchClient() {
    super();
  }

  public CZApiExecutions getExecutions(String zql) {
    return getExecutions(zql, null, 1, 1, null);
  }

  public CZApiExecutions getExecutions(String zql, Date lastSyncDate) {
    return getExecutions(zql, lastSyncDate, 1, 1, null);
  }

  public CZApiExecutions getExecutions(String zql, int parallelInputCount) {
    return getExecutions(zql, null, parallelInputCount, 1, null);
  }

  public CZApiExecutions getExecutions(String zql, Date lastSyncDate, int parallelInputCount) {
    return getExecutions(zql, lastSyncDate, parallelInputCount, 1, null);
  }

  public CZApiExecutions getExecutions(
      String zql,
      int parallelInputCount,
      int parallelOutputCount,
      Consumer<CZApiExecutions> supplier) {
    return getExecutions(zql, null, parallelInputCount, parallelOutputCount, supplier);
  }

  public CZApiExecutions getExecutions(
      String zql,
      Date lastSyncDate,
      int parallelInputCount,
      int parallelOutputCount,
      Consumer<CZApiExecutions> supplier) {
    CZApiExecutions executions = new CZApiExecutions();
    CParallelIO<CZApiExecutions> parallelIO = new CParallelIO<>("Search ZApi Executions", parallelInputCount, parallelOutputCount);

    int maxResult = CZApiConfigs.ZApi.getSearchBufferSize();
    AtomicInteger counter = new AtomicInteger(0);

    parallelIO.setInputExecutor(eof -> {
      int startAt = counter.getAndIncrement() * maxResult;
      CZApiExecutions search = CRetry.retry(integer -> _getExecutions(zql, lastSyncDate, startAt, maxResult), 5, 5000);

      if (search == null || search.isEmpty()) {
        log.info("{} execution record returned for ZQL = {}", executions.size(), zql);
        eof.set(true);
      } else {
        executions.addAll(search);
        eof.set(false);
      }
      return search;
    });

    parallelIO.setOutputExecutor((eof, issues) -> {
      if (supplier != null && issues != null && issues.isNotEmpty()) {
        supplier.accept(issues);
      }
    });

    try {
      parallelIO.run();
    } catch (Throwable t) {
      throw new CZApiClientException("Could not finish search.", t);
    }

    return executions;
  }

  private CZApiExecutions _getExecutions(String zql, Date lastSyncDate, int offset, int maxResults) {
    if (lastSyncDate != null) {
      zql +=
          String.format(
              " AND (creationDate >= '%1$s' or executionDate >= '%1$s')",
              new CDate(lastSyncDate).toFormat("yyyy/MM/dd"));
    }

    log.info("Execute Search {}, maxResults:{}, startAt:{}", zql, maxResults, offset);
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZApiConfigs.ZApi.getZApiUri())
            .basePath("/zql/executeSearch")
            .queryParam("zqlQuery", zql)
            .queryParam("offset", offset)
            .queryParam("maxRecords", maxResults);

    return CZApiExecutionsParser.parse(get(specification));
  }
}
