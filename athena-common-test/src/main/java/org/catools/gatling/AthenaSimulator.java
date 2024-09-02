package org.catools.gatling;

import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Simulation;
import org.catools.gatling.population.common.PopulationInfo;

import java.util.ArrayList;
import java.util.List;

import static io.gatling.javaapi.http.HttpDsl.http;

public abstract class AthenaSimulator extends Simulation {
  protected static final int MAX_DURATION = 60;

  protected abstract List<PopulationInfo> getPopulationInfo();

  {
    setUp(getPopulations())
        .protocols(http.disableCaching().disableFollowRedirect())
        .maxDuration(MAX_DURATION)
        .assertions(getAssertions());
  }

  private List<PopulationBuilder> getPopulations() {
    List<PopulationBuilder> populations = new ArrayList<>();
    getPopulationInfo().forEach(p -> populations.add(p.population()));
    return populations;
  }

  private List<Assertion> getAssertions() {
    List<Assertion> assertions = new ArrayList<>();
    getPopulationInfo().forEach(p -> assertions.addAll(p.assertions()));
    return assertions;
  }
}