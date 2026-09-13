package org.catools.athena.model.analytics;

import java.io.Serializable;
import java.util.Map;

/** A caller-supplied read-only SQL statement and its named parameters. */
public record AdHocQueryRequestDto(String sql, Map<String, Object> params) implements Serializable {}
