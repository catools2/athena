package org.catools.gatling;

import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Simulation;
import org.catools.gatling.population.common.PopulationInfo;
import org.catools.gatling.population.core.EnvironmentPopulation;
import org.catools.gatling.population.core.ProjectPopulation;
import org.catools.gatling.population.core.UserPopulation;
import org.catools.gatling.population.core.VersionPopulation;

import java.util.ArrayList;
import java.util.List;

import static io.gatling.javaapi.http.HttpDsl.http;

public class AthenaSimulator extends Simulation {
  private static List<PopulationInfo> getPopulationInfo() {
    List<PopulationInfo> populationsInfo = new ArrayList<>();
    populationsInfo.addAll(ProjectPopulation.getPopulationsInfo());
    populationsInfo.addAll(EnvironmentPopulation.getPopulationsInfo());
    populationsInfo.addAll(UserPopulation.getPopulationsInfo());
    populationsInfo.addAll(VersionPopulation.getPopulationsInfo());
    return populationsInfo;
  }

  {
    setUp(getPopulations())
        .protocols(http.disableCaching().disableFollowRedirect())
        .maxDuration(120)
        .assertions(getAssertions());
  }

  private static List<PopulationBuilder> getPopulations() {
    List<PopulationBuilder> populations = new ArrayList<>();
    getPopulationInfo().forEach(p -> populations.add(p.population()));
    return populations;
  }

  private static List<Assertion> getAssertions() {
    List<Assertion> assertions = new ArrayList<>();
    getPopulationInfo().forEach(p -> assertions.addAll(p.assertions()));
    return assertions;
  }
}