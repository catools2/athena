package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.gatling.configs.SimulatorConfig;
import org.catools.gatling.population.common.GatlingRequestUtils;
import org.catools.gatling.population.common.PopulationInfo;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.details;
import static io.gatling.javaapi.core.CoreDsl.group;
import static io.gatling.javaapi.core.CoreDsl.nothingFor;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static org.catools.athena.common.utils.ResponseEntityUtils.ENTITY_ID;
import static org.catools.athena.core.rest.controller.EnvironmentController.ENVIRONMENT;

public class EnvironmentPopulation {

  private static final List<String> searchableStorage = Collections.synchronizedList(new ArrayList<>());
  private static final List<EnvironmentDto> updatableStorage = Collections.synchronizedList(new ArrayList<>());

  public static List<PopulationInfo> getPopulationsInfo(int maxDuration) {
    return List.of(
        getCreatePopulation(maxDuration),
        getUpdatePopulation(maxDuration),
        getSearchPopulation(maxDuration)
    );
  }

  @NotNull
  private static PopulationInfo getCreatePopulation(int maxDuration) {
    return new PopulationInfo(
        scenario("Save Environment").exec(group("Environment").on(createRequest())).injectOpen(
            constantUsersPerSec(2).during(maxDuration)
        ),
        List.of(
            details("Environment", "Save Environment").failedRequests().count().is(0L),
            details("Environment", "Save Environment").responseTime().mean().lte(30),
            details("Environment", "Save Environment").responseTime().percentile3().lte(40),
            details("Environment", "Save Environment").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getUpdatePopulation(int maxDuration) {
    int quiteTime = 10;
    return new PopulationInfo(
        scenario("Update Environment").exec(group("Environment").on(updateRequest())).injectOpen(
            nothingFor(quiteTime),
            constantUsersPerSec(2).during(maxDuration)
        ),
        List.of(
            details("Environment", "Update Environment").failedRequests().count().is(0L),
            details("Environment", "Update Environment").responseTime().mean().lte(20),
            details("Environment", "Update Environment").responseTime().stdDev().lte(10),
            details("Environment", "Update Environment").responseTime().percentile3().lte(40),
            details("Environment", "Update Environment").responseTime().max().lte(100))
    );
  }

  @NotNull
  private static PopulationInfo getSearchPopulation(int maxDuration) {
    int quiteTime = 10;
    int rampUpTime = (int) (maxDuration * 0.2);
    int executionTime = maxDuration - rampUpTime - quiteTime - quiteTime;
    return new PopulationInfo(
        scenario("Search Environment").exec(group("Environment").on(searchRequest())).injectOpen(
            nothingFor(quiteTime),
            rampUsersPerSec(5).to(50).during(rampUpTime),
            constantUsersPerSec(50).during(executionTime),
            nothingFor(quiteTime)
        ),
        List.of(
            details("Environment", "Search Environment").failedRequests().count().is(0L),
            details("Environment", "Search Environment").responseTime().mean().lte(10),
            details("Environment", "Search Environment").responseTime().percentile3().lte(15),
            details("Environment", "Search Environment").responseTime().max().lte(150))
    );
  }

  private static HttpRequestActionBuilder createRequest() {
    Function<Session, String> buildEnvironment = session -> new Gson().toJson(buildRandomEnvironment());
    HttpRequestActionBuilder actionBuilder = http("Save Environment")
        .post(SimulatorConfig.getApiHost() + ENVIRONMENT)
        .body(StringBody(buildEnvironment))
        .transformResponse((response, session) -> {
          EnvironmentDto environment = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), EnvironmentDto.class);
          environment.setId(Long.parseLong(response.headers().get(ENTITY_ID)));
          if (updatableStorage.size() < 10)
            updatableStorage.add(environment);
          else {
            searchableStorage.add(environment.getCode());
            searchableStorage.add(environment.getName());
          }
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder updateRequest() {
    Function<Session, String> buildEnvironment =
        session -> new Gson().toJson(buildRandomEnvironment().setId(updatableStorage.stream().findAny().orElseThrow().getId()));

    HttpRequestActionBuilder actionBuilder = http("Update Environment")
        .put(SimulatorConfig.getApiHost() + ENVIRONMENT)
        .body(StringBody(buildEnvironment));

    return GatlingRequestUtils.decorateJsonPostRequest(200, actionBuilder);
  }

  private static HttpRequestActionBuilder searchRequest() {
    HttpRequestActionBuilder actionBuilder = http("Search Environment")
        .get(SimulatorConfig.getApiHost() + ENVIRONMENT)
        .queryParam("keyword", session ->
            searchableStorage
                .stream()
                .skip(RandomUtils.nextInt(0, searchableStorage.size()))
                .findAny()
                .orElseThrow());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }

  private static EnvironmentDto buildRandomEnvironment() {
    return CoreBuilder.buildEnvironmentDto(StagedTestData.getProject(1).getCode());
  }
}