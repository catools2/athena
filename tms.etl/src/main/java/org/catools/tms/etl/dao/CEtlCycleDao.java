package org.catools.tms.etl.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.tms.etl.model.CEtlCycle;

@Slf4j
public class CEtlCycleDao extends CEtlBaseDao {
  public static CEtlCycle getCycleById(String cycleId) {
    return find(CEtlCycle.class, cycleId);
  }
}
