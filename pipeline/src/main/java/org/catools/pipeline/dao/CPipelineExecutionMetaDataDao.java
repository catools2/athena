package org.catools.pipeline.dao;

import org.catools.pipeline.model.CPipelineExecutionMetaData;
import org.hibernate.annotations.QueryHints;

public class CPipelineExecutionMetaDataDao extends CPipelineBaseDao {
  public static CPipelineExecutionMetaData getMetaDataByNameAndValue(String name, String value) {
    return getTransactionResult(entityManager -> {
      return entityManager
          .createNamedQuery("getExecutionMetaDataByNameAndValue", CPipelineExecutionMetaData.class)
          .setParameter("name", name)
          .setParameter("value", value)
          .setHint(QueryHints.CACHEABLE, true)
          .getResultStream()
          .findFirst()
          .orElse(null);
    });
  }
}
