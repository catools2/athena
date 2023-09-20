package org.catools.etl.tms.dao;

import org.catools.etl.tms.model.CEtlItemMetaData;
import org.hibernate.annotations.QueryHints;

public class CEtlMetaDataDao extends CEtlBaseDao {
  public static CEtlItemMetaData getMetaDataByNameAndValue(CEtlItemMetaData metaData) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getEtlMetaDataByNameAndValue", CEtlItemMetaData.class)
              .setParameter("name", metaData.getName())
              .setParameter("value", metaData.getValue())
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
