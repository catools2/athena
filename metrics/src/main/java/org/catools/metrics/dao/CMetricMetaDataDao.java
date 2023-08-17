package org.catools.metrics.dao;

import org.catools.metrics.model.CMetric;
import org.hibernate.annotations.QueryHints;

public class CMetricMetaDataDao extends CMetricsDao {
  public static CMetric getMetaDataByNameAndValue(String name, Number value) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getMetaDataByNameAndValue", CMetric.class)
        .setParameter("name", name)
        .setParameter("value", value)
        .setHint(QueryHints.CACHEABLE, true)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
