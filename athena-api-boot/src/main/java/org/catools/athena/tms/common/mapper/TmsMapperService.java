package org.catools.athena.tms.common.mapper;

import org.catools.athena.tms.common.entity.*;

public interface TmsMapperService {

  /**
   * Get item type by code
   */
  Item getItemByCode(String code);

  /**
   * Get item type by code
   */
  ItemType getItemTypeByCode(String code);

  /**
   * Get status by code
   */
  Status getStatusByCode(String code);

  /**
   * Get priority by code
   */
  Priority getPriorityByCode(String code);

  /**
   * Get test cycle by code
   */
  TestCycle getTestCycleByCode(String code);
}