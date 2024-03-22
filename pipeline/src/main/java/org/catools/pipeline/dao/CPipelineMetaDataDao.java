package org.catools.pipeline.dao;

import org.catools.pipeline.model.CPipelineMetaData;
import org.hibernate.annotations.QueryHints;

public class CPipelineMetaDataDao extends CPipelineBaseDao {
  public static CPipelineMetaData getMetaDataByNameAndValue(String name, String value) {
    return getTransactionResult(entityManager -> entityManager
        .createNamedQuery("getPipelineMetaDataByNameAndValue", CPipelineMetaData.class)
          .setParameter("name", name)
          .setParameter("value", value)
          .setHint(QueryHints.CACHEABLE, true)
          .getResultStream()
          .findFirst()
        .orElse(null));
  }
}
