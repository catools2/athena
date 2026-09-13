package org.catools.athena.model.analytics;

import java.io.Serializable;
import java.util.List;

/** Description of a registered analytics query. */
public record QuerySummaryDto(
    String id,
    String title,
    List<String> views,
    List<String> tables,
    List<QueryParamDto> params,
    List<String> dashboards,
    String origin) implements Serializable {}
