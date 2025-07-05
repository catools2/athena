package org.catools.gatling.population.core;

import com.google.gson.Gson;
import io.gatling.http.client.body.string.StringRequestBody;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.rest.controller.UserController;
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
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static org.catools.athena.common.utils.ResponseEntityUtils.ENTITY_ID;

public class UserPopulation {

  private static final List<String> searchableStorage = Collections.synchronizedList(new ArrayList<>());
  private static final List<UserDto> updatableStorage = Collections.synchronizedList(new ArrayList<>());

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
        scenario("Save User").exec(group("User").on(createRequest())).injectOpen(
            rampUsers(2).during(5),
            constantUsersPerSec(3).during(maxDuration)
        ),
        List.of(
            details("User", "Save User").failedRequests().percent().lte(1.0),
            details("User", "Save User").responseTime().mean().lte(100),
            details("User", "Save User").responseTime().stdDev().lte(50),
            details("User", "Save User").responseTime().percentile3().lte(80),
            details("User", "Save User").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getUpdatePopulation(int maxDuration) {
    int quiteTime = 10;
    return new PopulationInfo(
        scenario("Update User").exec(group("User").on(updateRequest())).injectOpen(
            nothingFor(quiteTime),
            rampUsers(5).during(5),
            constantUsersPerSec(3).during(maxDuration)
        ),
        List.of(
            details("User", "Update User").failedRequests().percent().lte(1.0),
            details("User", "Update User").responseTime().mean().lte(100),
            details("User", "Update User").responseTime().stdDev().lte(50),
            details("User", "Update User").responseTime().percentile3().lte(80),
            details("User", "Update User").responseTime().max().lte(1000))
    );
  }

  @NotNull
  private static PopulationInfo getSearchPopulation(int maxDuration) {
    int quiteTime = 10;
    int rampUpTime = (int) (maxDuration * 0.2);
    int executionTime = maxDuration - rampUpTime - quiteTime - quiteTime;
    return new PopulationInfo(
        scenario("Search User").exec(group("User").on(searchRequest())).injectOpen(
            nothingFor(quiteTime),
            rampUsersPerSec(5).to(50).during(rampUpTime),
            constantUsersPerSec(50).during(executionTime),
            nothingFor(quiteTime)
        ),
        List.of(
            details("User", "Search User").failedRequests().percent().lte(1.0),
            details("User", "Search User").responseTime().mean().lte(10),
            details("User", "Search User").responseTime().stdDev().lte(5),
            details("User", "Search User").responseTime().percentile3().lte(15),
            details("User", "Search User").responseTime().max().lte(150))
    );
  }

  private static HttpRequestActionBuilder createRequest() {
    Function<Session, String> buildUser = session -> new Gson().toJson(CoreBuilder.buildUserDto());
    HttpRequestActionBuilder actionBuilder = http("Save User")
        .post(SimulatorConfig.getApiHost() + UserController.USER)
        .body(StringBody(buildUser))
        .transformResponse((response, session) -> {
          UserDto user = new Gson().fromJson(((StringRequestBody) response.request().getBody()).getContent(), UserDto.class);
          user.setId(Long.parseLong(response.headers().get(ENTITY_ID)));
          if (updatableStorage.size() < 30)
            updatableStorage.add(user);
          else {
            searchableStorage.add(user.getUsername());
            user.getAliases().forEach(a -> searchableStorage.add(a.getAlias()));
          }
          return response;
        });

    return GatlingRequestUtils.decorateJsonPostRequest(201, actionBuilder);
  }

  private static HttpRequestActionBuilder updateRequest() {
    Function<Session, String> buildUser =
        session -> new Gson().toJson(CoreBuilder.buildUserDto().setId(updatableStorage.stream().findAny().orElseThrow().getId()));

    HttpRequestActionBuilder actionBuilder = http("Update User")
        .put(SimulatorConfig.getApiHost() + UserController.USER)
        .body(StringBody(buildUser));

    return GatlingRequestUtils.decorateJsonPostRequest(200, actionBuilder);
  }

  private static HttpRequestActionBuilder searchRequest() {
    HttpRequestActionBuilder actionBuilder = http("Search User")
        .get(SimulatorConfig.getApiHost() + UserController.USER)
        .queryParam("keyword", session ->
            searchableStorage.stream().skip(RandomUtils.nextInt(0, searchableStorage.size())).findAny().orElseThrow());

    return GatlingRequestUtils.decorateGetRequest(List.of(200), actionBuilder);
  }
}