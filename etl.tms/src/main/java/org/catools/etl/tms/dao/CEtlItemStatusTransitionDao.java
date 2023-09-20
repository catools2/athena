package org.catools.etl.tms.dao;

import org.catools.common.date.CDate;
import org.catools.etl.tms.model.CEtlItemStatusTransition;
import org.hibernate.annotations.QueryHints;

public class CEtlItemStatusTransitionDao extends CEtlBaseDao {
  public static CEtlItemStatusTransition getItemStatusTransition(String itemId, CEtlItemStatusTransition statusTransition) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getItemStatusTransitionByStatusesAndDate", CEtlItemStatusTransition.class)
              .setParameter("fromName", statusTransition.getFrom().getName())
              .setParameter("toName", statusTransition.getTo().getName())
              .setParameter("occurred", CDate.of(statusTransition.getOccurred()).getTimeStamp())
              .setParameter("itemId", itemId)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
