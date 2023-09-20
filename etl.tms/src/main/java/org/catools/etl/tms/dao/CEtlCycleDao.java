package org.catools.etl.tms.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.etl.tms.model.CEtlCycle;

@Slf4j
public class CEtlCycleDao extends CEtlBaseDao {
  public static CEtlCycle getCycleById(String cycleId) {
    return find(CEtlCycle.class, cycleId);
  }
}
