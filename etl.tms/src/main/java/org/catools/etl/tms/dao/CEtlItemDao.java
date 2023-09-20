package org.catools.etl.tms.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.utils.CRetry;
import org.catools.etl.tms.helpers.CEtlHelper;
import org.catools.etl.tms.model.CEtlItem;
import org.catools.etl.tms.model.CEtlItemMetaData;
import org.catools.etl.tms.model.CEtlItems;

@Slf4j
public class CEtlItemDao extends CEtlBaseDao {
  public static void mergeItems(CEtlItems items) {
    for (CEtlItem item : items) {
      mergeItem(item);
    }
  }

  public static void mergeItem(CEtlItem item) {
    // We should delete metadata relationship to replicate the effect of updating or changing when item update
    String itemId = item.getId();
    log.trace("Start mering {} item.", itemId);

    CRetry.retry(integer -> {
      CEtlHelper.normalizeItem(item);
      doTransactions(
          session -> session
              .createNativeQuery("DELETE from tms.item_metadata where item_id=:itemId", CEtlItemMetaData.class)
              .setParameter("itemId", itemId)
              .executeUpdate(),
          session -> session.merge(item));
      return true;
    }, 5, 15000);
    log.trace("Finish mering {} item.", itemId);
  }

  public static CEtlItem getItemById(String itemId) {
    return find(CEtlItem.class, itemId);
  }
}
