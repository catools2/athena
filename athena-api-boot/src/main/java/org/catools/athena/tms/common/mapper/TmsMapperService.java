package org.catools.athena.tms.common.mapper;

import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;

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

}