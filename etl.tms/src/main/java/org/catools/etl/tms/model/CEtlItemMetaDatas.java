package org.catools.etl.tms.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CEtlItemMetaDatas extends CSet<CEtlItemMetaData> {
  public CEtlItemMetaDatas() {
  }

  public CEtlItemMetaDatas(CEtlItemMetaData... c) {
    super(c);
  }

  public CEtlItemMetaDatas(Stream<CEtlItemMetaData> stream) {
    super(stream);
  }

  public CEtlItemMetaDatas(Iterable<CEtlItemMetaData> iterable) {
    super(iterable);
  }
}
