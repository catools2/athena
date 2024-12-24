package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.rest.controller.ProjectController;
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

public class ProjectPopulation {

  private static final List<String> searchableStorage = Collections.synchronizedList(new ArrayList<>());
  private static final List<ProjectDto> updatableStorage = Collections.synchronizedList(new ArrayList<>());

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
        scenario("Save Project").exec(group("Project").on(createRequest())).injectOpen(
            constantUsersPerSec(2).during(maxDuration)
        ),
        List.of(
            details("Project", "Save Project").failedRequests().count().is(0L),
            details("Project", "Save Project").responseTime().mean().lte(30),
            details("Project", "Save Project").responseTime().percentile3().lte(40),
            details("Project", "Save Project").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getUpdatePopulation(int maxDuration) {
    int quiteTime = 10;
    return new PopulationInfo(
        scenario("Update Project").exec(group("Project").on(updateRequest())).injectOpen(
            nothingFor(quiteTime),
            constantUsersPerSec(2).during(maxDuration)
        ),
        List.of(
            details("Project", "Update Project").failedRequests().count().is(0L),
            details("Project", "Update Project").responseTime().mean().lte(30),
            details("Project", "Update Project").responseTime().percentile3().lte(40),
            details("Project", "Update Project").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getSearchPopulation(int maxDuration) {
    int quiteTime = 10;
    int rampUpTime = (int) (maxDuration * 0.2);
    int executionTime = maxDuration - rampUpTime - quiteTime - quiteTime;
    return new PopulationInfo(
        scenario("Search Project").exec(group("Project").on(searchRequest())).injectOpen(
            nothingFor(quiteTime),
            rampUsersPerSec(5).to(50).during(rampUpTime),
            constantUsersPerSec(50).during(executionTime),
            nothingFor(quiteTime)
        ),
        List.of(
            details("Project", "Search Project").failedRequests().count().is(0L),
            details("Project", "Search Project").responseTime().mean().lte(10),
            details("Project", "Search Project").responseTime().percentile3().lte(20),
            details("Project", "Search Project").responseTime().max().lte(150))
    );
  }

  private static HttpRequestActionBuilder createRequest() {
    Function<Session, String> buildProject = session -> new Gson().toJson(CoreBuilder.buildProjectDto());

    HttpRequestActionBuilder actionBuilder = http("Save Project")
        .post(SimulatorConfig.getApiHost() + ProjectController.PROJECT)
        .body(StringBody(buildProject))
        .transformResponse((response, session) -> {
          ProjectDto proj = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), ProjectDto.class);
          proj.setId(Long.parseLong(response.headers().get(ENTITY_ID)));
          if (updatableStorage.size() < 10)
            updatableStorage.add(proj);
          else {
            searchableStorage.add(proj.getName());
            searchableStorage.add(proj.getCode());
          }
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder updateRequest() {
    Function<Session, String> buildProject =
        session -> new Gson().toJson(CoreBuilder.buildProjectDto().setId(updatableStorage.stream().findAny().orElseThrow().getId()));

    HttpRequestActionBuilder actionBuilder = http("Update Project")
        .post(SimulatorConfig.getApiHost() + ProjectController.PROJECT)
        .body(StringBody(buildProject));

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder searchRequest() {

    HttpRequestActionBuilder actionBuilder = http("Search Project")
        .get(SimulatorConfig.getApiHost() + ProjectController.PROJECT)
        .queryParam("keyword", session ->
            searchableStorage
                .stream()
                .skip(RandomUtils.nextInt(0, searchableStorage.size()))
                .findAny()
                .orElseThrow());


    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}