package org.catools.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;

import java.util.stream.Stream;

public class CResponseHeaders extends CSet<CResponseHeader> {
  public CResponseHeaders() {
  }

  public CResponseHeaders(CResponseHeader... c) {
    super(c);
  }

  public CResponseHeaders(Stream<CResponseHeader> stream) {
    super(stream);
  }

  public CResponseHeaders(Iterable<CResponseHeader> iterable) {
    super(iterable);
  }

  @JsonIgnore
  public String getValue(String name) {
    CResponseHeader firstOrNull =
        getFirstOrNull(h -> CStringUtil.equalsIgnoreCase(name, h.Name._get()));
    if (firstOrNull == null) {
      return "";
    }
    return firstOrNull.Value._get();
  }

  @JsonIgnore
  public CResponseHeader getHeader(String name) {
    return getFirstOrNull(h -> CStringUtil.equalsIgnoreCase(name, h.Name._get()));
  }
}
