package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.configs.SimulatorConfig;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.configs.StagedTestData;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.core.rest.controller.VersionController;
import org.catools.gatling.population.common.GatlingRequestUtils;
import org.catools.gatling.population.common.PopulationInfo;
import org.jetbrains.annotations.NotNull;

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

public class VersionPopulation {

  private static final List<String> versionsStorage = Collections.synchronizedList(new ArrayList<>());

  public static List<PopulationInfo> getPopulationsInfo() {
    return List.of(
        getCreateVersionPopulation(),
        getSearchVersionPopulation()
    );
  }

  @NotNull
  private static PopulationInfo getCreateVersionPopulation() {
    return new PopulationInfo(
        scenario("Create Version").exec(group("Version").on(createRandomVersion())).injectOpen(
            constantUsersPerSec(1).during(120)
        ),
        List.of(
            details("Version", "Save Version").failedRequests().count().is(0L),
            details("Version", "Save Version").responseTime().percentile3().lte(60),
            details("Version", "Save Version").responseTime().percentile4().lte(200),
            details("Version", "Save Version").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getSearchVersionPopulation() {
    return new PopulationInfo(
        scenario("Search Version").exec(group("Version").on(searchVersionByCode())).injectOpen(
            nothingFor(5),
            rampUsersPerSec(5).to(50).during(10),
            constantUsersPerSec(50).during(90),
            nothingFor(10)
        ),
        List.of(
            details("Version", "Search Version").failedRequests().count().is(0L),
            details("Version", "Search Version").responseTime().percentile3().lte(30),
            details("Version", "Search Version").responseTime().percentile4().lte(50),
            details("Version", "Search Version").responseTime().max().lte(100))
    );
  }

  private static HttpRequestActionBuilder createRandomVersion() {
    Function<Session, String> buildVersion = session -> new Gson().toJson(CoreBuilder.buildVersionDto(StagedTestData.getProject(1).getCode()));
    HttpRequestActionBuilder actionBuilder = http("Save Version")
        .post(SimulatorConfig.getApiHost() + VersionController.VERSION)
        .body(StringBody(buildVersion))
        .transformResponse((response, session) -> {
          VersionDto version = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), VersionDto.class);
          versionsStorage.add(version.getCode());
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder searchVersionByCode() {
    HttpRequestActionBuilder actionBuilder = http("Search Version")
        .get(SimulatorConfig.getApiHost() + VersionController.VERSION)
        .queryParam("keyword", session -> versionsStorage.stream().findAny().get());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}