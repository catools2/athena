package org.catools.tms.etl.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CSet;
import org.catools.tms.etl.helpers.CEtlHelper;
import org.catools.tms.etl.model.CEtlExecution;
import org.catools.tms.etl.model.CEtlExecutions;

@Slf4j
public class CEtlExecutionDao extends CEtlBaseDao {
  public static void mergeExecutions(CEtlExecutions executions) {
    for (CEtlExecution execution : executions) {
      mergeExecution(execution);
    }
  }

  public static void mergeExecution(CEtlExecution execution) {
    log.trace("Start mering executions for {} item.", execution.getItem().getId());
    CEtlHelper.normalizeExecution(execution);
    merge(execution);
    log.trace("Finish mering executions for {} item.", execution.getItem().getId());
  }

  public static CSet<String> getExecutionsByCycleId(String cycleId) {
    return getTransactionResult(
        session -> {
          return CSet.of(session
              .createNativeQuery("SELECT DISTINCT item_id from tms.execution where cycle_id=:cycle_id")
              .setParameter("cycle_id", cycleId)
              .getResultStream());
        });
  }

  public static void deleteExecutions(String cycleId, CSet<String> issueIds) {
    issueIds.partition(50).forEach(ids -> {
      doTransaction(
          session -> {
            return session
                .createQuery("Delete CEtlExecution where cycle_id=:cycle_id AND item_id in (:item_ids)")
                .setParameter("cycle_id", cycleId)
                .setParameter("item_ids", ids)
                .executeUpdate();
          });
    });
  }
}
