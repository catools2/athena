package org.catools.athena.analytics.registry;

/**
 * How a registry parameter reaches the database.
 *
 * <p>{@link #OPERATOR} is the odd one out and the reason this enum exists: Grafana's
 * {@code $cycle_type} expands to {@code LIKE} or {@code NOT LIKE}, which no bind parameter can
 * carry. It is substituted into the SQL text, so its value must come from a closed whitelist rather
 * than from the caller.
 */
public enum ParamKind {
  SCALAR,
  LIST,
  INSTANT,
  OPERATOR
}
