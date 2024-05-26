package org.catools.gatling.population.git;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.configs.SimulatorConfig;
import org.catools.athena.git.builder.GitBuilder;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.git.rest.controller.GitRepositoryController;
import org.catools.gatling.population.common.GatlingRequestUtils;
import org.catools.gatling.population.common.PopulationInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.details;
import static io.gatling.javaapi.core.CoreDsl.group;
import static io.gatling.javaapi.core.CoreDsl.nothingFor;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.reachRps;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class GitRepositoryPopulation {

  private static final List<String> repositoryStorage = Collections.synchronizedList(new ArrayList<>());

  public static List<PopulationInfo> getPopulationsInfo() {
    return List.of(
        getCreateGitRepositoryPopulation(),
        getSearchGitRepositoryPopulation()
    );
  }

  @NotNull
  private static PopulationInfo getCreateGitRepositoryPopulation() {
    return new PopulationInfo(
        scenario("Create GitRepository").exec(group("GitRepository").on(createRandomGitRepository())).injectOpen(
            atOnceUsers(1)
        ).throttle(reachRps(1).during(100)),
        List.of(
            details("GitRepository", "Save GitRepository").failedRequests().count().is(0L),
            details("GitRepository", "Save GitRepository").responseTime().percentile3().lte(30),
            details("GitRepository", "Save GitRepository").responseTime().percentile4().lte(60),
            details("GitRepository", "Save GitRepository").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getSearchGitRepositoryPopulation() {
    return new PopulationInfo(
        scenario("Search GitRepository").exec(group("GitRepository").on(searchGitRepositoryByCode())).injectOpen(
            nothingFor(5),
            rampUsersPerSec(5).to(50).during(10),
            constantUsersPerSec(50).during(90),
            nothingFor(10)
        ),
        List.of(
            details("GitRepository", "Search GitRepository").failedRequests().count().is(0L),
            details("GitRepository", "Search GitRepository").responseTime().percentile3().lte(30),
            details("GitRepository", "Search GitRepository").responseTime().percentile4().lte(50),
            details("GitRepository", "Search GitRepository").responseTime().max().lte(100))
    );
  }

  private static HttpRequestActionBuilder createRandomGitRepository() {
    Function<Session, String> buildGitRepository = session -> new Gson().toJson(GitBuilder.buildGitRepositoryDto());

    HttpRequestActionBuilder actionBuilder = http("Save GitRepository")
        .post(SimulatorConfig.getApiHost() + GitRepositoryController.REPOSITORY)
        .body(StringBody(buildGitRepository))
        .transformResponse((response, session) -> {
          GitRepositoryDto proj = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), GitRepositoryDto.class);
          repositoryStorage.add(proj.getName());
          repositoryStorage.add(proj.getUrl());
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder searchGitRepositoryByCode() {

    HttpRequestActionBuilder actionBuilder = http("Search GitRepository")
        .get(SimulatorConfig.getApiHost() + GitRepositoryController.REPOSITORY)
        .queryParam("keyword", session -> repositoryStorage.stream().findAny().get());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}