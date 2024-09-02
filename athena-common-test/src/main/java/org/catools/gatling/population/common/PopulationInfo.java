package org.catools.gatling.population.common;

import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.PopulationBuilder;

import java.util.List;

public record PopulationInfo(PopulationBuilder population, List<Assertion> assertions) {
}
