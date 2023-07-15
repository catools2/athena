package org.catools.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import io.restassured.http.Header;
import lombok.*;
import org.catools.common.extensions.types.CStaticStringExtension;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CResponseHeader {
  private String name;
  private String value;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @JsonIgnore
  public final CStaticStringExtension Name =
      new CStaticStringExtension() {
        @Override
        @JsonIgnore
        public String _get() {
          return name;
        }
      };

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @JsonIgnore
  public final CStaticStringExtension Value =
      new CStaticStringExtension() {
        @Override
        @JsonIgnore
        public String _get() {
          return value;
        }
      };

  public CResponseHeader(Header header) {
    this(header == null ? null : header.getName(), header == null ? null : header.getValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CResponseHeader that = (CResponseHeader) o;
    return Objects.equal(name, that.name) && Objects.equal(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, value);
  }
}
