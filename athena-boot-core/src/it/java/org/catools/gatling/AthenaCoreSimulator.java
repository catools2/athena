package org.catools.gatling;

import org.catools.gatling.population.common.PopulationInfo;
import org.catools.gatling.population.core.VersionPopulation;

import java.util.ArrayList;
import java.util.List;

public class AthenaCoreSimulator extends AthenaSimulator {

  protected List<PopulationInfo> getPopulationInfo() {
    List<PopulationInfo> populationsInfo = new ArrayList<>();
//    populationsInfo.addAll(ProjectPopulation.getPopulationsInfo(MAX_DURATION));
//    populationsInfo.addAll(EnvironmentPopulation.getPopulationsInfo(MAX_DURATION));
//    populationsInfo.addAll(UserPopulation.getPopulationsInfo(MAX_DURATION));
    populationsInfo.addAll(VersionPopulation.getPopulationsInfo(MAX_DURATION));
    return populationsInfo;
  }

}