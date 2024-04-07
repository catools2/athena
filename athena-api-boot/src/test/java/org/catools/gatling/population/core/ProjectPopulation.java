package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.configs.SimulatorConfig;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.rest.controller.ProjectController;
import org.catools.gatling.population.common.GatlingRequestUtils;
import org.catools.gatling.population.common.PopulationInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ProjectPopulation {

  private static final List<String> projectToSearch = Collections.synchronizedList(new ArrayList<>());

  public static List<PopulationInfo> getPopulationsInfo() {
    return List.of(
        getCreateProjectPopulation(),
        getSearchProjectPopulation()
    );
  }

  @NotNull
  private static PopulationInfo getCreateProjectPopulation() {
    return new PopulationInfo(
        scenario("Create Project").exec(createRandomProject()).injectOpen(
            rampUsers(2).during(5),
            constantUsersPerSec(1).during(110)
        ), List.of(
        details("Save Project").failedRequests().count().is(0L),
        details("Save Project").responseTime().percentile3().lte(30),
        details("Save Project").responseTime().percentile4().lte(60),
        details("Save Project").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getSearchProjectPopulation() {
    return new PopulationInfo(
        scenario("Search Project").exec(searchProjectByCode()).injectOpen(
            nothingFor(1),
            rampUsers(50).during(40),
            constantUsersPerSec(50).during(60),
            nothingFor(20)
        ), List.of(
        details("Search Project").failedRequests().count().is(0L),
        details("Search Project").responseTime().percentile3().lte(30),
        details("Search Project").responseTime().percentile4().lte(50),
        details("Search Project").responseTime().max().lte(100))
    );
  }

  private static HttpRequestActionBuilder createRandomProject() {
    Function<Session, String> buildProject = session -> new Gson().toJson(CoreBuilder.buildProjectDto());

    HttpRequestActionBuilder actionBuilder = http("Save Project")
        .post(SimulatorConfig.getApiHost() + ProjectController.PROJECT)
        .body(StringBody(buildProject))
        .transformResponse((response, session) -> {
          ProjectDto proj = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), ProjectDto.class);
          projectToSearch.add(proj.getName());
          projectToSearch.add(proj.getCode());
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder searchProjectByCode() {

    HttpRequestActionBuilder actionBuilder = http("Search Project")
        .get(SimulatorConfig.getApiHost() + ProjectController.PROJECT)
        .queryParam("keyword", session -> projectToSearch.stream().findAny().get());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}