package org.catools.gatling;

import org.catools.gatling.population.common.PopulationInfo;
import org.catools.gatling.population.git.CommitPopulation;
import org.catools.gatling.population.git.GitRepositoryPopulation;

import java.util.ArrayList;
import java.util.List;

public class GitRepositorySimulator extends AthenaSimulator {

  protected List<PopulationInfo> getPopulationInfo() {
    List<PopulationInfo> populationsInfo = new ArrayList<>();
    populationsInfo.addAll(GitRepositoryPopulation.getPopulationsInfo());
    populationsInfo.addAll(CommitPopulation.getPopulationsInfo());
    return populationsInfo;
  }

}