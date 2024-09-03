package org.catools.gatling;

import org.catools.gatling.population.common.PopulationInfo;
import org.catools.gatling.population.core.SpecPopulation;

import java.util.ArrayList;
import java.util.List;

public class AthenaSpecSimulator extends AthenaSimulator {

  protected List<PopulationInfo> getPopulationInfo() {
    List<PopulationInfo> populationsInfo = new ArrayList<>();
    populationsInfo.addAll(SpecPopulation.getPopulationsInfo(MAX_DURATION));
    return populationsInfo;
  }

}