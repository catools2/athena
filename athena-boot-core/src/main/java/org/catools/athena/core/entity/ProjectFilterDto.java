package org.catools.athena.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Project entity filter parameters.
 * Provides type-safe filtering for Project queries.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Filter parameters for Project entity queries")
public class ProjectFilterDto {

  @Schema(description = "Filter by code property", example = "P100")
  private String code;

  @Schema(description = "Filter by name property", example = "Production")
  private String name;

  /**
   * Convert ProjectFilterDto to filter string format for internal use
   * Format: "property:value,property2:value2"
   *
   * @return filter string
   */
  public String toFilterString() {
    StringBuilder sb = new StringBuilder();

    if (code != null && !code.trim().isEmpty()) {
      if (sb.length() > 0) sb.append(",");
      sb.append("code:").append(code.trim());
    }

    if (name != null && !name.trim().isEmpty()) {
      if (sb.length() > 0) sb.append(",");
      sb.append("name:").append(name.trim());
    }

    return sb.toString();
  }

  /**
   * Check if any filter is set
   *
   * @return true if at least one filter property is not null/empty
   */
  public boolean hasFilters() {
    return (code != null && !code.trim().isEmpty()) ||
           (name != null && !name.trim().isEmpty());
  }
}

