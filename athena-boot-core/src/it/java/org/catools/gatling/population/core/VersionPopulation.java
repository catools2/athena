package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.core.rest.controller.VersionController;
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

public class VersionPopulation {

  private static final List<String> searchableStorage = Collections.synchronizedList(new ArrayList<>());
  private static final List<VersionDto> updatableStorage = Collections.synchronizedList(new ArrayList<>());

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
        scenario("Save Version").exec(group("Version").on(createRequest())).injectOpen(
            constantUsersPerSec(5).during(maxDuration)
        ),
        List.of(
            details("Version", "Save Version").failedRequests().percent().lte(1.0),
            details("Version", "Save Version").responseTime().mean().lte(40),
            details("Version", "Save Version").responseTime().percentile3().lte(50),
            details("Version", "Save Version").responseTime().max().lte(1500))
    );
  }

  @NotNull
  private static PopulationInfo getUpdatePopulation(int maxDuration) {
    int quiteTime = 10;
    return new PopulationInfo(
        scenario("Update Version").exec(group("Version").on(updateRequest())).injectOpen(
            nothingFor(quiteTime),
            constantUsersPerSec(1).during(maxDuration)
        ),
        List.of(
            details("Version", "Update Version").failedRequests().percent().lte(1.0),
            details("Version", "Update Version").responseTime().mean().lte(40),
            details("Version", "Update Version").responseTime().percentile3().lte(50),
            details("Version", "Update Version").responseTime().max().lte(1500))
    );
  }

  @NotNull
  private static PopulationInfo getSearchPopulation(int maxDuration) {
    int quiteTime = 10;
    int rampUpTime = (int) (maxDuration * 0.2);
    int executionTime = maxDuration - rampUpTime - quiteTime - quiteTime;
    return new PopulationInfo(
        scenario("Search Version").exec(group("Version").on(searchRequest())).injectOpen(
            nothingFor(quiteTime),
            rampUsersPerSec(5).to(50).during(rampUpTime),
            constantUsersPerSec(50).during(executionTime),
            nothingFor(quiteTime)
        ),
        List.of(
            details("Version", "Search Version").failedRequests().percent().lte(1.0),
            details("Version", "Search Version").responseTime().mean().lte(10),
            details("Version", "Search Version").responseTime().percentile3().lte(15),
            details("Version", "Search Version").responseTime().max().lte(150))
    );
  }

  private static HttpRequestActionBuilder createRequest() {
    Function<Session, String> buildVersion = session -> new Gson().toJson(buildRandomVersion());
    HttpRequestActionBuilder actionBuilder = http("Save Version")
        .post(SimulatorConfig.getApiHost() + VersionController.VERSION)
        .body(StringBody(buildVersion))
        .transformResponse((response, session) -> {
          VersionDto version = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), VersionDto.class);
          version.setId(Long.parseLong(response.headers().get(ENTITY_ID)));
          if (updatableStorage.size() < 30)
            updatableStorage.add(version);
          else {
            searchableStorage.add(version.getCode());
            searchableStorage.add(version.getName());
          }
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder updateRequest() {
    Function<Session, String> buildVersion =
        session -> new Gson().toJson(buildRandomVersion().setId(updatableStorage.stream().findAny().orElseThrow().getId()));

    HttpRequestActionBuilder actionBuilder = http("Update Version")
        .put(SimulatorConfig.getApiHost() + VersionController.VERSION)
        .body(StringBody(buildVersion));

    return GatlingRequestUtils.decorateJsonPostRequest(200, actionBuilder);
  }

  private static VersionDto buildRandomVersion() {
    return CoreBuilder.buildVersionDto(StagedTestData.getProject(1).getCode());
  }

  private static HttpRequestActionBuilder searchRequest() {
    HttpRequestActionBuilder actionBuilder = http("Search Version")
        .get(SimulatorConfig.getApiHost() + VersionController.VERSION)
        .queryParam("keyword", session ->
            searchableStorage
                .stream()
                .skip(RandomUtils.nextInt(0, searchableStorage.size()))
                .findAny()
                .orElseThrow());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}