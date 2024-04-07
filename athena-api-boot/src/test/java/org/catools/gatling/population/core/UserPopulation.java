package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.configs.SimulatorConfig;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.rest.controller.UserController;
import org.catools.gatling.population.common.GatlingRequestUtils;
import org.catools.gatling.population.common.PopulationInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class UserPopulation {

  private static final List<String> usersToSearch = Collections.synchronizedList(new ArrayList<>());

  public static List<PopulationInfo> getPopulationsInfo() {
    return List.of(
        getCreateUserPopulation(),
        getSearchUserPopulation()
    );
  }

  @NotNull
  private static PopulationInfo getCreateUserPopulation() {
    return new PopulationInfo(
        scenario("Create User").exec(createRandomUser()).injectOpen(
            rampUsers(5).during(5),
            constantUsersPerSec(3).during(110)
        ), List.of(
        details("Save User").failedRequests().count().is(0L),
        details("Save User").responseTime().percentile3().lte(60),
        details("Save User").responseTime().percentile4().lte(100),
        details("Save User").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getSearchUserPopulation() {
    return new PopulationInfo(
        scenario("Search User").exec(searchUserByCode()).injectOpen(
            nothingFor(1),
            rampUsers(50).during(40),
            constantUsersPerSec(50).during(60),
            nothingFor(20)
        ), List.of(
        details("Search User").failedRequests().count().is(0L),
        details("Search User").responseTime().percentile3().lte(30),
        details("Search User").responseTime().percentile4().lte(50),
        details("Search User").responseTime().max().lte(100))
    );
  }

  private static HttpRequestActionBuilder createRandomUser() {
    Function<Session, String> buildUser = session -> new Gson().toJson(CoreBuilder.buildUserDto());
    HttpRequestActionBuilder actionBuilder = http("Save User")
        .post(SimulatorConfig.getApiHost() + UserController.USER)
        .body(StringBody(buildUser))
        .transformResponse((response, session) -> {
          UserDto user = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), UserDto.class);
          usersToSearch.add(user.getUsername());
          user.getAliases().forEach(a -> usersToSearch.add(a.getAlias()));
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder searchUserByCode() {
    HttpRequestActionBuilder actionBuilder = http("Search User")
        .get(SimulatorConfig.getApiHost() + UserController.USER)
        .queryParam("keyword", session -> usersToSearch.stream().findAny().get());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}