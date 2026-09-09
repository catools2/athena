package org.catools.athena.analytics.registry;

import java.util.List;

/**
 * A declared parameter of a registered query.
 *
 * @param name    parameter name, matching the {@code :name} bind or {@code {{name}}} slot in the SQL
 * @param kind    how it is bound, or that it is substituted
 * @param allowed for {@link ParamKind#OPERATOR}, the only values that may be substituted; empty otherwise
 */
public record QueryParam(String name, ParamKind kind, List<String> allowed) {

  public QueryParam {
    allowed = allowed == null ? List.of() : List.copyOf(allowed);
  }

  public boolean isOperator() {
    return kind == ParamKind.OPERATOR;
  }
}
