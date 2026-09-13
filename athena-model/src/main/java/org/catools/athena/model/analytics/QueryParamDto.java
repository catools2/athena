package org.catools.athena.model.analytics;

import java.io.Serializable;
import java.util.List;

/** One parameter declared by a registered analytics query. */
public record QueryParamDto(String name, String kind, List<String> allowed) implements Serializable {}
